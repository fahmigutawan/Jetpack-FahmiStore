package com.example.base.presentation.profile

import androidx.lifecycle.ViewModel
import com.example.core.data.auth.AuthRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class ProfileViewModel @Inject constructor(
    private val authRepository: AuthRepository
): ViewModel() {
    fun deleteAuthData(){
        authRepository.saveToken("")
    }
}