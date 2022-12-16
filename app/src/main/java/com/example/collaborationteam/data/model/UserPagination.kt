package com.example.collaborationteam.data.model

import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class UserPagination(
    val data: List<User> = listOf(),
    val page: Int = 0,
    @Json(name = "per_page")
    val perPage: Int = 0,
    val total: Int = 0,
    @Json(name = "total_pages")
    val total_pages: Int = 0
)