package com.example.softwarelabassignment.presentation.components

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import com.example.softwarelabassignment.R
import com.example.softwarelabassignment.ui.theme.BgGrey

@Composable
fun SocialIcons() {
    Row(modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.SpaceEvenly) {
        SingleIcon(R.drawable.ic_google)
        SingleIcon(R.drawable.ic_apple_logo)
        SingleIcon(R.drawable.ic_fb_logo)
    }
}

@Composable
fun SingleIcon(icon: Int) {
    Box(contentAlignment = Alignment.Center,
        modifier = Modifier.height(52.dp).width(96.dp)
            .border(width = 1.dp, color = BgGrey, RoundedCornerShape(30.dp))

    ) {
        Image(painter = painterResource(id = icon), contentDescription = null, modifier = Modifier.size(30.dp))
    }
}