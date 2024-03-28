package com.example.softwarelabassignment.presentation.login_signup.signup

import android.util.Log
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Scaffold
import androidx.compose.material3.SnackbarDuration
import androidx.compose.material3.SnackbarHost
import androidx.compose.material3.SnackbarHostState
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import com.example.softwarelabassignment.R
import com.example.softwarelabassignment.common.UiEvents
import com.example.softwarelabassignment.presentation.Screens
import com.example.softwarelabassignment.presentation.components.CommonTextField
import com.example.softwarelabassignment.presentation.components.CustomText
import com.example.softwarelabassignment.presentation.components.FarmerEats
import com.example.softwarelabassignment.presentation.components.FinalButton
import com.example.softwarelabassignment.presentation.components.SocialIcons
import com.example.softwarelabassignment.presentation.components.TitleText
import com.example.softwarelabassignment.presentation.login_signup.AuthViewModel
import com.example.softwarelabassignment.ui.theme.BgGrey
import com.example.softwarelabassignment.ui.theme.TextOrange
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch


@Composable
fun SignUpScreen(
    navController: NavController, viewModel: AuthViewModel = hiltViewModel()
) {
    val emailState = viewModel.emailState.value
    val fullNameState = viewModel.fullNameState.value
    val phoneState = viewModel.phoneState.value
    val passwordState = viewModel.passwordState.value
    val rpasswordState = viewModel.rpasswordState.value

    val loadingState = viewModel.loadingState.collectAsState()
    val loginState = viewModel.loginState.collectAsState().value
    val snackbarHostState = remember { SnackbarHostState() }
    val coroutineScope = rememberCoroutineScope()

    var passwordVisible by rememberSaveable { mutableStateOf(false) }

    LaunchedEffect(key1 = viewModel.eventFlow) {
        Log.d("AuthViewModel", "Login Started ${viewModel.eventFlow}")

        viewModel.eventFlow.collectLatest { event ->
            when (event) {
                is UiEvents.SnackbarEvent -> {
                    coroutineScope.launch {

                        snackbarHostState.showSnackbar(
                            message = event.message,
                            duration = SnackbarDuration.Short
                        )
                    }
                }

                is UiEvents.NavigateEvent -> {
                    navController.navigate(
                        event.route
                    )
                }
            }
        }
    }

    if (loginState) {
        navController.navigate(Screens.SplashScreen.route)
    }

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
            Spacer(modifier = Modifier.height(32.dp))
            CustomText(value = "Signup 1 of 4")
            TitleText(text = "Welcome!")
            Spacer(modifier = Modifier.height(40.dp))
            SocialIcons()
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 120.dp, vertical = 32.dp),
                horizontalArrangement = Arrangement.Center
            ) {

                CustomText(value = "or login with", fontSize = 10.sp)
            }

            Row {
                CustomText(value = "New Here? ")
                CustomText(
                    value = "Create account",
                    color = TextOrange,
                    modifier = Modifier.clickable {
                        coroutineScope.launch {
                            viewModel.navigationEvent(Screens.SignUpScreenMain.route)
                        }
                    })
            }

            CommonTextField(
                text = fullNameState.text,
                hint = "Full Name",
                icon = R.drawable.ic_person
            ) {
                viewModel.setFullNameState(it)
            }
            CommonTextField(
                text = emailState.text,
                hint = "Email Address",
                icon = R.drawable.ic_atrate_sign
            ) {
                viewModel.setEmailState(it)
            }

            CommonTextField(
                text = phoneState.text,
                hint = "Phone Number",
                icon = R.drawable.ic_phone
            ) {
                viewModel.setPhoneState(it)
            }

            TextField(
                value = passwordState.text,
                visualTransformation = if (passwordVisible) VisualTransformation.None else PasswordVisualTransformation(),
                onValueChange = {
                    viewModel.setPasswordState(it)
                },
                placeholder = {
                    CustomText(value = "Password")
                },
                textStyle = TextStyle(
                    fontWeight = FontWeight(500),
                    fontFamily = FontFamily(Font(R.font.be_vietnam_regular)),
                    fontSize = 14.sp
                ),
                leadingIcon = {
                    Image(
                        painter = painterResource(id = R.drawable.ic_passlock),
                        contentDescription = "Password",
                        Modifier
                            .size(15.dp)
                            .clickable {
                                passwordVisible = !passwordVisible
                            }
                    )
                },
                trailingIcon = {
                    CustomText(value = "Forgot", color = TextOrange, modifier = Modifier
                        .padding(14.dp)
                        .clickable {

                        })
                },
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(vertical = 12.dp)
                    .clip(RoundedCornerShape(8.dp))
                    .background(BgGrey),
            )

            TextField(
                value = rpasswordState.text,
                visualTransformation = PasswordVisualTransformation(),
                onValueChange = {
                    viewModel.setrPasswordState(it)
                },
                placeholder = {
                    CustomText(value = "Re-enter Password")
                },
                textStyle = TextStyle(
                    fontWeight = FontWeight(500),
                    fontFamily = FontFamily(Font(R.font.be_vietnam_regular)),
                    fontSize = 14.sp
                ),
                leadingIcon = {
                    Image(
                        painter = painterResource(id = R.drawable.ic_passlock),
                        contentDescription = "Re-enter Password",
                        Modifier
                            .size(15.dp)
                    )
                },
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(vertical = 12.dp)
                    .clip(RoundedCornerShape(8.dp))
                    .background(BgGrey),
            )
            Box(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(vertical = 52.dp),
                contentAlignment = Alignment.BottomStart
            ) {
                Row(Modifier.fillMaxWidth(), verticalAlignment = Alignment.CenterVertically) {
                    CustomText(value = "Login", modifier = Modifier
                        .padding(end = 65.dp)
                        .clickable {
                            coroutineScope.launch {
                                viewModel.navigationEvent(Screens.LoginScreenMain.route)
                            }
                        })
                    FinalButton(text = "Continue", loding = loadingState.value) {
                        coroutineScope.launch {
                            if (emailState.text.isEmpty() || fullNameState.text.isEmpty() || passwordState.text.isEmpty()) {
                                viewModel.snackBarEvent("Please Fill All fields")
                            } else if (passwordState.text != rpasswordState.text) {
                                viewModel.snackBarEvent("Password Don't match")
                                viewModel.setrPasswordState("")
                            } else {
                                viewModel.update1()
                                viewModel.navigationEvent(Screens.SignUpFarmScreen.route)
                            }
                        }
                    }
                }
            }

        }
    }
}