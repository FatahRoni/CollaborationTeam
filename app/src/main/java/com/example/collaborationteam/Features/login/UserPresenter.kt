package com.example.collaborationteam.Features.login

import com.example.collaborationteam.data.model.AddUserModel
import com.example.collaborationteam.data.model.AddUserResponse
import com.example.collaborationteam.data.model.User
import com.example.collaborationteam.data.model.UserPagination
import com.example.collaborationteam.data.network.ResponseStatus
import com.example.collaborationteam.data.network.api.NetworkClient
import com.example.collaborationteam.data.network.deserializeJson
import com.example.collaborationteam.data.network.mapFailedResponse
import com.example.collaborationteam.data.network.serialized
import okhttp3.Call
import okhttp3.Callback
import okhttp3.Response
import org.json.JSONObject
import java.io.IOException

class UserPresenter {
    private val usersEndpoint = "/users"
    fun getUser(onResponse: (ResponseStatus<UserPagination>) -> Unit) {
        NetworkClient.client
            .newCall(
                NetworkClient.requestBuilder(usersEndpoint)
            )
            .enqueue(object : Callback {
                override fun onFailure(call: Call, e: IOException) {
                    onResponse.invoke(ResponseStatus.Failed(1, e.message.toString(), e))
                }

                override fun onResponse(call: Call, response: Response) {
                    if (response.isSuccessful) {
//                        val body = JSONObject(response.body?.string() ?: "")
                        val data = deserializeJson<UserPagination>(response.body?.string() ?: "")
                        data?.let {
                            onResponse.invoke(
                                ResponseStatus.Success(data)
                            )
                        }
                    } else {
                        onResponse.invoke(
                            mapFailedResponse(response)
                        )
                    }
                }
            })
    }

    fun getUserPagination(pages: Int = 1, onResponse: (ResponseStatus<List<User>>) -> Unit) {
        val endpoint = "$usersEndpoint${if (pages > 1) "?page=$pages" else ""}"
        val request = NetworkClient.requestBuilder(endpoint)
        NetworkClient.client
            .newCall(request)
            .enqueue(object : Callback {
                override fun onFailure(call: Call, e: IOException) {
                    onResponse.invoke(
                        ResponseStatus.Failed(
                            code = -1,
                            message = e.message.toString(),
                            throwable = e
                        )
                    )
                }

                override fun onResponse(call: Call, response: Response) {
                    if (response.isSuccessful) {
                        val userPagination = deserializeJson<UserPagination>(response.body?.string() ?: "") ?: UserPagination()
                        onResponse.invoke(
                            ResponseStatus.Success(
                                data = userPagination.data,
                                method = "GET",
                                status = true
                            )
                        )
                    } else {
                        onResponse.invoke(
                            mapFailedResponse(response)
                        )
                    }
                    response.body?.close()
                }
            })
    }

    fun getError(onResponse: (ResponseStatus<Nothing>) -> Unit) {
        NetworkClient.client
            .newCall(NetworkClient.requestBuilder("/unknown/23"))
            .enqueue(object: Callback {
                override fun onFailure(call: Call, e: IOException) {
                    onResponse.invoke(ResponseStatus.Failed(-1, e.message.toString(), e))
                }

                override fun onResponse(call: Call, response: Response) {
                    onResponse.invoke(ResponseStatus.Failed(-1, response.message))
                    response.body?.close()
                }
            })
    }

    fun addUser(data: AddUserModel, onResponse: (ResponseStatus<AddUserResponse>) -> Unit) {
        val request = NetworkClient.requestBuilder(
            usersEndpoint,
            NetworkClient.METHOD.POST,
            data.serialized()
        )
        NetworkClient.client
            .newCall(request)
            .enqueue(object: Callback {
                override fun onFailure(call: Call, e: IOException) {
                    onResponse.invoke(
                        ResponseStatus.Failed(1, e.message.toString(), e)
                    )
                }

                override fun onResponse(call: Call, response: Response) {
                    if (response.isSuccessful) {
                        onResponse.invoke(
                            ResponseStatus.Success(
                                AddUserResponse(
                                    JSONObject(
                                        response.body?.string().toString()
                                    )
                                )
                            )
                        )
                    } else {
                        onResponse.invoke(
                            ResponseStatus.Failed(response.code, "Failed")
                        )
                    }

                    response.body?.close()
                }
            })
    }

    companion object {
        private const val BASE_URL = "https://reqres.in/api"
    }

}