package com.example.softwarelabassignment.presentation.components

import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.softwarelabassignment.R

@Composable
fun FarmerEats() {
    Text(modifier = Modifier.padding(top =36.dp),
        text = "FarmerEats",
        fontWeight = FontWeight(400),
        fontSize = 16.sp,
        fontFamily = FontFamily(
            Font(R.font.be_vietnam_regular)
        )
    )

}