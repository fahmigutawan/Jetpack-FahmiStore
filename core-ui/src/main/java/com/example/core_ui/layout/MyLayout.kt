package com.example.core_ui.layout

import android.annotation.SuppressLint
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.navigationBarsPadding
import androidx.compose.foundation.layout.statusBarsPadding
import androidx.compose.material.ExperimentalMaterialApi
import androidx.compose.material.pullrefresh.PullRefreshIndicator
import androidx.compose.material.pullrefresh.rememberPullRefreshState
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Snackbar
import androidx.compose.material3.SnackbarHost
import androidx.compose.material3.SnackbarHostState
import androidx.compose.material3.SnackbarResult
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import kotlinx.coroutines.delay

@OptIn(ExperimentalMaterialApi::class)
@SuppressLint("UnusedMaterial3ScaffoldPaddingParameter")
@Composable
fun MyLayout(
    modifier: Modifier = Modifier,
    showSnackbar: Boolean,
    onShowSnackbarChange:(Boolean) -> Unit,
    snackbarMsg: String,
    onSnackbarMsgChange:(String) -> Unit,
    loading: Boolean,
    onLoadingChange:(Boolean) -> Unit,
    content: @Composable () -> Unit
) {
    val snackbarHostState = remember {
        SnackbarHostState()
    }

    LaunchedEffect(key1 = showSnackbar) {
        if (showSnackbar) {
            snackbarHostState.currentSnackbarData?.dismiss()
            snackbarHostState.showSnackbar(
                message = snackbarMsg
            )
        } else {
            snackbarHostState.currentSnackbarData?.dismiss()
        }
    }

    LaunchedEffect(key1 = snackbarHostState.currentSnackbarData){
        delay(3000)
        onShowSnackbarChange(false)
        onSnackbarMsgChange("")
    }

    Scaffold(
        snackbarHost = {
            SnackbarHost(
                hostState = snackbarHostState
            ) {
                Snackbar(snackbarData = it)
            }
        }
    ) {
        Box(
            modifier = modifier
                .statusBarsPadding()
                .navigationBarsPadding()
        ) {
            content()
        }

        Box(
            modifier = Modifier
                .fillMaxWidth(),
            contentAlignment = Alignment.Center
        ) {
            PullRefreshIndicator(
                refreshing = loading,
                state = rememberPullRefreshState(
                    refreshing = loading,
                    onRefresh = {}
                )
            )
        }
    }
}