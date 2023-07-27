package com.seojunpark.android.presentation.viewmodel

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.seojunpark.android.domain.usecase.MainUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.firstOrNull
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class DetailViewModel @Inject constructor(
    private val mainUseCase: MainUseCase
) : ViewModel() {

    private val _doneEvent = MutableLiveData<Pair<Boolean, String>>()
    val doneEvent: LiveData<Pair<Boolean, String>> = _doneEvent

    fun request(accessToken: String, id: Long) {
        viewModelScope.launch(Dispatchers.IO) {
            mainUseCase.request(accessToken, id).also {
                _doneEvent.postValue(
                    Pair(
                        it.first,
                        it.second
                    )
                )
            }

        }
    }
}