package com.example.collaborationteam.Features.login

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.example.collaborationteam.R
<<<<<<< HEAD
import com.example.collaborationteam.databinding.ActivityMainBinding
import com.example.collaborationteam.databinding.ActivityRegisterBinding

class LoginActivity : AppCompatActivity() {

    private lateinit var binding : ActivityMainBinding

=======
import com.example.collaborationteam.databinding.ActivityLoginBinding

class LoginActivity : AppCompatActivity() {
    private lateinit var binding : ActivityLoginBinding
>>>>>>> Dev-Roni
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
    }
}