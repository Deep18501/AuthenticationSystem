package com.example.softwarelabassignment.presentation.components

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.softwarelabassignment.R
import com.example.softwarelabassignment.ui.theme.BgGrey
import com.example.softwarelabassignment.ui.theme.TextGrey

@Composable
fun CommonTextField(
    text: String,
    hint: String,
    icon: Int,
    onValueChange: (String) -> Unit
) {

    TextField(
        value = text,
        onValueChange = onValueChange,
        placeholder = {
            CustomText(value = hint)
        },
        textStyle = TextStyle(
            fontWeight = FontWeight(500),
            fontFamily = FontFamily(Font(R.font.be_vietnam_regular)),
            fontSize = 14.sp
        ),
        leadingIcon = {
            Image(
                painter = painterResource(id = icon),
                contentDescription = hint,
                Modifier.size(15.dp)
            )
        },
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 12.dp)
            .clip(RoundedCornerShape(8.dp))
            .background(BgGrey),
    )

}

