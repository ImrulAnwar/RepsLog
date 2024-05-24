package com.imrul.replog.feature_workout.presentation

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.navigation.compose.rememberNavController
import com.imrul.replog.core.presentation.navigation.NavGraph
import com.imrul.replog.feature_workout.presentation.screen_workout.WorkoutScreen
import com.imrul.replog.ui.theme.RepLogTheme
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            RepLogTheme {
                val navController = rememberNavController()
                NavGraph(navController = navController)
            }
        }
    }
}
