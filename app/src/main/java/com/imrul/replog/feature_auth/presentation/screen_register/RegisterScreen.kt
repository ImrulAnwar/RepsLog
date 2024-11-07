package com.imrul.replog.feature_auth.presentation.screen_register

import android.content.Context
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavHostController
import com.imrul.replog.R
import com.imrul.replog.core.Constants
import com.imrul.replog.feature_auth.presentation.components.EmailTextField
import com.imrul.replog.feature_auth.presentation.components.OtherAuthButton
import com.imrul.replog.feature_auth.presentation.components.PasswordTextField
import com.imrul.replog.ui.theme.Maroon70
import com.imrul.replog.ui.theme.WhiteCustom

@Composable
fun RegisterScreen(
    navController: NavHostController,
    viewModel: RegisterViewModel = hiltViewModel(),
    context: Context = LocalContext.current
) {
    val usernameText = viewModel.usernameText
    val emailText = viewModel.emailText
    val passwordText = viewModel.passwordText
    val confirmPasswordText = viewModel.confirmPasswordText
    val isRegistering = viewModel.isRegistering

    var passwordVisibility by remember { mutableStateOf(false) }

    val annotatedString = buildAnnotatedString {
        append(Constants.ALREADY_HAVE_AN_ACCOUNT)
        pushStyle(SpanStyle(fontWeight = FontWeight.Bold))
        append(Constants.SIGN_IN)
        pop()
    }

    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(color = WhiteCustom)
            .padding(top = 220.dp)
        // Set the background color here
    ) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(20.dp)
                .verticalScroll(rememberScrollState()),
            verticalArrangement = Arrangement.Top,
            horizontalAlignment = Alignment.CenterHorizontally,
        ) {
            EmailTextField(
                text = usernameText,
                onValueChange = { viewModel.onUsernameChanged(it) },
                label = Constants.USERNAME_PLACEHOLDER
            )
            Spacer(modifier = Modifier.height(20.dp))
            EmailTextField(
                text = emailText,
                onValueChange = { viewModel.onEmailChanged(it) },
                label = Constants.EMAIL_PLACEHOLDER
            )
            Spacer(modifier = Modifier.height(20.dp))
            PasswordTextField(
                password = passwordText,
                passwordVisibility = passwordVisibility,
                onPasswordChange = { viewModel.onPasswordChanged(it) },
                onPasswordVisibilityChange = { passwordVisibility = it },
                Constants.PASSWORD_PLACEHOLDER
            )
            Spacer(modifier = Modifier.height(20.dp))
            PasswordTextField(
                password = confirmPasswordText,
                passwordVisibility = passwordVisibility,
                onPasswordChange = { viewModel.onConfirmPasswordChanged(it) },
                onPasswordVisibilityChange = { passwordVisibility = it },
                Constants.CONFIRM_PASSWORD_PLACEHOLDER
            )
            Spacer(modifier = Modifier.height(20.dp))
            if (isRegistering) {
                CircularProgressIndicator(color = Maroon70)
            } else {
                Button(
                    onClick = {
                        viewModel.registerWithEmail(context, navController)
                    },
                    shape = RoundedCornerShape(16.dp),
                    colors = ButtonDefaults.buttonColors(
                        containerColor = Maroon70,
                        contentColor = WhiteCustom
                    )
                ) {
                    Text(text = Constants.SIGN_UP, fontSize = 14.sp)
                }
            }
            Spacer(modifier = Modifier.height(20.dp))
            Text(
                text = annotatedString,
                modifier = Modifier
                    .align(Alignment.CenterHorizontally)
                    .clickable {
                        navController.navigateUp()
                    },
                color = Maroon70
            )
        }
    }
}