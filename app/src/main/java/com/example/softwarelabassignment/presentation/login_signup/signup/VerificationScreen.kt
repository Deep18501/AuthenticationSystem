package com.example.softwarelabassignment.presentation.login_signup.signup

import android.content.Context
import android.net.Uri
import android.provider.OpenableColumns
import androidx.activity.compose.rememberLauncherForActivityResult
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
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Close
import androidx.compose.material3.Icon
import androidx.compose.material3.Scaffold
import androidx.compose.material3.SnackbarHost
import androidx.compose.material3.SnackbarHostState
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextDecoration
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
import com.example.softwarelabassignment.presentation.components.HandleUiEvents
import com.example.softwarelabassignment.presentation.components.TitleText
import com.example.softwarelabassignment.presentation.login_signup.AuthViewModel
import com.example.softwarelabassignment.ui.theme.BgGrey
import com.example.softwarelabassignment.ui.theme.BgOrange
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch
import java.io.File

@Composable
fun SignUpVerificationScreen(
    navController: NavController,
    viewModel: AuthViewModel = hiltViewModel()
) {
    val result = remember { mutableStateOf<Uri?>(null) }
    val launcher = rememberLauncherForActivityResult(ActivityResultContracts.OpenDocument()) {
        result.value = it
    }
    var documentName = ""

    val loadingState = viewModel.loadingState.collectAsState()
    val loginState = viewModel.loginState.collectAsState().value
    val snackbarHostState = remember { SnackbarHostState() }
    val coroutineScope = rememberCoroutineScope()

    result.value?.let { image ->
        val temp = getDocumentName(image, LocalContext.current)
        if (temp != null) {
            documentName = temp
        }
    }
    HandleUiEvents(viewModel,snackbarHostState,coroutineScope,navController)

    val prevDetails = viewModel.provideRegisterDetails()

    if (prevDetails != null) {
        if (prevDetails.registration_proof != "") {
                documentName = prevDetails.registration_proof
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
            CustomText(value = "Signup 3 of 4")
            TitleText(text = "Verification")
            Spacer(modifier = Modifier.height(40.dp))
            CustomText(value = "Attached proof of Department of Agriculture registrations i.e. Florida Fresh. USDA Approved,USDA Organic")
            Spacer(modifier = Modifier.height(40.dp))

            Row(
                modifier = Modifier.fillMaxWidth(),
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                CustomText(value = "Attach Proof of Registration")
                Box(modifier = Modifier
                    .size(53.dp)
                    .clip(
                        CircleShape
                    )
                    .clickable {
                        launcher.launch(arrayOf("application/pdf"))

                    }
                    .background(BgOrange), contentAlignment = Alignment.Center) {

                    Image(
                        painter = painterResource(id = R.drawable.ic_camera),
                        contentDescription = "Document Attach",
                        modifier = Modifier
                            .height(20.dp)
                            .width(24.dp)
                    )
                }

            }
            Spacer(modifier = Modifier.height(40.dp))


                if (documentName!=""){


                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceBetween,
                    verticalAlignment = Alignment.CenterVertically
                ) {

                    Text(
                        modifier = Modifier
                            .padding(16.dp),
                        text = documentName,
                        fontWeight = FontWeight(500),
                        fontSize = 14.sp,
                        textDecoration = TextDecoration.Underline,
                        fontFamily = FontFamily(Font(R.font.be_vietnam_regular)),
                        color = Color.Black
                    )
                    Icon(
                        imageVector = Icons.Default.Close,
                        contentDescription = "",
                        tint = Color.Black,
                        modifier = Modifier.clickable {
                            documentName=""
                            result.value = null

                        })


                }
            }


            Spacer(modifier = Modifier.weight(1f))
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(vertical = 52.dp), contentAlignment = Alignment.BottomStart
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

                        coroutineScope.launch {
                            viewModel.update3(documentName)
                            viewModel.navigationEvent(Screens.SignUpHoursScreen.route)
                        }

                    }
                }
            }

        }
    }
}

fun getDocumentName(uri: Uri, context: Context): String? {
    var documentName: String? = null
    if (uri.scheme == "content") {
        val cursor = context.contentResolver.query(uri, null, null, null, null)
        cursor?.use {
            if (it.moveToFirst()) {
                documentName = it.getString(it.getColumnIndex(OpenableColumns.DISPLAY_NAME))
            }
        }
    } else if (uri.scheme == "file") {
        documentName = uri.path?.let { File(it).name }
    }
    return documentName
}