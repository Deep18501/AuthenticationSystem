package com.example.softwarelabassignment.presentation.login_signup.signup

import android.app.Activity
import android.os.Bundle
import android.util.Log
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.IntentSenderRequest
import androidx.activity.result.contract.ActivityResultContracts
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
import androidx.compose.material3.SnackbarHost
import androidx.compose.material3.SnackbarHostState
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.DisposableEffect
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
import androidx.compose.ui.platform.LocalContext
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
import com.example.softwarelabassignment.data.GoogleAuthUiClient
import com.example.softwarelabassignment.presentation.Screens
import com.example.softwarelabassignment.presentation.components.CommonTextField
import com.example.softwarelabassignment.presentation.components.CustomText
import com.example.softwarelabassignment.presentation.components.FarmerEats
import com.example.softwarelabassignment.presentation.components.FinalButton
import com.example.softwarelabassignment.presentation.components.HandleUiEvents
import com.example.softwarelabassignment.presentation.components.SocialIcons
import com.example.softwarelabassignment.presentation.components.TitleText
import com.example.softwarelabassignment.presentation.login_signup.AuthViewModel
import com.example.softwarelabassignment.presentation.components.SignInResult
import com.example.softwarelabassignment.presentation.components.UserData
import com.example.softwarelabassignment.ui.theme.BgGrey
import com.example.softwarelabassignment.ui.theme.TextOrange
import com.facebook.CallbackManager
import com.facebook.FacebookCallback
import com.facebook.FacebookException
import com.facebook.GraphRequest
import com.facebook.login.LoginManager
import com.facebook.login.LoginResult
import com.google.android.gms.auth.api.identity.Identity
import kotlinx.coroutines.launch


@Composable
fun SignUpScreen(
    navController: NavController, viewModel: AuthViewModel = hiltViewModel()
) {
    val context= LocalContext.current
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


    val googleAuthUiClient by lazy {
        GoogleAuthUiClient(
            context = context,
            oneTapClient = Identity.getSignInClient(context)
        )
    }


    val launcherGoogle = rememberLauncherForActivityResult(
        contract = ActivityResultContracts.StartIntentSenderForResult(),
        onResult = { result ->
            if(result.resultCode == Activity.RESULT_OK) {
                coroutineScope.launch {
                    val signInResult = googleAuthUiClient.signInWithIntent(
                        intent = result.data ?: return@launch
                    )
                    viewModel.onSocialRegisterInResult(signInResult,"google")
                }
            }
            else{
                coroutineScope.launch {
                    viewModel.snackBarEvent("Some Error occur")
                }
            }
        }
    )

//    facebook register
    val loginManager = LoginManager.getInstance()
    val callbackManager = remember { CallbackManager.Factory.create() }
    val launcherFb = rememberLauncherForActivityResult(
        loginManager.createLogInActivityResultContract(callbackManager, null)
    ) {
        // nothing to do. handled in FacebookCallback
    }
    DisposableEffect(Unit) {
        loginManager.registerCallback(callbackManager, object : FacebookCallback<LoginResult> {
            override fun onCancel() {
                // do nothing

            }

            override fun onError(error: FacebookException) {
                Log.e("AuthViewModel", "on error call$error")
            }

            override fun onSuccess(result: LoginResult) {
                Log.d("AuthViewModel", "on Success call$result")
                val requestFb = GraphRequest.newMeRequest(
                    result.accessToken
                ) { obj, response ->
                    if (obj != null) {
                    val signUpResult= SignInResult(
                        data = UserData(userId = result.accessToken.userId,username = obj.optString("name"), userEmail = obj.optString("email")),
                        errorMessage = null
                    )
                        viewModel.onSocialRegisterInResult(signUpResult,"facebook")
                    }


                }
                Log.d("AuthViewModel", "handleFacebookAccessToken:${result.accessToken}")

                val parameters = Bundle()
                parameters.putString("fields", "id,name,email,link")
                requestFb.parameters = parameters
                requestFb.executeAsync()
                Log.d("AuthViewModel", "req:${requestFb.tag}")

            }
        })

        onDispose {
            loginManager.unregisterCallback(callbackManager)
        }
    }


    HandleUiEvents(viewModel,snackbarHostState,coroutineScope,navController)

    if (loginState) {
        navController.navigate(Screens.HomeScreen.route)
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
            SocialIcons(onClickGoogle = {
                coroutineScope.launch {
                    val signInIntentSender = googleAuthUiClient.signIn()
                    launcherGoogle.launch(
                        IntentSenderRequest.Builder(
                            signInIntentSender ?: return@launch
                        ).build()
                    )
                }
            }, onClickFb = {
                launcherFb.launch(listOf("email", "public_profile"))
            })
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