package com.imrul.replog.feature_workout.presentation.screen_workout

import android.content.Context
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.imrul.replog.core.Strings
import com.imrul.replog.feature_workout.domain.model.Session
import com.imrul.replog.feature_workout.domain.model.Set
import com.imrul.replog.feature_workout.domain.model.Workout
import com.imrul.replog.feature_workout.domain.use_cases.WorkoutUseCases
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale
import javax.inject.Inject

@HiltViewModel
class WorkoutViewModel @Inject constructor(
    private val workoutUseCases: WorkoutUseCases,
) : ViewModel() {
    var isWorkOutRunning by mutableStateOf(
        false
    )
        private set

    var workoutTitle by mutableStateOf(Strings.WORKOUT_TITLE)
        private set

    var elapsedTime by mutableStateOf("00:00")
        private set

    var listOfWeights = mutableStateListOf<Pair<Int, String>>()
        private set
    var listOfReps = mutableStateListOf<String>()
        private set

    var listOfPrevious = mutableStateListOf<String>()
        private set

    var listOfIsDone = mutableStateListOf<Boolean>()
        private set
    var listOfTillFailure = mutableStateListOf<Boolean>()
        private set

    var listOfExerciseName = mutableStateListOf<String>()
        private set

    var listOfWeightUnits = mutableStateListOf<String>()
        private set
    var listOfExerciseId = mutableStateListOf<Long>()
        private set
    var listOfNotes = mutableStateListOf<String>()
        private set

    init {
        viewModelScope.launch { observeDuration() }
    }

    private suspend fun observeDuration() {
        workoutUseCases.durationUseCase.elapsedTime.collect {
            elapsedTime = it
        }
    }

    fun onWorkoutTitleChanged(value: String) {
        workoutTitle = value
    }

    fun addSet(exerciseIndex: Int? = null) {
        exerciseIndex?.let {
            listOfWeights.add(Pair(it, ""))
            listOfReps.add("")
            listOfIsDone.add(false)
            listOfTillFailure.add(false)
            listOfPrevious.add("-")
        }
    }

    fun onWeightValueChanged(
        setIndex: Int,
        content: String,
    ) {
        listOfWeights[setIndex] = Pair(
            first = listOfWeights[setIndex].first,
            second = content
        )
    }

    fun onRepValueChanged(
        setIndex: Int,
        content: String,
    ) {
        listOfReps[setIndex] = content
    }

    fun addExerciseNameAndId(name: String, exerciseId: Long, context: Context) {
        listOfExerciseName.add(name)
        listOfExerciseId.add(exerciseId)
        // loading previous session
        viewModelScope.launch {
            workoutUseCases.getLatestSessionByExerciseId(exerciseId = exerciseId)
                .collect { session ->
                    session?.sessionId?.let { sessionId ->

                        workoutUseCases.getAllSetsBySessionId(sessionId = sessionId)
                            .collect { listOfSets ->
                                val exerciseIndex = listOfExerciseName.size - 1
                                listOfSets.forEach { set ->
                                    listOfWeights.add(Pair(exerciseIndex, ""))
                                    listOfReps.add("")
                                    listOfIsDone.add(false)
                                    listOfTillFailure.add(set.setType == Set.SET_TYPE_FAILURE)
                                    listOfPrevious.add("${set.weightValue} ${session.weightUnit} x ${set.reps.toInt()}")
                                }
                            }
                    }
                }
        }
        // add the previous Weight Unit
        listOfWeightUnits.add(Session.WEIGHT_UNIT_KG)
    }

    fun removeExercise(exerciseId: Long) {
        val exerciseIndex = listOfExerciseId.indexOf(exerciseId)
        listOfExerciseName.removeAt(exerciseIndex)
        listOfExerciseId.removeAt(exerciseIndex)

        val listOfWeightsCopy = listOfWeights.toList()
        val listOfIndicesToRemove = mutableListOf<Int>()
        listOfWeightsCopy.forEachIndexed { i, item ->
            if (item.first == exerciseIndex) {
                listOfIndicesToRemove.add(i)
            }
        }

        // since I am using index to determine which set belong to which exercise
        // so when i remove an exercise, I will have to update to the new index
        listOfWeightsCopy.forEachIndexed { index, item ->
            if (item.first > exerciseIndex) {
                listOfWeights[index] = Pair(item.first - 1, item.second)
            }
        }
        // descending because removed item doesn't change the next items index
        listOfIndicesToRemove.sortedDescending().forEach { index ->
            removeSet(index)
        }
    }

    fun removeSet(index: Int) {
        listOfWeights.removeAt(index)
        listOfReps.removeAt(index)
        listOfIsDone.removeAt(index)
        listOfTillFailure.removeAt(index)
        listOfPrevious.removeAt(index)
    }

    fun onNoteValueChanged(
        exerciseIndex: Int,
        content: String
    ) {
        listOfNotes[exerciseIndex] = content
    }

    private suspend fun insertSessions(exerciseIndex: Int, workoutId: Long) {

        var session = Session(
            workoutIdForeign = workoutId
        )
        val sessionId = workoutUseCases.insertSession(session)
        var setCount = 0
        var maxWeight = 0f
        var maxReps = 0

        val listOfWeightsCopy = listOfWeights.toList()
        listOfWeightsCopy.forEachIndexed { i, item ->
            if (item.first == exerciseIndex) {
                //this set belongs to the exercise
                val set = Set(
                    weightValue = listOfWeights[i].second.toFloatOrNull() ?: 0f,
                    reps = listOfReps[i].toFloatOrNull() ?: 0f,
                    sessionIdForeign = sessionId,
                    isDone = true,
                    setType = if (listOfTillFailure[i]) Set.SET_TYPE_FAILURE else Set.SET_TYPE_WARM_UP
                )
                if (listOfIsDone[i]) {
                    setCount++
                    workoutUseCases.insertSet(set)
                }

                // for best set
                if (maxWeight < listOfWeights[i].second.toFloat()) {
                    maxWeight = listOfWeights[i].second.toFloat()
                    maxReps = listOfReps[i].toInt()
                }
            }
        }
        session = Session(
            sessionId = sessionId,
            workoutIdForeign = workoutId,
            setCount = setCount.toLong(),
            exerciseIdForeign = listOfExerciseId[exerciseIndex],
            exerciseName = listOfExerciseName[exerciseIndex],
            bestSet = "${convertFloatToIntIfPossible(maxWeight)} ${listOfWeightUnits[exerciseIndex]} x $maxReps ",
            weightUnit = listOfWeightUnits[exerciseIndex]
        )
        workoutUseCases.insertSession(session)
    }

    private fun convertFloatToIntIfPossible(number: Float): Number {
        val numberAsString = number.toString()
        return if (numberAsString.endsWith(".0") || numberAsString.endsWith(".")) {
            number.toInt()
        } else {
            number
        }
    }

    fun insertWorkout() {
        viewModelScope.launch {
            val date = System.currentTimeMillis()
            var dateFormat = SimpleDateFormat("MMMM dd", Locale.getDefault())
            val dateString = dateFormat.format(Date(date))
            dateFormat = SimpleDateFormat("EEEE", Locale.getDefault())
            val weekDayString = dateFormat.format(Date(date))

            val workout = Workout(
                name = workoutTitle,
                durationString = elapsedTime.split(":")[0].toInt().toString() + "m",
                date = date,
                dateString = dateString,
                weekdayString = weekDayString
            )
            val workoutId: Long = workoutUseCases.insertWorkout(workout)
            val exerciseNamesCopy = listOfExerciseName.toList()
            exerciseNamesCopy.forEachIndexed { index, _ ->
                insertSessions(index, workoutId)
            }
        }.invokeOnCompletion {
            clearAllData()
        }
    }

    fun toggleIsDone(setIndex: Int) {
        listOfIsDone[setIndex] = !listOfIsDone[setIndex]
    }

    fun setWorkoutRunning(flag: Boolean) {
        isWorkOutRunning = flag
    }

    fun toggleTillFailure(setIndex: Int) {
        listOfTillFailure[setIndex] = !listOfTillFailure[setIndex]
    }

    fun shouldInsertWorkout(): Boolean =
        workoutUseCases.shouldInsertWorkout(listOfIsDone, listOfWeights, listOfReps)

    override fun onCleared() {
        isWorkOutRunning = false
        super.onCleared()
    }

    fun clearAllData() {
        isWorkOutRunning = false
        workoutTitle = Strings.WORKOUT_TITLE
        elapsedTime = "00:00"
        listOfWeights.clear()
        listOfReps.clear()
        listOfIsDone.clear()
        listOfTillFailure.clear()
        listOfExerciseName.clear()
        listOfWeightUnits.clear()
        listOfExerciseId.clear()
        listOfNotes.clear()
        listOfPrevious.clear()
    }

}

