package com.example.collaborationteam.Features.login

import android.content.DialogInterface
import android.content.Intent
import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.isVisible
import com.example.collaborationteam.Features.home.MainActivity
import com.example.collaborationteam.Features.register.RegisterActivity
import com.example.collaborationteam.Features.register.RegisterView
import com.example.collaborationteam.data.model.UserPagination
import com.example.collaborationteam.data.network.api.LoginApi
import com.example.collaborationteam.data.network.api.RegisterApi
import com.example.collaborationteam.databinding.ActivityLoginBinding

class LoginActivity : AppCompatActivity(), RegisterView {
    private lateinit var binding : ActivityLoginBinding
    private var presenter = LoginPresenter(RegisterApi(), LoginApi())
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityLoginBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.btnMasuk.setOnClickListener {
            presenter.login(
                binding.edtEmail.text.toString(),
                binding.edtPassword.text.toString()
            )
        }

        binding.txtLupaUsername.setOnClickListener {
            startActivity(Intent(this, RegisterActivity::class.java))
        }

        presenter.onAttach(this)
    }

    private fun validateInput() {
        binding.btnMasuk.isEnabled =
            binding.edtPassword.text.toString().isNotBlank() && binding.edtEmail.text.toString()
                .isNotBlank()
    }

    override fun onLoading() {
        binding.progressIndicator.isVisible = true
    }

    override fun onFinishedLoading() {
        binding.progressIndicator.isVisible = false
    }

    override fun onError(code: Int, message: String) {
        when(code){
            1 -> binding.edtPassword.error = message
            2 -> binding.edtPassword.error = message
            else -> binding.edtPassword.error = null
        }
    }

    override fun resetPasswordError() {
        null
    }

    override fun onErrorPassword(visible: Boolean, message: String) {
        null

    }

    private fun dialogClickListener(dialogInterface: DialogInterface, button: Int) {
        when (button) {
            DialogInterface.BUTTON_NEGATIVE -> {}
            DialogInterface.BUTTON_POSITIVE -> {}
            DialogInterface.BUTTON_NEUTRAL -> {}
        }
    }

    override fun onSuccessGetUser(user: UserPagination) {
        AlertDialog.Builder(this)
            .setMessage("user -> $user")
            .setPositiveButton("Ok", this::dialogClickListener)
            .setNegativeButton("Cancel", this::dialogClickListener)
            .create()
            .show()
    }

    override fun onSuccessLogin() {
        startActivity(Intent(this, MainActivity::class.java))
        Toast.makeText(this, "Success Login", Toast.LENGTH_SHORT).show()
        presenter.login("","")
    }

    override fun onSuccessRegister() {
        Toast.makeText(this, "Success Register", Toast.LENGTH_SHORT).show()
    }

    override fun onDestroy() {
        super.onDestroy()
        presenter.onDetach()
    }
}