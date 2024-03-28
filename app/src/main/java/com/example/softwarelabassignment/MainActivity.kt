package com.example.softwarelabassignment

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.navigation
import androidx.navigation.compose.rememberNavController
import com.example.softwarelabassignment.presentation.HomeScreen
import com.example.softwarelabassignment.presentation.Screens
import com.example.softwarelabassignment.presentation.login_signup.AuthViewModel
import com.example.softwarelabassignment.presentation.login_signup.login.ForgotPasswordScreen
import com.example.softwarelabassignment.presentation.login_signup.login.LoginOtpScreen
import com.example.softwarelabassignment.presentation.login_signup.login.LoginResetScreen
import com.example.softwarelabassignment.presentation.login_signup.login.LoginScreen
import com.example.softwarelabassignment.presentation.login_signup.signup.FarmInfo
import com.example.softwarelabassignment.presentation.login_signup.signup.SignUpConformationScreen
import com.example.softwarelabassignment.presentation.login_signup.signup.SignUpHoursScreen
import com.example.softwarelabassignment.presentation.login_signup.signup.SignUpScreen
import com.example.softwarelabassignment.presentation.login_signup.signup.SignUpVerificationScreen
import com.example.softwarelabassignment.presentation.onboarding_screen.OnboardingScreen
import com.example.softwarelabassignment.ui.theme.SoftwareLabAssignmentTheme
import dagger.hilt.android.AndroidEntryPoint


@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            SoftwareLabAssignmentTheme {
                // A surface container using the 'background' color from the theme
                val navController = rememberNavController()
                val viewModel: AuthViewModel = hiltViewModel()
                NavHost(
                    navController = navController,
                    startDestination = Screens.SplashScreen.route
                ) {
                    composable(Screens.SplashScreen.route) {
                        OnboardingScreen(navController)
                    }

                    navigation(
                        startDestination = Screens.SignUpScreen.route,
                        Screens.SignUpScreenMain.route
                    ) {

                        composable(Screens.SignUpScreen.route) {
                            SignUpScreen(navController = navController, viewModel)
                        }
                        composable(Screens.SignUpFarmScreen.route) {
                            FarmInfo(navController = navController, viewModel)
                        }
                        composable(Screens.SignUpVerificationScreen.route) {
                            SignUpVerificationScreen(navController = navController, viewModel)
                        }
                        composable(Screens.SignUpHoursScreen.route) {
                            SignUpHoursScreen(navController, viewModel)
                        }

                        composable(Screens.SignUpConformationScreen.route) {
                            SignUpConformationScreen(navController = navController, viewModel)
                        }

                    }
                    navigation(
                        startDestination = Screens.LoginScreen.route,
                        route = Screens.LoginScreenMain.route
                    ) {
                        composable(Screens.LoginScreen.route) {
                            LoginScreen(navController = navController, viewModel)
                        }
                        composable(Screens.LoginForgotScreen.route) {
                            ForgotPasswordScreen(navController = navController, viewModel)
                        }
                        composable(Screens.LoginOtpScreen.route) {
                            LoginOtpScreen(navController = navController, viewModel)
                        }
                        composable(Screens.LoginResetScreen.route) {
                            LoginResetScreen(navController, viewModel)
                        }
                    }
                    composable(Screens.HomeScreen.route) {
                        HomeScreen(navController = navController, viewModel)
                    }
                }
            }
        }
    }
}

