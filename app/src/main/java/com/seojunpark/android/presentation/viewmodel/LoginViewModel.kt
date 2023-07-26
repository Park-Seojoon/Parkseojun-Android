package com.seojunpark.android.presentation.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.seojunpark.android.domain.usecase.MainUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class LoginViewModel @Inject constructor(
    private val mainUseCase: MainUseCase
) : ViewModel() {

    private val _doneEvent = MutableLiveData<Pair<Boolean, String>>()
    val doneEvent: LiveData<Pair<Boolean, String>> = _doneEvent

    var email = MutableLiveData<String>()
    var password = MutableLiveData<String>()

    fun login() {
        val emailValue = email.value.toString()
        val passwordValue = password.value.toString()

        if (emailValue.isNullOrBlank() ||
            passwordValue.isNullOrBlank()
        ) {
            _doneEvent.value = Pair(false, "모든 항목을 입력해주세요.")
            return
        }

        viewModelScope.launch(Dispatchers.IO) {
            mainUseCase.login(emailValue, passwordValue).also {
                _doneEvent.postValue(
                    Pair(
                        true,
                        "로그인 성공"
                    )
                )
            }
        }
    }
}