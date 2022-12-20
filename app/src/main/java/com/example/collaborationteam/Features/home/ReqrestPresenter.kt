package com.example.collaborationteam.Features.home

import com.example.collaborationteam.data.model.AddUserModel
import com.example.collaborationteam.data.network.ResponseStatus
import com.example.collaborationteam.data.network.api.ReqresApi
import kotlinx.coroutines.*
import kotlin.coroutines.CoroutineContext

class ReqrestPresenter (
    private val view: ReqrestContract.View,
    private val api: ReqresApi,
    uiContext: CoroutineContext = Dispatchers.Main
): ReqrestContract.Presenter {
    private val supervisorJob: Job = SupervisorJob()
    private val scope = CoroutineScope(supervisorJob + uiContext)

    override fun onAttach() {
        getUsers()
        api.getError {
            scope.launch {
                when(it){
                    is ResponseStatus.Failed -> view.onError(it.message)
                    else -> {}
                }
            }
        }
    }

    override fun onDetach() {
        scope.cancel()
    }

    fun getUsers(page: Int = 1){
        view.onLoading()
        api.getUserPagination {
            scope.launch {
                when (it){
                    is ResponseStatus.Success -> view.onSuccessGetUser(it.data)
                    is ResponseStatus.Failed -> view.onError(it.message)
                }
                view.onFinishedLoading()
            }
        }
    }

    fun addUsers(name: String, job: String){
        view.onLoading()
        api.addUser(AddUserModel(name,job)){
            scope.launch {
                when (it){
                    is ResponseStatus.Success -> view.onSuccessAddUser()
                    is ResponseStatus.Failed -> view.onError(it.message)
                }
                view.onFinishedLoading()
            }
        }
    }
}