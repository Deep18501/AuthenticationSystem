package com.example.softwarelabassignment.presentation.login_signup.signup

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import com.example.softwarelabassignment.R
import com.example.softwarelabassignment.presentation.Screens
import com.example.softwarelabassignment.presentation.components.FinalButton
import com.example.softwarelabassignment.presentation.login_signup.AuthViewModel
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

@Composable
fun SignUpConformationScreen(
    navController: NavController,
    viewModel: AuthViewModel = hiltViewModel()
) {

    Column (horizontalAlignment = Alignment.CenterHorizontally, modifier = Modifier.padding(16.dp)){
        Image(painter = painterResource(id = R.drawable.img_done_registr), contentDescription = "", modifier = Modifier
            .height(237.dp)
            .width(326.dp)
            .padding(top = 185.dp, start = 32.dp, end = 32.dp), contentScale = ContentScale.Crop)
        Spacer(modifier = Modifier.weight(1f))
        FinalButton(text = "Got It!", modifier = Modifier.padding(30.dp)) {

            CoroutineScope(Dispatchers.Main).launch {
                navController.navigate(Screens.HomeScreen.route){
                    popUpTo(Screens.SignUpConformationScreen.route){
                        inclusive=true
                    }
                }
            }

        }
    }
}