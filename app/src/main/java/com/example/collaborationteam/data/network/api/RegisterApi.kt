package com.example.collaborationteam.data.network.api

import com.example.collaborationteam.data.model.LoginRegisterModel
import com.example.collaborationteam.data.model.RegisterResult
import com.example.collaborationteam.data.network.ResponseStatus
import com.example.collaborationteam.data.network.deserializeJson
import com.example.collaborationteam.data.network.mapFailedResponse
import com.example.collaborationteam.data.network.serialized
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import java.io.IOException

class RegisterApi {
    fun registerUser(email: String, password: String): Flow<ResponseStatus<RegisterResult>> = flow {
        val model = LoginRegisterModel(email, password)
        try {
            val result = NetworkClient
                .makeCallApi("/register?delay=100000", NetworkClient.METHOD.POST, model.serialized())
                .execute()
            val response = if (result.isSuccessful) {
                val data: RegisterResult = deserializeJson<RegisterResult>(result.body?.string() ?: "") ?: RegisterResult()
                ResponseStatus.Success(data)
            } else {
                mapFailedResponse(result)
            }
            emit(response)
            result.body?.close()
        } catch (e: IOException) {
            emit(ResponseStatus.Failed(-1, e.message.toString(), e))
        }
    }
}