package com.example.payment.components.card.loading

import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.background
import androidx.compose.foundation.basicMarquee
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import com.example.core_ui.modifier.loading

@OptIn(ExperimentalFoundationApi::class)
@Composable
fun ShipmentLoadingCard() {
    Box(
        modifier = Modifier
            .width(140.dp)
            .clip(RoundedCornerShape(12.dp))
            .loading()
    ) {
        Column(
            modifier = Modifier.padding(horizontal = 16.dp, vertical = 12.dp)
        ) {
            Text(
                modifier = Modifier
                    .basicMarquee(Int.MAX_VALUE)
                    .loading(),
                text = "MODE",
                maxLines = 1,
                style = MaterialTheme.typography.labelLarge,
                fontWeight = FontWeight.Bold
            )
            Text(
                modifier = Modifier
                    .basicMarquee(Int.MAX_VALUE)
                    .loading(),
                text = "RP XXX",
                maxLines = 1,
                style = MaterialTheme.typography.labelSmall
            )
        }
    }
}