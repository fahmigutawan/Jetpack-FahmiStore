package com.example.payment.components.bill_form

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.background
import androidx.compose.foundation.basicMarquee
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.Remove
import androidx.compose.material.icons.filled.Search
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.ElevatedCard
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.core.text.isDigitsOnly
import coil.compose.AsyncImage
import com.example.core.util.mainViewModel

@OptIn(ExperimentalFoundationApi::class)
@Composable
fun BillProductCard(
    name: String,
    image: String,
    price: String,
    quantity: Int,
    onQuantityChange: (Int) -> Unit
) {
    Row(
        horizontalArrangement = Arrangement.spacedBy(8.dp)
    ) {
        AsyncImage(
            modifier = Modifier
                .size(80.dp)
                .clip(RoundedCornerShape(12.dp))
                .background(Color.White),
            model = image,
            contentDescription = ""
        )

        Column(
            modifier = Modifier.weight(1f)
        ) {
            Text(
                text = name,
                style = MaterialTheme.typography.titleLarge
            )
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Text(
                    modifier = Modifier
                        .weight(1f)
                        .basicMarquee(Int.MAX_VALUE),
                    text = price,
                    style = MaterialTheme.typography.titleMedium,
                    color = MaterialTheme.colorScheme.primary,
                    maxLines = 1
                )

                BasicTextField(
                    modifier = Modifier.width(130.dp),
                    value = quantity.toString(),
                    onValueChange = {
                        if (it.isEmpty()) {
                            onQuantityChange(1)
                        } else {
                            if (it.isDigitsOnly() && ((it.toIntOrNull() ?: 0) > 0)) {
                                it.toIntOrNull()?.let(onQuantityChange)
                            }
                        }
                    }
                ) {
                    Box(
                        modifier = Modifier
                            .padding(horizontal = 10.dp)
                            .border(
                                border = BorderStroke(
                                    1.dp,
                                    color = Color.LightGray,
                                ),
                                shape = RoundedCornerShape(12.dp)
                            )
                    ) {
                        Row(
                            modifier = Modifier.padding(vertical = 8.dp),
                            verticalAlignment = Alignment.CenterVertically
                        ) {
                            Icon(
                                modifier = Modifier
                                    .size(24.dp)
                                    .weight(1f)
                                    .padding(start = 12.dp)
                                    .clickable(
                                        enabled = quantity > 1,
                                        onClick = { onQuantityChange(quantity - 1) }
                                    ),
                                imageVector = Icons.Default.Remove,
                                contentDescription = ""
                            )

                            Box(
                                modifier = Modifier
                                    .weight(1f),
                                contentAlignment = Alignment.Center
                            ) {
                                it()
                            }

                            Icon(
                                modifier = Modifier
                                    .size(24.dp)
                                    .weight(1f)
                                    .padding(end = 12.dp)
                                    .clickable(
                                        onClick = { onQuantityChange(quantity + 1) }
                                    ),
                                imageVector = Icons.Default.Add,
                                contentDescription = ""
                            )
                        }
                    }
                }
            }
        }
    }
}