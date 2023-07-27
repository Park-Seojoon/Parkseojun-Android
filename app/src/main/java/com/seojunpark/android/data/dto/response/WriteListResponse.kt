package com.seojunpark.android.data.dto.response

data class WriteListResponse(
    val boardList: List<WriteListDTO>
)

data class WriteListDTO(
    val id: Long,
    val title: String,
    val point: Int,
    val myListIngType: String,
    val url: String
)
