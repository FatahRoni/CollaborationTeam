package com.example.collaborationteam.data.model

import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class LoginRegisterModel(
    val email: String?,
    val password: String?
)