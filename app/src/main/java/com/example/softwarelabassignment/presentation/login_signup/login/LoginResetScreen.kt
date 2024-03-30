package com.example.softwarelabassignment.presentation.login_signup.login

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Scaffold
import androidx.compose.material3.SnackbarDuration
import androidx.compose.material3.SnackbarHost
import androidx.compose.material3.SnackbarHostState
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextDecoration
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavHostController
import com.example.softwarelabassignment.R
import com.example.softwarelabassignment.common.UiEvents
import com.example.softwarelabassignment.presentation.Screens
import com.example.softwarelabassignment.presentation.components.CommonTextField
import com.example.softwarelabassignment.presentation.components.CustomText
import com.example.softwarelabassignment.presentation.components.FarmerEats
import com.example.softwarelabassignment.presentation.components.FinalButton
import com.example.softwarelabassignment.presentation.components.HandleUiEvents
import com.example.softwarelabassignment.presentation.components.TitleText
import com.example.softwarelabassignment.presentation.login_signup.AuthViewModel
import com.example.softwarelabassignment.ui.theme.TextOrange
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch

@Composable
fun LoginResetScreen(navController: NavHostController, viewModel: AuthViewModel= hiltViewModel()) {

    val loadingState = viewModel.loadingState.collectAsState()
    val passwordState = viewModel.passwordState.value
    val rpasswordState = viewModel.rpasswordState.value

    val snackbarHostState = remember { SnackbarHostState() }
    val coroutineScope = rememberCoroutineScope()

    HandleUiEvents(viewModel,snackbarHostState,coroutineScope,navController)


    Scaffold(
        snackbarHost = {
            SnackbarHost(hostState = snackbarHostState)
        }

    ) { it ->
        print(it)

        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(horizontal = 30.dp)
                .verticalScroll(rememberScrollState()),
        ) {
            FarmerEats()
            Spacer(modifier = Modifier.height(90.dp))
            TitleText(text = "Reset Password")
            Spacer(modifier = Modifier.height(40.dp))
            Row {
                CustomText(value = "Remember your Password?")
                CustomText(
                    value = "Login",
                    color = TextOrange,
                    modifier = Modifier.clickable {
                        coroutineScope.launch {
                            viewModel.navigationEvent(Screens.LoginScreenMain.route)
                        }
                    })
            }

            CommonTextField(
                text = passwordState.text,
                hint = "Enter New Password",
                icon = R.drawable.ic_passlock
            ) {
                viewModel.setPasswordState(it)
            }
            CommonTextField(
                text =rpasswordState.text,
                hint = "Confirm New Password",
                icon = R.drawable.ic_passlock
            ) {
                viewModel.setrPasswordState(it)
            }

            FinalButton(text = "Submit", loding = loadingState.value) {

                coroutineScope.launch {
                    viewModel.changePassword()
                }

            }

        }
    }
}