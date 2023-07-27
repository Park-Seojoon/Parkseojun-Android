package com.seojunpark.android.data.dto

data class MainResponse(
    val boardList: List<MainDTO>
)

data class MainDTO(
    val id: Long,
    val title: String,
    val point: Int,
    val url: String
)
