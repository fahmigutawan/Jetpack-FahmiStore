package com.example.core_ui.layout

import android.annotation.SuppressLint
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.ExperimentalLayoutApi
import androidx.compose.foundation.layout.FlowRow
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.width
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.onSizeChanged
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp

@OptIn(ExperimentalLayoutApi::class)
@Composable
fun MyFlowRow(
    modifier: Modifier = Modifier.fillMaxWidth(),
    horizontalArrangement: Arrangement.Horizontal = Arrangement.Start,
    verticalArrangement: Arrangement.Vertical = Arrangement.Top,
    content: @Composable() (VobisFlowRowScope.() -> Unit)
) {
    val density = LocalDensity.current
    val eachPartitionWidth = remember { mutableStateOf(0.dp) }
    val scope = VobisFlowRowScope(eachPartitionWidth)
    content(scope)

    FlowRow(
        modifier = modifier.onSizeChanged {
            with(density) {
                eachPartitionWidth.value = it.width.toDp() / 12
            }
        },
        horizontalArrangement = horizontalArrangement,
        verticalArrangement = verticalArrangement
    ) {
        scope.contents.forEach {
            it()
        }
    }
}

@SuppressLint("ComposableNaming")
class VobisFlowRowScope(
    private val eachPartitionWidth: MutableState<Dp>
) {
    val contents = ArrayList<@Composable (() -> Unit)>()

    @Composable
    fun item(
        content: @Composable (() -> Unit)
    ) {
        contents.add(content)
    }

    @Composable
    fun item(
        /**
         * Params "partition" should be filled by the number between 1 and 12. If it's not, it will throw an Exception
         */
        partition: Int,
        content: @Composable (() -> Unit)
    ) {
        contents.add {
            Box(
                modifier = Modifier
                    .width((partition * eachPartitionWidth.value.value).dp)
            ) {
                content()
            }
        }
    }
}