package com.example.core_ui.bottombar

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.material3.BottomAppBar
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.unit.dp

@Composable
fun MyBottomBar(
    currentRoute: String,
    onItemClick: (route: String) -> Unit,
    items: List<BottomBarItemData>
) {
    val eachItemWidth = LocalConfiguration.current.screenWidthDp / items.size

    BottomAppBar(
        containerColor = MaterialTheme.colorScheme.background
    ) {
        Row {
            items.forEach { item ->
                Box(
                    modifier = Modifier
                        .fillMaxHeight()
                        .width(eachItemWidth.dp)
                        .clickable {
                            onItemClick(item.route)
                        }
                ) {
                    Column(
                        modifier = Modifier.fillMaxSize(),
                        verticalArrangement = Arrangement.Center,
                        horizontalAlignment = Alignment.CenterHorizontally
                    ) {
                        Icon(
                            modifier = Modifier.size(24.dp),
                            imageVector = item.icon,
                            contentDescription = "",
                            tint = when {
                                currentRoute == item.route -> MaterialTheme.colorScheme.primary
                                else -> MaterialTheme.colorScheme.outline
                            }
                        )

                        Text(
                            modifier = Modifier.padding(top = 4.dp),
                            text = item.word,
                            color = when {
                                currentRoute == item.route -> MaterialTheme.colorScheme.primary
                                else -> MaterialTheme.colorScheme.outline
                            },
                            style = MaterialTheme.typography.labelMedium
                        )
                    }
                }
            }
        }
    }
}