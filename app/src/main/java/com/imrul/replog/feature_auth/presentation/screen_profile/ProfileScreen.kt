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
            .fillMaxSize()
            .padding(20.dp),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally,
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
}
