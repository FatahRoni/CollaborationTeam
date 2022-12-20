package com.example.collaborationteam.Features.home

import com.example.collaborationteam.data.model.User

interface ReqrestContract {

    interface Presenter{
        fun onAttach()
        fun onDetach()
    }

    interface View{
        fun onLoading()
        fun onFinishedLoading()
        fun onError(message: String)
        fun onSuccessGetUser(user: List<User>){}
        fun onSuccessAddUser(){}
    }
}