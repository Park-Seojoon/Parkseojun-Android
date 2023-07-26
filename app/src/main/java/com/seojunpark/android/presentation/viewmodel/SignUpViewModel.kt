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
class SignUpViewModel @Inject constructor(
    private val mainUseCase: MainUseCase
) : ViewModel() {

    private val _doneEvent = MutableLiveData<Pair<Boolean, String>>()
    val doneEvent: LiveData<Pair<Boolean, String>> = _doneEvent

    var email = MutableLiveData<String>()
    var name = MutableLiveData<String>()
    var password = MutableLiveData<String>()
    var rePassword = MutableLiveData<String>()

    fun signUp() {
        val emailValue = email.value.toString()
        val nameValue = name.value.toString()
        val passwordValue = password.value.toString()
        val rePasswordValue = rePassword.value.toString()

        if (emailValue.isNullOrBlank() ||
            nameValue.isNullOrBlank() ||
            passwordValue.isNullOrBlank() ||
            rePasswordValue.isNullOrBlank()
        ) {
            _doneEvent.value = Pair(false, "모든 항목을 입력해주세요.")
            return
        } else if (passwordValue != rePasswordValue) {
            _doneEvent.value = Pair(false, "비밀번호가 올바르지 않습니다.\n" + "다시 입력해주세요.")
            return
        }

        viewModelScope.launch(Dispatchers.IO) {
            mainUseCase.signUp(nameValue, emailValue, passwordValue, rePasswordValue).also {
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