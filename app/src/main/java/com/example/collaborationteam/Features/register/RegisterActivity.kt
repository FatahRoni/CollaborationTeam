package com.example.collaborationteam.Features.register

import android.content.DialogInterface
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import androidx.core.view.isVisible
import androidx.core.widget.addTextChangedListener
import com.example.collaborationteam.Features.login.LoginActivity
import com.example.collaborationteam.Features.login.LoginPresenter
import com.example.collaborationteam.data.model.UserPagination
import com.example.collaborationteam.data.network.api.RegisterApi
import com.example.collaborationteam.databinding.ActivityRegisterBinding

class RegisterActivity : AppCompatActivity(), RegisterView {

    private lateinit var binding : ActivityRegisterBinding
    private val presenter = LoginPresenter(RegisterApi())

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityRegisterBinding.inflate(layoutInflater)
        setContentView(binding.root)
        binding.btnRegister.setOnClickListener {
            presenter.validateCredential(
                binding.etUsername.text.toString(),
                binding.etPassword.text.toString()
            )
        }

        binding.ivBackDetail.setOnClickListener {
            startActivity(Intent(this, LoginActivity::class.java))
        }
        presenter.onAttach(this)

        binding.etPassword.addTextChangedListener {
            validateInput()
        }

        binding.etConfPassword.addTextChangedListener {
            validateInput()
        }

        binding.etUsername.addTextChangedListener {
            validateInput()
        }

    }

    private fun validateInput() {
        binding.btnRegister.isEnabled =
            binding.etPassword.text.toString().isNotBlank() && binding.etUsername.text.toString()
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
            1 -> binding.passwordInputLayout.error = message
            2 -> binding.passwordInputLayout.error = message
            else -> binding.passwordInputLayout.error = null
        }
        when(code){
            1 -> binding.passwordConfInputLayout.error = message
            2 -> binding.passwordConfInputLayout.error = message
            else -> binding.passwordConfInputLayout.error = null
        }
        when(code){
            0 -> binding.textInputLayout.error = message
            2 -> binding.textInputLayout.error = message
            else -> binding.textInputLayout.error = null
        }
    }

    override fun resetPasswordError() {
        binding.tvCheckValidate.isVisible = false
    }

    override fun onErrorPassword(visible: Boolean, message: String) {
        binding.tvCheckValidate.text = message
        binding.tvCheckValidate.isVisible = visible
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

    override fun onSuccessLogin(username: String, password: String) {
        startActivity(Intent(this, LoginActivity::class.java))
        Toast.makeText(this, "Success Login", Toast.LENGTH_SHORT).show()
        presenter.register(username, password)
    }

    override fun onSuccessRegister() {
        Toast.makeText(this, "Success Register", Toast.LENGTH_SHORT).show()
    }

    override fun onDestroy() {
        super.onDestroy()
        presenter.onDetach()
    }
}