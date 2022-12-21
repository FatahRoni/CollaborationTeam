package com.example.collaborationteam.data.network

import androidx.viewbinding.BuildConfig
import okhttp3.*
import okhttp3.RequestBody.Companion.toRequestBody
import okhttp3.logging.HttpLoggingInterceptor
import java.util.concurrent.TimeUnit

class NetworkClientReqres {
    companion object {
        const val BASE_URL = "https://reqres.in/api"


        private val headerInterceptor: Interceptor = Interceptor {
            val request = it.request().newBuilder()
            request
                .addHeader("Content-Type", "application/json")

            return@Interceptor it.proceed(request.build())
        }

        val client: OkHttpClient by lazy {
            OkHttpClient
                .Builder()
                .addInterceptor(NetworkClientReqres.headerInterceptor)
                .addInterceptor(
                    HttpLoggingInterceptor().apply {
                        level =
                            if (BuildConfig.DEBUG) HttpLoggingInterceptor.Level.BODY else HttpLoggingInterceptor.Level.NONE
                    }
                )
                .callTimeout(timeout = 5L, unit = TimeUnit.SECONDS)
                .connectTimeout(timeout = 2L, unit = TimeUnit.SECONDS)
                .build()
        }

        fun requestBuilder(
            endpoint: String,
            method: METHOD = METHOD.GET,
            jsonBody: String? = null
        ): Request {
            val request = Request
                .Builder()
                .url("$BASE_URL$endpoint")

            if (jsonBody != null)
                request.method(method.name, jsonBody.toRequestBody())

            return request.build()
        }

        fun makeCallApi(
            endpoint: String,
            method: METHOD = METHOD.GET,
            jsonBody: String? = null
        ): Call {
            val request = requestBuilder(endpoint, method, jsonBody)
            return client.newCall(request)
        }
    }

    enum class METHOD {
        GET,
        POST
    }
}