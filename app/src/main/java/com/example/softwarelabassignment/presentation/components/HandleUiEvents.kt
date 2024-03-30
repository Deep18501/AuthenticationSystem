package com.example.softwarelabassignment.presentation.components

import android.util.Log
import androidx.compose.material3.SnackbarDuration
import androidx.compose.material3.SnackbarHostState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.navigation.NavController
import com.example.softwarelabassignment.common.UiEvents
import com.example.softwarelabassignment.presentation.login_signup.AuthViewModel
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch

@Composable
fun HandleUiEvents(viewModel: AuthViewModel,snackbarHostState:SnackbarHostState,coroutineScope:CoroutineScope,navController:NavController) {
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
}
