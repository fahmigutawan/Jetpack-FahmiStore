package com.example.base.components.card.loading

import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.background
import androidx.compose.foundation.basicMarquee
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.unit.dp
import coil.compose.AsyncImage
import com.example.core_ui.modifier.loading

@OptIn(ExperimentalFoundationApi::class)
@Composable
fun CategoryLoadingCard() {
    Box(
        modifier = Modifier
            .width(100.dp)
            .height(120.dp)
            .clickable {
                /*TODO*/
            }
    ) {
        Column(
            modifier = Modifier.fillMaxSize(),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Box(
                modifier = Modifier
                    .padding(12.dp)
                    .size(64.dp)
                    .clip(RoundedCornerShape(10.dp))
                    .loading()
            )
            Text(
                modifier = Modifier.padding(horizontal = 8.dp).loading(),
                text = "NAMA",
                maxLines = 1
            )
        }
    }
}