package com.example.core_ui.topbar

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ChevronLeft
import androidx.compose.material3.CenterAlignedTopAppBar
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBarColors
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun MyTopBar(
    onBackClick: () -> Unit,
    title: @Composable (() -> Unit) = {},
    modifier: Modifier = Modifier,
    colors: TopAppBarColors = TopAppBarDefaults.centerAlignedTopAppBarColors()
) {
    CenterAlignedTopAppBar(
        modifier = Modifier,
        navigationIcon = {
            IconButton(
                onClick = onBackClick
            ) {
                Icon(imageVector = Icons.Default.ChevronLeft, contentDescription = "")
            }
        },
        title = title,
        colors = colors
    )
}

@OptIn(ExperimentalMaterial3Api::class)
@Preview
@Composable
fun MyTopBarPreview(){
    MyTopBar(
        onBackClick = {},
        title = {
            Text(text = "Preview")
        }
    )
}