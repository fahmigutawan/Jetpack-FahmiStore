package com.example.core.util

import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.core.data.auth.AuthRepository
import com.example.core.util.ConnectionState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

lateinit var mainViewModel: MainViewModel

@HiltViewModel
class MainViewModel @Inject constructor(
    private val authRepository: AuthRepository
) : ViewModel() {
    val showBottomBar = mutableStateOf(true)
    val showTopBar = mutableStateOf(true)
    val currentRoute = mutableStateOf("")

    val search = mutableStateOf("")
    val backClicked = mutableStateOf(false)

    val showSnackbar = mutableStateOf(false)
    val snackbarMsg = mutableStateOf("")
    val showLoading = mutableStateOf(false)

    private val _internetState = mutableStateOf<ConnectionState>(ConnectionState.Available)
    val internetState get() = _internetState

    val showShouldLoginPopup = mutableStateOf(false)

    fun isLogin() = authRepository.getToken().isNotEmpty()

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