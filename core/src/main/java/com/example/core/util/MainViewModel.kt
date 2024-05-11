package com.example.core.util

import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.core.util.ConnectionState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

lateinit var mainViewModel: MainViewModel

@HiltViewModel
class MainViewModel @Inject constructor() : ViewModel() {
    val showBottomBar = mutableStateOf(false)
    val showSnackbar = mutableStateOf(false)
    val snackbarMsg = mutableStateOf("")
    val showLoading = mutableStateOf(false)

    private val _internetState = mutableStateOf<ConnectionState>(ConnectionState.Available)
    val internetState get() = _internetState

    suspend fun runInternetStateMonitor(
        onCheck: () -> ConnectionState
    ) {
        while (true) {
            delay(5000)
            val checker = onCheck()

            if (_internetState.value != checker) {
                _internetState.value = checker
            }
        }
    }
}