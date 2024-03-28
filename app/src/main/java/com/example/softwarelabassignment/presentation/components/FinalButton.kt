package com.example.softwarelabassignment.presentation.components

import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.softwarelabassignment.R
import com.example.softwarelabassignment.presentation.Screens
import com.example.softwarelabassignment.ui.theme.TextOrange


@Composable
fun FinalButton(
    modifier: Modifier = Modifier,
    text: String,
    loding: Boolean = false,
    onClick: () -> Unit
) {
    Button(
        onClick = onClick,
        modifier = modifier.fillMaxWidth(),
        enabled = if (loding) false else true,
        colors = ButtonDefaults.buttonColors(TextOrange)
    ) {
        if (loding) {
            CircularProgressIndicator()
        } else {

            Text(
                modifier = Modifier.padding(vertical = 8.dp),
                text = text,
                fontWeight = FontWeight(500),
                textAlign = TextAlign.Center,
                fontSize = 18.sp,
                fontFamily = FontFamily(
                    Font(R.font.be_vietnam_regular)
                )
            )
        }
    }
}