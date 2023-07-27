package com.seojunpark.android.presentation.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.seojunpark.android.data.dto.response.ProfileResponse
import com.seojunpark.android.domain.usecase.MainUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.firstOrNull
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class ProfileViewModel @Inject constructor(
    private val mainUseCase: MainUseCase
) : ViewModel() {

    private val _userInfo = MutableStateFlow<ProfileResponse?>(null)
    val userInfo: StateFlow<ProfileResponse?> = _userInfo

    fun userInfo(accessToken: String) {
        viewModelScope.launch {
            val response = mainUseCase.userInfo(accessToken).firstOrNull()
            _userInfo.value = response
        }
    }
}
