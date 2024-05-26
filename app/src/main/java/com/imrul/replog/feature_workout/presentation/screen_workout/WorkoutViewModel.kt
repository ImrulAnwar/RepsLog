package com.imrul.replog.feature_workout.presentation.screen_workout

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.navigation.NavHostController
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
    var listOfReps = mutableStateListOf<String>()
        private set

    var listOfIsDone = mutableStateListOf<Boolean>()
        private set
    var listOfTillFailure = mutableStateListOf<Boolean>()
        private set

    var listOfExercises = mutableStateListOf<String>()
        private set
    var listOfNotes = mutableStateListOf<String>()
        private set

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
                    weightValue = listOfWeights[i].second.toFloatOrNull() ?: 0f,
                    reps = listOfReps[i].toFloatOrNull() ?: 0f,
                    exerciseIdForeign = exerciseId
                )
                if (listOfIsDone[i])
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
    }

    fun toggleIsDone(setIndex: Int) {
        listOfIsDone[setIndex] = !listOfIsDone[setIndex]
    }

    fun toggleTillFailure(setIndex: Int) {
        listOfTillFailure[setIndex] = !listOfTillFailure[setIndex]
    }

    fun shouldInsertWorkout(): Boolean = workoutUseCases.shouldInsertWorkout(listOfIsDone)
}