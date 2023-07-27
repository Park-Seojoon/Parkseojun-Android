package com.seojunpark.android.presentation.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.seojunpark.android.data.dto.MainResponse
import com.seojunpark.android.domain.usecase.MainUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.firstOrNull
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class MainViewModel @Inject constructor(
    private val mainUseCase: MainUseCase
) : ViewModel() {

    private val _listResponse = MutableStateFlow<MainResponse?>(null)
    val listResponse: StateFlow<MainResponse?> = _listResponse

    fun loadList(accessToken: String) {
        viewModelScope.launch {
            _listResponse.value = mainUseCase.loadList(accessToken).firstOrNull()
        }
    }
}