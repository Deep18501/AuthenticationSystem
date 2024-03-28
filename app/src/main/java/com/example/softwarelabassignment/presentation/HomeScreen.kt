package com.example.softwarelabassignment.presentation

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import com.example.softwarelabassignment.presentation.components.FinalButton
import com.example.softwarelabassignment.presentation.login_signup.AuthViewModel
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

@Composable
fun HomeScreen(navController: NavController,viewModel: AuthViewModel= hiltViewModel()) {
    Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center){
        FinalButton(text = "Logout", modifier = Modifier.padding(20.dp)) {
            CoroutineScope(Dispatchers.Main).launch {

            viewModel.logout()
            }
            navController.navigate(Screens.LoginScreenMain.route){
                popUpTo(Screens.HomeScreen.route){
                    inclusive=true
                }
            }
        }
    }

}