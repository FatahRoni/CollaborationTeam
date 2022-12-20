package com.example.collaborationteam.Features.login

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.example.collaborationteam.R
import com.example.collaborationteam.databinding.ActivityLoginBinding

class LoginActivity : AppCompatActivity() {
    private lateinit var binding : ActivityLoginBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)
    }
}