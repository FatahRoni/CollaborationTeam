package com.example.collaborationteam.Features.login

import com.example.collaborationteam.Features.register.RegisterView
import com.example.collaborationteam.data.network.ResponseStatus
import com.example.collaborationteam.data.network.api.CredentialApi
import com.example.collaborationteam.data.network.api.UserApi
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.SupervisorJob
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.flow.flowOn
import kotlinx.coroutines.launch
import kotlin.coroutines.CoroutineContext

class LoginPresenter(
    private val credentialApi: CredentialApi,
    private val userApi: UserApi,
    private val uiContext: CoroutineContext = Dispatchers.Main
) {
    companion object {
        const val PASSWORD_NOT_CONTAIN_LOWERCASE = 0
        const val PASSWORD_NOT_CONTAIN_NUMBER = 2
        const val PASSWORD_ERROR = 9
        const val USERNAME_ERROR = 10
    }

    private var view: RegisterView? = null
    private var job = SupervisorJob()
    private var scope = CoroutineScope(job + uiContext)

    fun onAttach(view: RegisterView) {
        this.view = view
    }

    fun onDetach() {
        this.view = null
    }

    fun validateCredential(userName: String, password: String) {
        view?.onLoading()
        val isPasswordValid =
            password.contains("[a-z]".toRegex())
                    && password.contains("[A-Z]".toRegex())
                    && password.contains("[0-9]".toRegex())
                    && password.length >= 8

        val isUsernameValid = userName.length > 5

        if (isPasswordValid && isUsernameValid) { view?.onSuccessLogin(username = "", password = "") }

        if (!isUsernameValid) { view?.onError(0,"invalid username") }
        if (!isPasswordValid) { view?.onError(1,"invalid password") }
        if (!isUsernameValid && !isPasswordValid) { view?.onError(2,"invalid username & password")}

        view?.onFinishedLoading()

    }

    fun register(email: String, password: String) {
        view?.onLoading()
        scope.launch {
            credentialApi
                .registerUser(email, password)
                .flowOn(Dispatchers.Default)
                .collectLatest {
                    when (it) {
                        is ResponseStatus.Success -> view?.onSuccessRegister()
                        is ResponseStatus.Failed -> view?.onError(it.code, it.message)
                    }
                    view?.onFinishedLoading()
                }
        }
    }


    fun getUser() {
        view?.onLoading()
        userApi.getUser {
            scope.launch {
                when(it) {
                    is ResponseStatus.Success -> view?.onSuccessGetUser(it.data)
                    is ResponseStatus.Failed -> view?.onError(it.code, it.message)
                }
                view?.onFinishedLoading()
            }
        }
    }
}