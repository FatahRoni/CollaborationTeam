package com.example.collaborationteam.Features.login

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.example.collaborationteam.R
import com.example.collaborationteam.databinding.ActivityMainBinding
import com.example.collaborationteam.databinding.ActivityRegisterBinding

class LoginActivity : AppCompatActivity() {

    private lateinit var binding : ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
    }
}