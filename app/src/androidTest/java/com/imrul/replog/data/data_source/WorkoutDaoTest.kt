package com.imrul.replog.data.data_source

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.test.filters.SmallTest
import com.google.common.truth.Truth
import com.imrul.replog.core.Constants.TEST_DATABASE_NAME
import com.imrul.replog.feature_workout.data.data_source.WorkoutDao
import com.imrul.replog.feature_workout.data.data_source.WorkoutDatabase
import com.imrul.replog.feature_workout.domain.model.Exercise
import com.imrul.replog.feature_workout.domain.model.Set
import com.imrul.replog.feature_workout.domain.model.Workout
import dagger.hilt.android.testing.HiltAndroidRule
import dagger.hilt.android.testing.HiltAndroidTest
import kotlinx.coroutines.cancelAndJoin
import kotlinx.coroutines.launch
import kotlinx.coroutines.test.runTest
import org.junit.After
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import javax.inject.Inject
import javax.inject.Named

@HiltAndroidTest
@SmallTest
class WorkoutDaoTest {
//    @get:Rule
//    var hiltRule = HiltAndroidRule(this)
//
//    @get:Rule
//    var instantTaskExecutorRule = InstantTaskExecutorRule()
//
//    @Inject
//    @Named(TEST_DATABASE_NAME)
//    lateinit var database: WorkoutDatabase
//    private lateinit var dao: WorkoutDao
//
//    @Before
//    fun setup() {
//        hiltRule.inject()
//        dao = database.workoutDao()
//    }
//
//    @After
//    fun teardown() {
//        database.close()
//    }
//
//    @Test
//    fun insertSet() = runTest {
//        val sessionId = 10L
//        val set = Set(
//            sessionIdForeign = sessionId,
//        )
//        dao.insertSet(set)
//        var allSets: List<Set>? = null
//
//        val job = launch {
//            dao.getAllSetsBySessionId(sessionId).collect {
//                allSets = it
//            }
//        }
//
//        testScheduler.apply { runCurrent() }
//        job.cancelAndJoin()
//        Truth.assertThat(allSets?.contains(set))
//    }
//
//    @Test
//    fun insertExercise() = runTest {
//        val workoutId = 10L
//        val exercise = Exercise()
//        dao.insertExercise(exercise)
//        var allExercises: List<Exercise>? = null
//
//        val job = launch {
//            dao.getAllExercises().collect {
//                allExercises = it
//            }
//        }
//
//        testScheduler.apply { runCurrent() }
//        job.cancelAndJoin()
//        Truth.assertThat(allExercises?.contains(exercise))
//    }
//
//    @Test
//    fun insertWorkout() = runTest {
//        val workout = Workout()
//        dao.insertWorkout(workout)
//        var allWorkouts: List<Workout>? = null
//
//        val job = launch {
//            dao.getAllWorkouts().collect {
//                allWorkouts = it
//            }
//        }
//
//        testScheduler.apply { runCurrent() }
//        job.cancelAndJoin()
//        Truth.assertThat(allWorkouts?.contains(workout))
//    }
//
//    @Test
//    fun deleteSet() = runTest {
//        val sessionId = 10L
//        val set = Set(sessionIdForeign = sessionId)
//        dao.insertSet(set)
//        dao.deleteSet(set)
//        var allSets: List<Set>? = null
//
//        val job = launch {
//            dao.getAllSetsBySessionId(sessionId).collect {
//                allSets = it
//            }
//        }
//
//        testScheduler.apply { runCurrent() }
//        job.cancelAndJoin()
//        Truth.assertThat(allSets?.isEmpty())
//    }
//
//    @Test
//    fun deleteExercise() = runTest {
//        val workoutId = 10L
//        val exercise = Exercise()
//        dao.insertExercise(exercise)
//        dao.deleteExercise(exercise)
//        var allExercises: List<Exercise>? = null
//
//        val job = launch {
//            dao.getAllExercises().collect {
//                allExercises = it
//            }
//        }
//
//        testScheduler.apply { runCurrent() }
//        job.cancelAndJoin()
//        Truth.assertThat(allExercises?.isEmpty())
//    }
//
//    @Test
//    fun deleteWorkout() = runTest {
//        val workout = Workout()
//        dao.insertWorkout(workout)
//        dao.deleteWorkout(workout)
//        var allWorkouts: List<Workout>? = null
//
//        val job = launch {
//            dao.getAllWorkouts().collect {
//                allWorkouts = it
//            }
//        }
//
//        testScheduler.apply { runCurrent() }
//        job.cancelAndJoin()
//        Truth.assertThat(allWorkouts?.isEmpty())
//    }
//
//    @Test
//    fun getWorkoutById() = runTest {
//        val workoutId = 1L
//        val workout1 = Workout(id = workoutId)
//        dao.insertWorkout(workout1)
//        val workout2 = dao.getWorkoutById(workoutId)
//        Truth.assertThat(workout1 == workout2)
//    }


}