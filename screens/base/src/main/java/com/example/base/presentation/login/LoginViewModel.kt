package com.example.base.presentation.login

import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import com.example.core.data.auth.AuthRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class LoginViewModel @Inject constructor(
    private val authRepository: AuthRepository
): ViewModel(){
    val username = mutableStateOf("")
    val password = mutableStateOf("")

    fun saveDummyToken(){
        authRepository.saveToken("DUMMY")
    }
}