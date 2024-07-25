package com.imrul.replog.feature_workout.presentation.screen_workout

import android.util.Log
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
    var listOfExerciseId = mutableStateListOf<Long>()
        private set

    var listOfNoteId = mutableStateListOf<Long>()
        private set

    init {
        viewModelScope.launch { observeDuration() }
    }

    private suspend fun observeDuration() {
        workoutUseCases.durationUseCase.elapsedTime.collect {
            elapsedTime = it
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

    private fun extractNumbers(data: String): Pair<Float, Float> {
        val parts = data.split(" ") // Split the string by whitespace

        // Try converting the first and last elements to float
        val firstNumber = parts.firstOrNull()?.toFloatOrNull() ?: 0.0f
        val lastNumber = parts.lastOrNull()?.toFloatOrNull() ?: 0.0f

        return Pair(firstNumber, lastNumber)
    }

    fun onRepValueChanged(
        setIndex: Int,
        content: String,
    ) {
        listOfReps[setIndex] = content
    }

//    fun addExerciseAndSets(name: String, exerciseId: Long) {
//        listOfExerciseName.add(name)
//        listOfExerciseId.add(exerciseId)
//        // loading previous session
//        viewModelScope.launch {
//
//            workoutUseCases.getLatestSessionByExerciseId(exerciseId = exerciseId)
//                .collect { session ->
//                    session?.sessionId?.let { sessionId ->
//                        launch {
//                            workoutUseCases.getNotesBySessionId(sessionId = sessionId)
//                                .collect { notes ->
//
//                                    val exerciseIndex = listOfExerciseName.size - 1
//                                    notes.forEach { note ->
//                                        listOfExerciseNotes.add(Pair(exerciseIndex, note.content))
//                                    }
//                                }
//                        }
//                        launch {
//                            workoutUseCases.getAllSetsBySessionId(sessionId = sessionId)
//                                .collect { listOfSets ->
//                                    val exerciseIndex = listOfExerciseName.size - 1
//                                    listOfSets.forEach { set ->
//                                        listOfWeights.add(Pair(exerciseIndex, ""))
//                                        listOfReps.add("")
//                                        listOfIsDone.add(false)
//                                        listOfTillFailure.add(set.setType == Set.SET_TYPE_FAILURE)
//                                        listOfPrevious.add("${set.weightValue} ${session.weightUnit} x ${set.reps.toInt()}")
//                                    }
//                                    listOfWeightUnits.add(session.weightUnit)
//                                }
//                        }
//
//                    }
//                    if (session == null) {
//                        listOfWeightUnits.add(Session.WEIGHT_UNIT_KG)
//                    }
//                }
//        }
//
//    }

    fun addExerciseAndSets(name: String, exerciseId: Long) {
        listOfExerciseName.add(name)
        listOfExerciseId.add(exerciseId)
        var exerciseIndex = listOfExerciseId.indexOf(exerciseId)

        // loading previous session
        viewModelScope.launch {

            workoutUseCases.getLatestSessionByExerciseId(exerciseId = exerciseId)
                .collect { session ->
                    session?.sessionId?.let { sessionId ->
                        launch {
                            workoutUseCases.getNotesBySessionId(sessionId = sessionId)
                                .collect { notes ->
                                    notes.forEach { note ->
                                        if (!listOfNoteId.contains(note.noteId)) {
                                            note.noteId?.let { listOfNoteId.add(it) }
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
                                        listOfWeights.add(Pair(exerciseIndex, ""))
                                        listOfReps.add("")
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

    fun removeExercise(exerciseId: Long) {
        var exerciseIndex = listOfExerciseId.indexOf(exerciseId)
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

        listOfNotesCopy = listOfExerciseNotes.toList()

        listOfNotesCopy.forEachIndexed { index, item ->
            if (item.first == exerciseIndex) {
                listOfNoteId.removeAt(index)
                removeExerciseNote(index = index)
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
                val weightString = listOfWeights[i].second
                val repString = listOfReps[i]
                if (weightString.isNotEmpty() && repString.isNotEmpty())
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
        insertExerciseNotes(sessionId = sessionId, exerciseIndex = exerciseIndex)
        workoutUseCases.insertSession(session)
    }

    fun insertExerciseNotes(sessionId: Long, exerciseIndex: Int) {
        val listOfNotesCopy = listOfExerciseNotes.toList()
        viewModelScope.launch {

            listOfNotesCopy.forEachIndexed { index, item ->
                val note = Note(
                    idForeign = sessionId,
                    belongsTo = Note.SESSION,
                    content = item.second
                )
                if (note.content.isNotEmpty() && exerciseIndex == item.first)
                    workoutUseCases.insertNote(note)
            }
        }
    }

    fun insertWorkoutNotes(workoutId: Long) {
        val listOfNotesCopy = listOfWorkoutNotes.toList()
        listOfNotesCopy.forEachIndexed { index, item ->
            val note = Note(
                idForeign = workoutId,
                belongsTo = Note.WORKOUT,
                content = item
            )
            viewModelScope.launch {
                if (note.content.isNotEmpty())
                    workoutUseCases.insertNote(note)
            }
        }
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
            insertWorkoutNotes(workoutId = workoutId)
//            insertExerciseNotes()
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
        listOfExerciseNotes.clear()
        listOfPrevious.clear()
        listOfExerciseNotes.clear()
        listOfWorkoutNotes.clear()
        listOfNoteId.clear()
    }

}

