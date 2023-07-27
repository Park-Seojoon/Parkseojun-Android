package com.seojunpark.android.data.dto.response

data class RequestResponse(
    val boardList: List<RequestDTO>
)

data class RequestDTO(
    val id: Long,
    val title: String,
    val point: Int,
    val url: String,
    val ingType: String
)
