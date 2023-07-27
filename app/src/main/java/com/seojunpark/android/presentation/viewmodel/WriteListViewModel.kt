package com.seojunpark.android.presentation.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.seojunpark.android.data.dto.response.DetailResponse
import com.seojunpark.android.data.dto.response.MainResponse
import com.seojunpark.android.data.dto.response.WriteListResponse
import com.seojunpark.android.domain.usecase.MainUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.firstOrNull
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class WriteListViewModel @Inject constructor(
    private val mainUseCase: MainUseCase
) : ViewModel() {

    private val _writeListResponse = MutableStateFlow<WriteListResponse?>(null)
    val writeListResponse: StateFlow<WriteListResponse?> = _writeListResponse

    fun writeList(accessToken: String) {
        viewModelScope.launch {
            _writeListResponse.value = mainUseCase.writeList(accessToken).firstOrNull()
        }
    }

    private val _detailList = MutableStateFlow<DetailResponse?>(null)
    val detailList: StateFlow<DetailResponse?> = _detailList

    fun loadDetailList(accessToken: String, id: Long) {
        viewModelScope.launch {
            val response = mainUseCase.loadDetailList(accessToken, id).firstOrNull()
            _detailList.value = response
        }
    }
}