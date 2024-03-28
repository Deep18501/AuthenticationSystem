package com.example.softwarelabassignment.presentation

sealed class Screens(val route: String) {
    data object SplashScreen: Screens("splash-screen")
    data object HomeScreen: Screens("home-screen")
    data object AuthScreen: Screens("auth-screen")
    data object LoginScreenMain: Screens("login-screen")
    data object LoginScreen: Screens("login1-screen")
    data object LoginForgotScreen: Screens("login2-screen")
    data object LoginOtpScreen: Screens("login3-screen")
    data object LoginResetScreen: Screens("login4-screen")
    data object SignUpScreenMain: Screens("signUp-screen")
    data object SignUpScreen: Screens("signUp1-screen")
    data object SignUpFarmScreen: Screens("signUp2-screen")
    data object SignUpVerificationScreen: Screens("signUp3-screen")
    data object SignUpHoursScreen: Screens("signUp4-screen")
    data object SignUpConformationScreen: Screens("signUp6-screen")


    fun withArgs(vararg args: String): String {
        return buildString {
            append(route)
            args.forEach { arg ->
                append("/$arg")
            }
        }
    }
}
