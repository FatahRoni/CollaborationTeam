package com.example.collaborationteam.data.model

import org.json.JSONObject

data class AddUserResponse(
    val name : String,
    val job: String,
    val id: Int,
    val createdAt: String,
){
    constructor(data : JSONObject): this(
        name = data.optString("name","-"),
        job = data.optString("job","-"),
        id = data.optInt("id", -1),
        createdAt = data.optString("createdAt")
    )
}