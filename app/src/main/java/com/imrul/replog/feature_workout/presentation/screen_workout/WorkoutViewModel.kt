package com.imrul.replog.feature_workout.presentation.screen_workout

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.imrul.replog.feature_workout.domain.model.Exercise
import com.imrul.replog.feature_workout.domain.model.Workout
import com.imrul.replog.feature_workout.domain.use_cases.WorkoutUseCases
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject
import com.imrul.replog.feature_workout.domain.model.Set


@HiltViewModel
class WorkoutViewModel @Inject constructor(
    private val workoutUseCases: WorkoutUseCases
) : ViewModel() {
    var workoutTitle by mutableStateOf("Workout Title")
        private set

    fun onWorkoutTitleChanged(value: String) {
        workoutTitle = value
    }

    var listOfWeights = mutableStateListOf<Pair<Int, String>>()
        private set
    var listOfReps = mutableStateListOf<Pair<Int, String>>()
        private set

    //    var listOfWeight = mutableStateListOf<Pair<String, String>>()
    var listOfExercises = mutableStateListOf<String>()
        private set
    var listOfNotes = mutableStateListOf<String>()
        private set

    fun addSet(first: String, second: String, exerciseIndex: Int? = null) {
        exerciseIndex?.let {
            listOfWeights.add(Pair(it, ""))
            listOfReps.add(Pair(it, ""))
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
        listOfReps[setIndex] = Pair(
            first = listOfReps[setIndex].first,
            second = content
        )
    }

    fun addExercise() {
        listOfExercises.add("Exercise Name")
        listOfNotes.add("")
    }

    fun onNoteValueChanged(
        exerciseIndex: Int,
        content: String
    ) {
        listOfNotes[exerciseIndex] = content
    }

    fun onExerciseNameValueChanged(
        exerciseIndex: Int,
        content: String
    ) {
        listOfExercises[exerciseIndex] = content
    }

    private suspend fun insertExercises(exerciseIndex: Int, workoutId: Long) {
        // exercise id ta exercise insert korar por pabo. workout id ta use kore

        val exercise = Exercise(
            name = listOfExercises[exerciseIndex],
            note = listOfNotes[exerciseIndex],
            workoutIdForeign = workoutId
        )
        val exerciseId = workoutUseCases.insertExercise(exercise)
        listOfWeights.forEachIndexed { i, item ->
            if (item.first == exerciseIndex) {
                //this set belongs to the exercise
                val set = Set(
                    weightValue = listOfWeights[i].second.toFloat(),
                    reps = listOfReps[i].second.toFloat(),
                    exerciseIdForeign = exerciseId
                )
                workoutUseCases.insertSet(set)
            }
        }
    }

    fun insertWorkout() {
        viewModelScope.launch {
            val workout = Workout(
                name = workoutTitle
            )
            val workoutId: Long = workoutUseCases.insertWorkout(workout)

            listOfExercises.forEachIndexed { index, item ->
                insertExercises(index, workoutId)
            }
        }
        // workout insert korle workout id peye jabo


    }
}