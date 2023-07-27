package com.seojunpark.android.presentation.viewmodel

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.seojunpark.android.domain.usecase.MainUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import okhttp3.MediaType.Companion.toMediaTypeOrNull
import okhttp3.MultipartBody
import okhttp3.RequestBody
import javax.inject.Inject

@HiltViewModel
class WriteViewModel @Inject constructor(
    private val mainUseCase: MainUseCase
) : ViewModel() {

    private val _doneEvent = MutableLiveData<Pair<Boolean, String>>()
    val doneEvent: LiveData<Pair<Boolean, String>> = _doneEvent

    var title = MutableLiveData<String>()
    var point = MutableLiveData<String>()
    var content = MutableLiveData<String>()

    fun write(accessToken: String, files: List<MultipartBody.Part>) {
        val titleValue = title.value.toString()
        val pointValue = point.value.toString()
        val contentValue = content.value.toString()

        val data = createDataRequestBody(titleValue, contentValue)

        if (titleValue.isNullOrBlank() ||
            pointValue.isNullOrBlank() ||
            contentValue.isNullOrBlank()
        ) {
            _doneEvent.value = Pair(false, "모든 항목을 입력해주세요.")
            return
        }

        viewModelScope.launch(Dispatchers.IO) {
            mainUseCase.write(accessToken, data, files).also {
                _doneEvent.postValue(Pair(true, "글 작성이 완료되었습니다."))
            }
            Log.d("move", accessToken + "\n" + data.toString() + "\n" + files.toString())
        }
    }

    private fun createDataRequestBody(title: String, content: String): RequestBody {
        // data를 JSON 형식의 문자열로 만듭니다.
        val json = """{
            "title": "$title",
            "content": "$content"
        }"""
        // JSON 문자열을 RequestBody로 변환하여 반환합니다.
        return RequestBody.create("application/json".toMediaTypeOrNull(), json)
    }
}