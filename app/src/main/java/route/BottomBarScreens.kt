package route

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Email
import androidx.compose.material.icons.filled.Person
import androidx.compose.material.icons.outlined.Email
import androidx.compose.material.icons.outlined.Menu
import androidx.compose.material.icons.outlined.Person
import androidx.compose.ui.graphics.painter.Painter
import com.imrul.replog.R
import com.imrul.replog.core.Routes
import com.imrul.replog.core.Strings

sealed class BottomBarScreens(
    val route: Routes,
    val selectedIcon: Int,
    val unselectedIcon: Int,
    val title: String
) {
    data object WorkoutHistoryScreenObject : BottomBarScreens(
        route = Routes.ScreenWorkoutHistory,
        selectedIcon = R.drawable.icon_history,
        unselectedIcon = R.drawable.icon_history,
        title = Strings.HISTORY
    )

    data object MeasurementsScreenObject : BottomBarScreens(
        route = Routes.ScreenMeasurements,
        selectedIcon = R.drawable.icon_measure,
        unselectedIcon = R.drawable.icon_measure,
        title = Strings.MEASURE
    )

    data object ExercisesScreenObject : BottomBarScreens(
        route = Routes.ScreenExercises,
        selectedIcon = R.drawable.icon_exercises,
        unselectedIcon = R.drawable.icon_exercises,
        title = Strings.EXERCISE
    )

    data object ProfileScreenObject : BottomBarScreens(
        route = Routes.ScreenProfile,
        selectedIcon = R.drawable.icon_exercises,
        unselectedIcon = R.drawable.icon_exercises,
        title = Strings.PROFILE
    )

    data object RoutineScreenObject : BottomBarScreens(
        route = Routes.ScreenRoutine,
        selectedIcon = R.drawable.icon_routine,
        unselectedIcon = R.drawable.icon_routine,
        title = Strings.PROFILE
    )
}