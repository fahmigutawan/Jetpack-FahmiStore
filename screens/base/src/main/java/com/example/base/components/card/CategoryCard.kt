package com.example.base.components.card

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

@OptIn(ExperimentalFoundationApi::class)
@Composable
fun CategoryCard(
    image: String,
    name: String
) {
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
            AsyncImage(
                modifier = Modifier
                    .padding(12.dp)
                    .size(64.dp)
                    .clip(RoundedCornerShape(10.dp))
                    .background(Color.LightGray),
                model = image,
                contentDescription = "",
                contentScale = ContentScale.FillBounds
            )
            Text(
                modifier = Modifier.padding(horizontal = 4.dp).basicMarquee(Int.MAX_VALUE),
                text = name,
                maxLines = 1
            )
        }
    }
}