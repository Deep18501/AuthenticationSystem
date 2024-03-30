package com.example.softwarelabassignment.presentation.login_signup.signup

import android.util.Log
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Scaffold
import androidx.compose.material3.SnackbarHost
import androidx.compose.material3.SnackbarHostState
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import com.example.softwarelabassignment.R
import com.example.softwarelabassignment.presentation.Screens
import com.example.softwarelabassignment.presentation.components.CommonTextField
import com.example.softwarelabassignment.presentation.components.CustomText
import com.example.softwarelabassignment.presentation.components.FarmerEats
import com.example.softwarelabassignment.presentation.components.FinalButton
import com.example.softwarelabassignment.presentation.components.HandleUiEvents
import com.example.softwarelabassignment.presentation.components.TitleText
import com.example.softwarelabassignment.presentation.login_signup.AuthViewModel
import com.example.softwarelabassignment.ui.theme.BgGrey
import kotlinx.coroutines.launch

@Composable
fun FarmInfo(
    navController: NavController,
    viewModel: AuthViewModel = hiltViewModel()
) {

    var businessName by remember {
        mutableStateOf("")
    }
    var informalName by remember {
        mutableStateOf("")
    }
    var streetAddress by remember {
        mutableStateOf("")
    }
    var city by remember {
        mutableStateOf("")
    }
    var state by remember {
        mutableStateOf("")
    }
    var zipCode by remember {
        mutableStateOf("")
    }

    val registerDetails = viewModel.provideRegisterDetails()

    if (registerDetails != null) {

        if (registerDetails.business_name != "") {
            businessName = registerDetails.business_name
        }
        if (registerDetails.informal_name != "") {
            informalName = registerDetails.informal_name
        }
        if (registerDetails.address != "") {
            streetAddress = registerDetails.address
        }
        if (registerDetails.state!=""){
        state = registerDetails.state
        }
        if (registerDetails.city!=""){
        city = registerDetails.city
        }
        if (registerDetails.zip_code.toString() == "0") {
            zipCode = ""
        }else{
            zipCode=registerDetails.zip_code.toString()
        }
    }

    val loadingState = viewModel.loadingState.collectAsState()
    val loginState = viewModel.loginState.collectAsState().value
    val snackbarHostState = remember { SnackbarHostState() }
    val coroutineScope = rememberCoroutineScope()

    HandleUiEvents(viewModel,snackbarHostState,coroutineScope,navController)

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
            CustomText(value = "Signup 2 of 4")
            TitleText(text = "Farm Info")
            Spacer(modifier = Modifier.height(40.dp))


            CommonTextField(
                text = businessName,
                hint = "Business Name",
                icon = R.drawable.ic_tag
            ) {
                Log.d("TextField", "$it  $businessName")
                businessName = it
            }
            CommonTextField(
                text = informalName,
                hint = "Informal Name",
                icon = R.drawable.ic_smile
            ) {
                informalName = it
            }

            CommonTextField(
                text = streetAddress,
                hint = "Street Address",
                icon = R.drawable.ic_home
            ) {
                streetAddress = it
            }
            CommonTextField(
                text = city,
                hint = "City",
                icon = R.drawable.ic_location
            ) {
                city = it
            }
            Row {

                TextField(
                    value = state,
                    onValueChange = {
                        state = it
                    },
                    placeholder = {
                        CustomText(value = "State")
                    },
                    textStyle = TextStyle(
                        fontWeight = FontWeight(500),
                        fontFamily = FontFamily(Font(R.font.be_vietnam_regular)),
                        fontSize = 14.sp
                    ),
                    maxLines = 1,
                    trailingIcon = {
                        Image(
                            painter = painterResource(id = R.drawable.ic_down),
                            contentDescription = "",
                            Modifier
                                .size(15.dp)

                        )
                    },
                    modifier = Modifier
                        .width(126.dp)
                        .padding(vertical = 12.dp)
                        .clip(RoundedCornerShape(8.dp))
                        .background(BgGrey),
                )
                TextField(
                    value = zipCode,
                    onValueChange = {
                        zipCode = it
                    },
                    keyboardOptions = KeyboardOptions(
                        keyboardType = KeyboardType.Number
                    ),
                    maxLines = 1,
                    placeholder = {
                        CustomText(value = "Enter Zipcode")
                    },
                    textStyle = TextStyle(
                        fontWeight = FontWeight(500),
                        fontFamily = FontFamily(Font(R.font.be_vietnam_regular)),
                        fontSize = 14.sp
                    ),
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(vertical = 12.dp, horizontal = 16.dp)
                        .clip(RoundedCornerShape(8.dp))
                        .background(BgGrey),
                )

            }
            Spacer(modifier = Modifier.weight(1f))

            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(vertical = 52.dp), contentAlignment = Alignment.BottomCenter
            ) {
                Row(Modifier.fillMaxWidth(), verticalAlignment = Alignment.CenterVertically) {
                    Image(
                        painter = painterResource(id = R.drawable.bic_ack_arrow),
                        modifier = Modifier
                            .padding(end = 65.dp)
                            .clickable {
                                navController.popBackStack()
                            },
                        contentDescription = ""
                    )
                    FinalButton(text = "Continue", loding = loadingState.value) {
                        viewModel.update2(
                            businessName,
                            informalName,
                            streetAddress,
                            city,
                            state,
                            zipCode
                        )
                        coroutineScope.launch {
                            viewModel.navigationEvent(Screens.SignUpVerificationScreen.route)
                        }

                    }
                }
            }

        }
    }
}