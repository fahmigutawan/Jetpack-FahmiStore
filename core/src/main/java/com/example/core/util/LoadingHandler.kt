package com.example.core.util

import com.example.core.util.mainViewModel

object LoadingHandler {
    fun show() {
        mainViewModel.showLoading.value = true
    }

    fun dismiss() {
        mainViewModel.showLoading.value = false
    }
}