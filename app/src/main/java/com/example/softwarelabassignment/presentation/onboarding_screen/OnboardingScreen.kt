package com.example.softwarelabassignment.presentation.onboarding_screen

import androidx.compose.animation.core.animateDpAsState
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.pager.HorizontalPager
import androidx.compose.foundation.pager.rememberPagerState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextDecoration
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import androidx.navigation.NavHostController
import com.example.softwarelabassignment.R
import com.example.softwarelabassignment.presentation.Screens
import com.example.softwarelabassignment.ui.theme.BgGreen
import com.example.softwarelabassignment.ui.theme.BgOrange
import com.example.softwarelabassignment.ui.theme.BgYellow

@OptIn(ExperimentalFoundationApi::class)
@Composable
fun OnboardingScreen(navController: NavController) {

    val pagerState = rememberPagerState(
        initialPage = 0,
        initialPageOffsetFraction = 0f
    ) {
        3
    }
    val pageList = listOf(
        SplashPages(
            "Quality",
            "Sell your farm fresh products directly to consumers. cutting out the middleman and reducing emissions of the global supply chain.",
            BgGreen,
            R.drawable.img_quality_01
        ),
        SplashPages(
            "Convenient",
            "Our team of delivery drivers will make sure your orders are picked up on time and promptly delivered to your customers.",
            BgOrange,
            R.drawable.img_convenient_02
        ),
        SplashPages(
            "Local",
            "We love the earth and know you do too! Join us in reducing our local carbon footprint one order at a time.",
            BgYellow,
            R.drawable.img_local_03
        )
    )

    val color=pageList[pagerState.currentPage].color
    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(color),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {

        HorizontalPager(state = pagerState) { page ->

            Image(
                modifier = Modifier
                    .fillMaxWidth()
                    .fillMaxHeight(.5f)
                    .padding(vertical = 25.dp),
                contentScale = ContentScale.FillBounds,
                painter = painterResource(id = pageList[page].pic),
                contentDescription = "Img"
            )
        }


        Column(
            modifier = Modifier
                .fillMaxSize()
                .clip(RoundedCornerShape(topStart = 49.dp, topEnd = 49.dp))
                .background(Color.White),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Text(
                modifier = Modifier.padding(30.dp),
                text = pageList[pagerState.currentPage].title,
                fontWeight = FontWeight(700),
                fontSize = 24.sp,
                fontFamily = FontFamily(Font(R.font.be_vietnam_regular))
            )
            Text(
                text = pageList[pagerState.currentPage].description,
                modifier = Modifier.padding(horizontal = 40.dp, vertical = 10.dp),
                fontWeight = FontWeight(400),
                textAlign = TextAlign.Center,
                fontSize = 14.sp,
                fontFamily = FontFamily(Font(R.font.be_vietnam_regular))

            )
            Spacer(modifier = Modifier.height(28.dp))
            PageIndicator(pageCount = 3, currentPage = pagerState.currentPage, modifier = Modifier)

            Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.BottomCenter) {

                Button(
                    onClick = {
                              navController.navigate(Screens.SignUpScreenMain.route)
                    },
                    modifier = Modifier
                        .padding(bottom = 80.dp),

                    colors = ButtonDefaults.buttonColors(color)
                ) {
                    Text(modifier = Modifier.padding(vertical =8.dp ),
                        text = "Join the movement!",
                        fontWeight = FontWeight(500),
                        textAlign = TextAlign.Center,
                        fontSize = 18.sp,
                        fontFamily = FontFamily(
                            Font(R.font.be_vietnam_regular)
                        )
                    )
                }

                Text(
                    modifier = Modifier
                        .padding(bottom = 40.dp)
                        .clickable {
                            navController.navigate(Screens.LoginScreenMain.route)
                        },
                    text = "Login",
                    fontWeight = FontWeight(500),
                    textAlign = TextAlign.Center,
                    fontSize = 14.sp,
                    fontFamily = FontFamily(
                        Font(R.font.be_vietnam_regular)
                    ),
                    textDecoration = TextDecoration.Underline
                )
            }

        }
    }
}

@Composable
fun PageIndicator(pageCount: Int, currentPage: Int, modifier: Modifier) {

    Row(
        horizontalArrangement = Arrangement.SpaceBetween,
        modifier = modifier
    ) {
        repeat(pageCount){
            IndicatorSingleDot(isSelected = it == currentPage )
        }


    }
}

@Composable
fun IndicatorSingleDot(isSelected: Boolean) {
    val width = animateDpAsState(targetValue = if (isSelected) 16.dp else 7.dp, label = "")
    Box(modifier = Modifier
        .padding(4.dp)
        .height(7.dp)
        .width(width.value)
        .clip(CircleShape)
        .background(if (isSelected) Color.Black else Color.Gray)
    )
}


