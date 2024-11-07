package com.imrul.replog.feature_auth.presentation.screen_profile

import android.content.Context
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.painter.Painter
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavHostController
import com.imrul.replog.R
import com.imrul.replog.core.Constants
import com.imrul.replog.core.Routes
import com.imrul.replog.core.presentation.CustomButton
import com.imrul.replog.core.presentation.components.MiniPlayer
import com.imrul.replog.feature_auth.presentation.screen_login.LoginViewModel
import com.imrul.replog.feature_workout.presentation.screen_workout.WorkoutViewModel
import com.imrul.replog.ui.theme.Maroon70
import com.imrul.replog.ui.theme.WhiteCustom

@Composable
fun ProfileScreen(
    navController: NavHostController,
    viewModel: LoginViewModel = hiltViewModel(),
    context: Context = LocalContext.current,
    workoutViewModel: WorkoutViewModel,
    profileViewModel: ProfileViewModel = hiltViewModel()
) {
    val userName = profileViewModel.userName
    val userEmail = profileViewModel.userEmail
    val numberOfWorkouts = profileViewModel.numberOfWorkouts
    val isAnonymous = profileViewModel.isAnonymous

    LaunchedEffect(isAnonymous) {
        profileViewModel.setUserNameAndEmail()
    }
    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(WhiteCustom),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally,
    ) {
        Column(
            modifier = Modifier
                .padding(20.dp)
                .weight(1f),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center,
        ) {
            CircularImage(
                painter = painterResource(id = R.drawable.profile_image_placeholder),
                size = 150.dp
            )
            Spacer(modifier = Modifier.height(20.dp))
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(20.dp),
                contentAlignment = Alignment.Center
            ) {
                Column(
                    modifier = Modifier.border(
                        width = 1.dp,
                        // Add border with rounded corners here
                        shape = MaterialTheme.shapes.medium,
                        color = Maroon70,
                    ),
                    verticalArrangement = Arrangement.Center,
                    horizontalAlignment = Alignment.CenterHorizontally,
                ) {
                    Spacer(modifier = Modifier.height(20.dp))
                    Text(
                        text = userName,
                        modifier = Modifier.padding(20.dp, 0.dp)
                    )
                    Spacer(modifier = Modifier.height(20.dp))
                    Text(
                        text = "$numberOfWorkouts Workouts",
                        modifier = Modifier.padding(20.dp, 0.dp)
                    )
                    Spacer(modifier = Modifier.height(20.dp))
                    Text(
                        text = userEmail,
                        modifier = Modifier.padding(20.dp, 0.dp)
                    )
                    Spacer(modifier = Modifier.height(20.dp))
                }

            }
            if (isAnonymous) Text(
                text = Constants.LINK_ACCOUNT,
                style = TextStyle(
                    fontWeight = FontWeight.Bold
                ),
                modifier = Modifier.clickable {
                    navController.navigate(Routes.ScreenLinkAccount)
                }
            )
            Spacer(modifier = Modifier.height(20.dp))
            if (viewModel.isSigningOut) {
                CircularProgressIndicator(color = Maroon70)
            } else {
                CustomButton(
                    onClick = {
                        workoutViewModel.clearAllData()
                        viewModel.signOut(context, navController)
                    },
                    modifier = Modifier.padding(top = 20.dp),
                    text = Constants.SIGN_OUT
                )
            }
        }

        if (workoutViewModel.isWorkOutRunning)
            MiniPlayer(
                workoutViewModel = workoutViewModel,
                navController = navController
            )
    }
}

@Composable
fun CircularImage(
    painter: Painter,
    modifier: Modifier = Modifier,
    size: Dp
) {
    Image(
        painter = painter,
        contentDescription = null,
        modifier = modifier
            .size(size)
            .clip(shape = CircleShape),
        contentScale = ContentScale.Crop,
    )
}
