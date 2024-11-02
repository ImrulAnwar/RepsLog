package com.imrul.replog.feature_workout.presentation.screen_workout

import android.content.Context
import android.util.Log
import android.widget.Toast
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.imrul.replog.core.Strings
import com.imrul.replog.feature_workout.domain.model.Note
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
    var isInserting by mutableStateOf(
        false
    )
        private set

    var workoutTitle by mutableStateOf(Strings.WORKOUT_TITLE)
        private set

    var elapsedTime by mutableStateOf("00:00")
        private set

    var listOfWeights = mutableStateListOf<Pair<Int, String>>()
        private set
    var listOfExerciseNotes = mutableStateListOf<Pair<Int, String>>()
        private set

    var listOfWorkoutNotes = mutableStateListOf<String>()
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
    var listOfExerciseId = mutableStateListOf<String>()
        private set

    var listOfNoteId = mutableStateListOf<String>()
        private set

    init {
        viewModelScope.launch { observeDuration() }
    }

    private suspend fun observeDuration() {
        workoutUseCases.durationUseCase.elapsedTime.collect {
            elapsedTime = it
        }
    }

    fun setWorkoutName(workoutName: String) {
        workoutTitle = workoutName
    }

    fun getAllWorkoutNotes(workoutId: String) {
        viewModelScope.launch {
            workoutUseCases.getAllNotes().collect { notes ->
                notes.forEach { note ->
                    if (note.belongsTo == Note.WORKOUT && note.idForeign == workoutId) {
                        listOfWorkoutNotes.add(note.content)
                    }
                }
            }
        }
    }

    fun addExerciseNote(exerciseIndex: Int, text: String = "") {
        listOfExerciseNotes.add(Pair(exerciseIndex, text))
    }

    fun addWorkoutNote(text: String = "") {
        listOfWorkoutNotes.add(text)
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

    fun removeExerciseNote(index: Int) {
        listOfNoteId.removeAt(index)
        listOfExerciseNotes.removeAt(index)
    }

    fun removeWorkoutNote(index: Int) {
        listOfWorkoutNotes.removeAt(index)
    }

    fun changeWeightUnit(exerciseIndex: Int) {
        val lisOfWeightsCopy = listOfWeights.toList()
        if (listOfWeightUnits[exerciseIndex] == Session.WEIGHT_UNIT_KG) {
            listOfWeightUnits[exerciseIndex] = Session.WEIGHT_UNIT_LB
            lisOfWeightsCopy.forEachIndexed { index, item ->
                if (item.first == exerciseIndex) {
                    val newWeight = item.second.toFloatOrNull()
                    newWeight?.let {
                        val formattedWeight = "%.2f".format(it * 2.205)
                        listOfWeights[index] = Pair(exerciseIndex, formattedWeight)
                    }
                }
            }
        } else {
            listOfWeightUnits[exerciseIndex] = Session.WEIGHT_UNIT_KG
            lisOfWeightsCopy.forEachIndexed { index, item ->
                if (item.first == exerciseIndex) {
                    val newWeight = item.second.toFloatOrNull()
                    newWeight?.let {
                        val formattedWeight = "%.2f".format(it / 2.205)
                        listOfWeights[index] = Pair(exerciseIndex, formattedWeight)
                    }
                }
            }
        }
    }

    fun onRepValueChanged(
        setIndex: Int,
        content: String,
    ) {
        listOfReps[setIndex] = content
    }

    fun addExerciseAndSets(name: String, exerciseId: String, context: Context) {
        if (listOfExerciseId.contains(exerciseId)) {
            Toast.makeText(context, "The exercise is already in your workout", Toast.LENGTH_SHORT)
                .show()
            return
        }

        listOfExerciseName.add(name)
        listOfExerciseId.add(exerciseId)
        val exerciseIndex = listOfExerciseId.indexOf(exerciseId)

        // loading previous session
        viewModelScope.launch {

            workoutUseCases.getLatestSessionByExerciseId(exerciseId = exerciseId)
                .collect { session ->
                    session?.id?.let { sessionId ->
                        launch {
                            workoutUseCases.getNotesBySessionId(sessionId = sessionId)
                                .collect { notes ->
                                    notes.forEach { note ->
                                        if (!listOfNoteId.contains(note.id)) {
                                            note.id?.let { listOfNoteId.add(it) }
                                            listOfExerciseNotes.add(
                                                Pair(
                                                    exerciseIndex,
                                                    note.content
                                                )
                                            )
                                        }

                                    }
                                }
                        }
                        launch {
                            workoutUseCases.getAllSetsBySessionId(sessionId = sessionId)
                                .collect { listOfSets ->
                                    listOfSets.forEach { set ->
                                        listOfWeights.add(
                                            Pair(
                                                exerciseIndex,
                                                set.weightValue.toString()
                                            )
                                        )
                                        listOfReps.add((set.reps.toInt() + 1).toString())
                                        listOfIsDone.add(false)
                                        listOfTillFailure.add(set.setType == Set.SET_TYPE_FAILURE)
                                        listOfPrevious.add("${set.weightValue} ${session.weightUnit} x ${set.reps.toInt()}")
                                    }
                                    listOfWeightUnits.add(session.weightUnit)
                                }
                        }

                    }
                    if (session == null) {
                        listOfWeightUnits.add(Session.WEIGHT_UNIT_KG)
                    }
                }
        }

    }

    fun removeExercise(exerciseId: String) {
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
        // removing Sets
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

        // removing notes
        var listOfNotesCopy = listOfExerciseNotes.toList()
        listOfNotesCopy.forEachIndexed { index, item ->
            if (item.first > exerciseIndex) {
                listOfExerciseNotes[index] = Pair(item.first - 1, item.second)
            }
        }

        listOfNotesCopy = listOfExerciseNotes.toList().reversed()

        listOfNotesCopy.forEachIndexed { _, item ->
            if (item.first == exerciseIndex) {
                val index = listOfExerciseNotes.indexOf(item)
                listOfExerciseNotes.removeAt(index)
                listOfNoteId.removeAt(index)
            }
        }
    }

    fun removeSet(index: Int) {
        listOfWeights.removeAt(index)
        listOfReps.removeAt(index)
        listOfIsDone.removeAt(index)
        listOfTillFailure.removeAt(index)
        listOfPrevious.removeAt(index)
    }

    fun onExerciseNoteValueChanged(
        exerciseIndex: Int,
        content: String,
        noteIndex: Int
    ) {
        listOfExerciseNotes[noteIndex] = Pair(exerciseIndex, content)
    }

    fun onWorkoutNoteValueChanged(
        index: Int,
        content: String,
    ) {
        listOfWorkoutNotes[index] = content
    }


    private suspend fun insertSessions(exerciseIndex: Int, workoutId: String) {

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
                val weightString = listOfWeights[i].second
                val repString = listOfReps[i]
                if (weightString.isValidNumber() && weightString.isNotEmpty() && weightString.isNotEmpty() && repString.isNotEmpty())
                    if (maxWeight < listOfWeights[i].second.toFloat()) {
                        maxWeight = listOfWeights[i].second.toFloat()
                        maxReps = listOfReps[i].toInt()
                    }
            }
        }
        session = Session(
            id = sessionId,
            workoutIdForeign = workoutId,
            setCount = setCount.toLong(),
            exerciseIdForeign = listOfExerciseId[exerciseIndex],
            exerciseName = listOfExerciseName[exerciseIndex],
            bestSet = "${maxWeight.toInt()} ${listOfWeightUnits[exerciseIndex]} x $maxReps ",
            weightUnit = listOfWeightUnits[exerciseIndex]
        )
//        insertExerciseNotes(sessionId = sessionId, exerciseIndex = exerciseIndex)
        workoutUseCases.insertSession(session)
    }
    fun String.isValidNumber(): Boolean {
        return toFloatOrNull() != null
    }

    private suspend fun insertExerciseNotes(sessionId: String, exerciseIndex: Int) {
        val listOfNotesCopy = listOfExerciseNotes.toList()
        listOfNotesCopy.forEachIndexed { _, item ->
            val note = Note(
                idForeign = sessionId,
                belongsTo = Note.SESSION,
                content = item.second
            )
            if (note.content.isNotEmpty() && exerciseIndex == item.first)
                workoutUseCases.insertNote(note)
        }
    }

    private suspend fun insertWorkoutNotes(workoutId: String) {
        val listOfNotesCopy = listOfWorkoutNotes.toList()
        listOfNotesCopy.forEachIndexed { _, item ->
            val note = Note(
                idForeign = workoutId,
                belongsTo = Note.WORKOUT,
                content = item
            )
            if (note.content.isNotEmpty())
                workoutUseCases.insertNote(note)
        }
    }

    fun insertWorkout() {
        viewModelScope.launch {
            isInserting = true
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
            val workoutId: String = workoutUseCases.insertWorkout(workout)
            val exerciseNamesCopy = listOfExerciseName.toList()
            exerciseNamesCopy.forEachIndexed { index, _ ->
                insertSessions(index, workoutId)
            }
            insertWorkoutNotes(workoutId = workoutId)
        }.invokeOnCompletion {
            clearAllData()
        }
    }

    fun toggleIsDone(setIndex: Int) {
        listOfIsDone[setIndex] = !listOfIsDone[setIndex]
        Log.d("Amar Problem", "addExerciseAndSets: ${listOfWeights[setIndex].first}")
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
        super.onCleared()
        clearAllData()
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
        listOfExerciseNotes.clear()
        listOfPrevious.clear()
        listOfExerciseNotes.clear()
        listOfWorkoutNotes.clear()
        listOfNoteId.clear()
        isInserting = false
    }

}

