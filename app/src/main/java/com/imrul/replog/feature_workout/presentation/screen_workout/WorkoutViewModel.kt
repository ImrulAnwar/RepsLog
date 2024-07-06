package com.imrul.replog.feature_workout.presentation.screen_workout

import android.content.Context
import android.widget.Toast
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.navigation.NavHostController
import com.imrul.replog.core.Routes
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
    var workoutTitle by mutableStateOf(Strings.WORKOUT_TITLE)
        private set

    var elapsedTime by mutableStateOf("00:00")
        private set

    var listOfWeights = mutableStateListOf<Pair<Int, String>>()
        private set
    var listOfReps = mutableStateListOf<String>()
        private set

    var listOfIsDone = mutableStateListOf<Boolean>()
        private set
    var listOfTillFailure = mutableStateListOf<Boolean>()
        private set

    var listOfExerciseName = mutableStateListOf<String>()
        private set
    var listOfExerciseId = mutableStateListOf<Long>()
        private set
    var listOfNotes = mutableStateListOf<String>()
        private set

    init {
        viewModelScope.launch { observeDuration() }
    }

    fun start() {
        workoutUseCases.durationUseCase.start()
    }

    private suspend fun observeDuration() {
        workoutUseCases.durationUseCase.elapsedTime.collect {
            elapsedTime = it
        }
    }

    fun onWorkoutTitleChanged(value: String) {
        workoutTitle = value
    }

    fun addSet(first: String, second: String, exerciseIndex: Int? = null) {
        exerciseIndex?.let {
            listOfWeights.add(Pair(it, ""))
            listOfReps.add("")
            listOfIsDone.add(false)
            listOfTillFailure.add(false)
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

    fun addExercise(navController: NavHostController) {
        navController.navigate(Routes.ScreenExerciseListFromWorkout)
        listOfNotes.add("")
    }

    fun addExerciseNameAndId(name: String, exerciseId: Long, context: Context) {
        listOfExerciseName.add(name)
        listOfExerciseId.add(exerciseId)
        Toast.makeText(context, listOfExerciseName.size.toString(), Toast.LENGTH_SHORT).show()
    }

    fun onNoteValueChanged(
        exerciseIndex: Int,
        content: String
    ) {
        listOfNotes[exerciseIndex] = content
    }

    private suspend fun insertSessions(exerciseIndex: Int, workoutId: Long) {
        // exercise id ta exercise insert korar por pabo. workout id ta use kore

        var session = Session(
            workoutIdForeign = workoutId
        )
        val sessionId = workoutUseCases.insertSession(session)
        var setCount = 0
        listOfWeights.forEachIndexed { i, item ->
            if (item.first == exerciseIndex) {
                //this set belongs to the exercise
                val set = Set(
                    weightValue = listOfWeights[i].second.toFloatOrNull() ?: 0f,
                    reps = listOfReps[i].toFloatOrNull() ?: 0f,
                    sessionIdForeign = sessionId
                )
                if (listOfIsDone[i]) {
                    setCount++
                    workoutUseCases.insertSet(set)
                }
            }
        }
        session = Session(
            sessionId = sessionId,
            workoutIdForeign = workoutId,
            setCount = setCount.toLong()
        )
        workoutUseCases.insertSession(session)
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

            listOfExerciseName.forEachIndexed { index, item ->
                insertSessions(index, workoutId)
            }
        }
    }

    fun toggleIsDone(setIndex: Int) {
        listOfIsDone[setIndex] = !listOfIsDone[setIndex]
    }

    fun toggleTillFailure(setIndex: Int) {
        listOfTillFailure[setIndex] = !listOfTillFailure[setIndex]
    }

    fun shouldInsertWorkout(): Boolean = workoutUseCases.shouldInsertWorkout(listOfIsDone)
}