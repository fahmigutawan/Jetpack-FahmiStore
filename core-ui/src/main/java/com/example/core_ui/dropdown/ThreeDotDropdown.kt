package com.example.core_ui.dropdown

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.material3.DropdownMenu
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.unit.dp
import coil.compose.rememberAsyncImagePainter
import com.example.core_ui.R

@Composable
fun ThreeDotDropdown(
    modifier: Modifier = Modifier,
    expanded: Boolean,
    onExpandChange: (Boolean) -> Unit,
    content: DropdownFieldScope.() -> Unit
) {
    val scope = DropdownFieldScope()
    content(scope)

    Column(
        modifier = modifier,
        horizontalAlignment = Alignment.End
    ) {
        IconButton(
            onClick = { onExpandChange(!expanded) }
        ) {
            Icon(
                painter = rememberAsyncImagePainter(model = R.drawable.ic_threedot_vertical),
                contentDescription = ""
            )
        }

        DropdownMenu(
            modifier = Modifier
                .background(MaterialTheme.colorScheme.background)
                .shadow(elevation = 2.dp),
            expanded = expanded,
            onDismissRequest = {
                onExpandChange(false)
            }
        ) {
            scope.contents.forEach {
                it()
            }
        }
    }
}