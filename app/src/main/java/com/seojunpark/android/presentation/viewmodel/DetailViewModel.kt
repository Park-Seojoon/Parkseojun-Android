package com.seojunpark.android.presentation.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.seojunpark.android.domain.usecase.MainUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.firstOrNull
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class DetailViewModel @Inject constructor(
    private val mainUseCase: MainUseCase
) : ViewModel() {

    fun request(accessToken: String, id: Long) {
        viewModelScope.launch {
            mainUseCase.request(accessToken, id)
        }
    }
}