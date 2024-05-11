package com.example.core_ui.modifier

import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Shape
import androidx.compose.ui.unit.dp
import com.google.accompanist.placeholder.PlaceholderHighlight
import com.google.accompanist.placeholder.placeholder
import com.google.accompanist.placeholder.shimmer

fun Modifier.loading(
    shape: Shape = RoundedCornerShape(12.dp)
) = this
    .padding(vertical = 1.dp)
    .placeholder(
        color = Color.Gray.copy(alpha = .2f),
        shape = shape,
        visible = true,
        highlight = PlaceholderHighlight.shimmer(
            highlightColor = Color.DarkGray
        )
    )