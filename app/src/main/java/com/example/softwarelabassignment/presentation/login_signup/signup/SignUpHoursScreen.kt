package com.example.softwarelabassignment.presentation.login_signup.signup

import android.util.Log
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
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
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Scaffold
import androidx.compose.material3.SnackbarHost
import androidx.compose.material3.SnackbarHostState
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import com.example.softwarelabassignment.R
import com.example.softwarelabassignment.data.model.BusinessHours
import com.example.softwarelabassignment.presentation.Screens
import com.example.softwarelabassignment.presentation.components.CustomText
import com.example.softwarelabassignment.presentation.components.FarmerEats
import com.example.softwarelabassignment.presentation.components.FinalButton
import com.example.softwarelabassignment.presentation.components.HandleUiEvents
import com.example.softwarelabassignment.presentation.components.TitleText
import com.example.softwarelabassignment.presentation.login_signup.AuthViewModel
import com.example.softwarelabassignment.ui.theme.BgGrey
import com.example.softwarelabassignment.ui.theme.BgOrange
import com.example.softwarelabassignment.ui.theme.BgYellow
import com.example.softwarelabassignment.ui.theme.TextGrey
import kotlinx.coroutines.launch

@Composable
fun SignUpHoursScreen(
    navController: NavController,
    viewModel: AuthViewModel = hiltViewModel()
) {
    val loadingState = viewModel.loadingState.collectAsState()
    val loginState = viewModel.loginState.collectAsState().value
    val snackbarHostState = remember { SnackbarHostState() }
    val coroutineScope = rememberCoroutineScope()


    val days = listOf("M", "T", "W", "Th", "F", "S", "Su")

    val timing = listOf(
        "8:00am - 10:00am",
        "10:00am - 1:00pm",
        "1:00pm - 4:00am",
        "4:00pm - 7:00pm",
        "7:00pm - 10:00pm",
    )

    var selectedDay = remember {
        mutableIntStateOf(0)
//        mutableStateListOf(
//        false, false, false, false, false, false, false )
    }

    var selectedHours = listOf(false, false, false, false, false)

    val values = remember {
        mutableStateOf(
            mutableListOf(
                selectedHours,
                selectedHours,
                selectedHours,
                selectedHours,
                selectedHours,
                selectedHours,
                selectedHours
            )
        )
    }



    HandleUiEvents(viewModel,snackbarHostState,coroutineScope,navController)


    LaunchedEffect(selectedDay) {
        println(selectedDay)
    }
    if (loginState) {
        navController.navigate(Screens.SignUpConformationScreen.route)
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
            CustomText(value = "Signup 4 of 4")
            TitleText(text = "Business Hours")
            Spacer(modifier = Modifier.height(40.dp))
            CustomText(value = "Choose the hours your farm is open for pickups. This will allow customers to order deliveries.")
            Spacer(modifier = Modifier.height(40.dp))

            Row(
                modifier = Modifier.fillMaxWidth(),
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                for (day in 0..6) {
                    var filled = false
                    for (fill in 0..4) {
                        Log.d(
                            "TextField",
                            "Current $fill ${values.value[selectedDay.intValue][fill]}"
                        )
                        if (values.value[day][fill]) {
                            filled = true
                            break
                        }
                    }
                    Log.d("TextField", "Current \n $filled")

                    WeekItem(
                        item = days[day],
                        selected = if (day == selectedDay.intValue) true else false,
                        filled = filled
                    ) {
                        if (selectedDay.intValue != day) {
                            selectedDay.intValue = day
                        }
                    }
                }
            }

            Spacer(modifier = Modifier.height(30.dp))

            HourseMaker(values.value, selectedDay.intValue, timing) {
                values.value[selectedDay.intValue] = it
//                Log.d("TextField", values.value.toString())
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
                    FinalButton(text = "Submit", loding = loadingState.value) {

                        val businessHours=BusinessHours()
                        businessHours.mon= hourList(values.value[0])
                        businessHours.tue= hourList(values.value[1])
                        businessHours.wed= hourList(values.value[2])
                        businessHours.thu= hourList(values.value[3])
                        businessHours.fri= hourList(values.value[4])
                        businessHours.sat= hourList(values.value[5])
                        businessHours.sun= hourList(values.value[6])

                        viewModel.update4(businessHours)

                        coroutineScope.launch {
                            viewModel.signUpUser()
                        }

                    }
                }
            }

        }


    }

}

fun hourList(list: List<Boolean>):List<String>?{
    val timing = listOf(
        "8:00am - 10:00am",
        "10:00am - 1:00pm",
        "1:00pm - 4:00am",
        "4:00pm - 7:00pm",
        "7:00pm - 10:00pm",
    )

    val hrLs= mutableListOf<String>()
    if (list[0]){ hrLs.add(timing[0])}
    if (list[1]){ hrLs.add(timing[1])}
    if (list[2]){hrLs.add(timing[2]) }
    if (list[3]){hrLs.add(timing[3]) }
    if (list[4]){hrLs.add(timing[4]) }
    if (hrLs.isEmpty()){
        return null
    }
    return hrLs.toList()
}

@Composable
fun HourseMaker(
    values: MutableList<List<Boolean>>,
    selectedDayT: Int,
    timing: List<String>,
    onChangesMade: (List<Boolean>) -> Unit
) {
    val selectedDay = remember {
        mutableIntStateOf(selectedDayT)
    }
    val boxSel = remember {
        mutableStateListOf<Boolean>(
            values[selectedDay.intValue][0],
            values[selectedDay.intValue][1],
            values[selectedDay.intValue][2],
            values[selectedDay.intValue][3],
            values[selectedDay.intValue][4]
        )
    }
    LaunchedEffect(selectedDayT) {
        selectedDay.intValue = selectedDayT
        boxSel[0] = values[selectedDayT][0]
        boxSel[1] = values[selectedDayT][1]
        boxSel[2] = values[selectedDayT][2]
        boxSel[3] = values[selectedDayT][3]
        boxSel[4] = values[selectedDayT][4]
    }

    Log.d("TextField", "Current ${selectedDay.intValue},${selectedDayT} , $values")

    Row(modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.SpaceBetween) {
        TimingItem(item = timing[0], selected = boxSel[0], width = .5f) {
            boxSel[0] = !(boxSel[0])
            values[selectedDay.intValue] = boxSel.toList()
            onChangesMade(boxSel.toList())
        }
        TimingItem(item = timing[1], selected = values[selectedDay.intValue][1]) {
            boxSel[1] = !(boxSel[1])
            values[selectedDay.intValue] = boxSel.toList()
            onChangesMade(boxSel.toList())
        }
    }
    Row(modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.SpaceBetween) {
        TimingItem(item = timing[2], selected = values[selectedDay.intValue][2], width = .5f) {
            boxSel[2] = !(boxSel[2])
            values[selectedDay.intValue] = boxSel.toList()
            onChangesMade(boxSel.toList())
        }
        TimingItem(item = timing[3], selected = values[selectedDay.intValue][3]) {
            boxSel[3] = !(boxSel[3])
            values[selectedDay.intValue] = boxSel.toList()
            onChangesMade(boxSel.toList())
        }
    }
    Row(modifier = Modifier.fillMaxWidth()) {
        TimingItem(item = timing[4], selected = values[selectedDay.intValue][4], width = .5f) {
            boxSel[4] = !(boxSel[4])
            values[selectedDay.intValue] = boxSel.toList()
            onChangesMade(boxSel.toList())
        }
    }
}


@Composable
fun WeekItem(item: String, selected: Boolean, filled: Boolean, onClick: () -> Unit) {
    Box(modifier = Modifier
        .height(36.dp)
        .width(37.dp)
        .clip(RoundedCornerShape(8.dp))
        .clickable {
            onClick()
        }
        .border(
            width = 1.dp,
            shape = RoundedCornerShape(8.dp),
            color = if (selected) {
                BgOrange
            } else BgGrey
        )
        .background(
            if (selected) {
                BgOrange
            } else if (filled) {
                BgGrey
            } else Color.Transparent
        ), contentAlignment = Alignment.Center) {

        Text(
            text = item,
            color = if (selected) {
                Color.White
            } else if (filled) {
                Color.Black
            } else TextGrey,
            fontWeight = FontWeight(400),
            fontSize = 14.sp,
            fontFamily = FontFamily(Font(R.font.be_vietnam_regular)),
            modifier = Modifier
        )
    }
}

@Composable
fun TimingItem(item: String, selected: Boolean, width: Float = 1f, onClick: () -> Unit) {
    Box(modifier = Modifier
        .fillMaxWidth(width)
        .padding(5.dp)
        .height(48.dp)
        .clip(RoundedCornerShape(8.dp))
        .clickable {
            onClick()
        }
        .background(
            if (selected) {
                BgYellow
            } else {
                BgGrey
            }
        ), contentAlignment = Alignment.Center) {

        Text(
            text = item,
            color = Color.Black,
            modifier = Modifier.padding(),
            fontWeight = FontWeight(400),
            fontSize = 14.sp,
            fontFamily = FontFamily(Font(R.font.be_vietnam_regular)),
        )
    }
}