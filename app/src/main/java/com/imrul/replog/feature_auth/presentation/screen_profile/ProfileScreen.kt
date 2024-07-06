package com.imrul.replog.feature_auth.presentation.screen_profile

import android.content.Context
import android.widget.Toast
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavHostController
import com.google.firebase.auth.FirebaseAuth
import com.imrul.replog.core.Constants
import com.imrul.replog.core.presentation.components.MiniPlayer
import com.imrul.replog.feature_auth.presentation.screen_login.LoginViewModel
import com.imrul.replog.feature_workout.presentation.screen_workout.WorkoutViewModel

@Composable
fun ProfileScreen(
    navController: NavHostController,
    viewModel: LoginViewModel = hiltViewModel(),
    context: Context = LocalContext.current,
    workoutViewModel: WorkoutViewModel
) {
    Column(
        modifier = Modifier
            .fillMaxSize(),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally,
    ) {
        Column(
            modifier = Modifier
                .padding(20.dp)
                .weight(1f),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Top,
        ) {
            Button(onClick = {
                viewModel.signOut(context, navController = navController)
            }) {
                Text(text = Constants.SIGN_OUT)
            }

            Button(onClick = {
                Toast.makeText(
                    context,
                    FirebaseAuth.getInstance().currentUser?.email.toString() + "\n${viewModel.isLoggedIn}",
                    Toast.LENGTH_SHORT
                ).show()
            }) {
                Text(text = "currentUser")
            }
        }

        if (workoutViewModel.isWorkOutRunning)
            MiniPlayer(
                workoutViewModel = workoutViewModel,
                navController = navController
            )
    }
}
