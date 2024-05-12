package com.example.core_ui.bottombar

import androidx.compose.ui.graphics.vector.ImageVector

data class BottomBarItemData(
    val route: String,
    val icon: ImageVector,
    val word: String,
    val needAuth: Boolean
)