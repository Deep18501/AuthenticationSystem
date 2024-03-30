package com.example.softwarelabassignment.presentation.components

import android.app.Activity.RESULT_OK
import android.content.Context
import android.util.Log
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.IntentSenderRequest
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.Image
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.example.softwarelabassignment.R
import com.example.softwarelabassignment.data.GoogleAuthUiClient
import com.example.softwarelabassignment.presentation.Screens
import com.example.softwarelabassignment.presentation.login_signup.AuthViewModel
import com.example.softwarelabassignment.ui.theme.BgGrey
import com.google.android.gms.auth.api.identity.Identity
import kotlinx.coroutines.launch

@Composable
fun SocialIcons(
   onClickGoogle: () -> Unit,
   onClickFb: () -> Unit
) {


    Row(modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.SpaceEvenly) {
        SingleIcon(R.drawable.ic_google){
            onClickGoogle()
        }
        SingleIcon(R.drawable.ic_apple_logo){

        }
        SingleIcon(R.drawable.ic_fb_logo){
onClickFb()
        }
    }
}


@Composable
fun SingleIcon(icon: Int,onClick:()->Unit) {
    Box(contentAlignment = Alignment.Center,
        modifier = Modifier
            .height(52.dp)
            .width(96.dp)
            .border(width = 1.dp, color = BgGrey, RoundedCornerShape(30.dp))
            .clickable {
                Log.d("AuthViewModel", " google clicked")
                onClick()
            }

    ) {
        Image(painter = painterResource(id = icon), contentDescription = null, modifier = Modifier.size(30.dp))
    }
}