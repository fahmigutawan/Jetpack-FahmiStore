package com.example.posworktest.util

import com.example.core.util.mainViewModel

object SnackbarHandler {
    fun showSnackbar(message: String) {
        mainViewModel.showSnackbar.value = false
        mainViewModel.snackbarMsg.value = message
        mainViewModel.showSnackbar.value = true
    }
}