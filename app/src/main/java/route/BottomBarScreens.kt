package route

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Email
import androidx.compose.material.icons.filled.Menu
import androidx.compose.material.icons.filled.Person
import androidx.compose.material.icons.outlined.Email
import androidx.compose.material.icons.outlined.Menu
import androidx.compose.material.icons.outlined.Person
import androidx.compose.ui.graphics.vector.ImageVector
import com.imrul.replog.core.Routes
import com.imrul.replog.core.Strings

sealed class BottomBarScreens(
    val route: Routes,
    val selectedIcon: ImageVector,
    val unselectedIcon: ImageVector,
    val title: String
) {
    data object WorkoutHistoryScreenObject : BottomBarScreens(
        route = Routes.ScreenWorkoutHistory,
        selectedIcon = Icons.Filled.Menu,
        unselectedIcon = Icons.Outlined.Menu,
        title = Strings.HISTORY
    )

    data object MeasurementsScreenObject : BottomBarScreens(
        route = Routes.ScreenMeasurements,
        selectedIcon = Icons.Filled.Email,
        unselectedIcon = Icons.Outlined.Email,
        title = Strings.MEASURE
    )

    data object ExercisesScreenObject : BottomBarScreens(
        route = Routes.ScreenExercises,
        selectedIcon = Icons.Filled.Email,
        unselectedIcon = Icons.Outlined.Email,
        title = Strings.EXERCISE
    )

    data object ProfileScreenObject : BottomBarScreens(
        route = Routes.ScreenProfile,
        selectedIcon = Icons.Filled.Person,
        unselectedIcon = Icons.Outlined.Person,
        title = Strings.PROFILE
    )
}