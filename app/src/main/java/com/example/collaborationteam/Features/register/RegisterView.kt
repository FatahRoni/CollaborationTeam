package com.example.collaborationteam.Features.register

import com.example.collaborationteam.data.model.UserPagination

interface RegisterView {
    fun onLoading()
    fun onFinishedLoading()
    fun onError(code: Int, message: String)
    fun onErrorPassword(visible: Boolean, message: String)
    fun resetPasswordError()
    fun onSuccessLogin(username: String, password: String)
    fun onSuccessGetUser(user: UserPagination)
    fun onSuccessRegister()
}