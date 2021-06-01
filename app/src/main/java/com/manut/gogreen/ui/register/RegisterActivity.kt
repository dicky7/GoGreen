package com.manut.gogreen.ui.register

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.activity.viewModels
import com.manut.gogreen.databinding.ActivityRegisterBinding

class RegisterActivity : AppCompatActivity() {
    private  lateinit var binding : ActivityRegisterBinding
    private val registerViewModel: RegisterViewModel by viewModels()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityRegisterBinding.inflate(layoutInflater)
        setContentView(binding.root)

        registerViewModel.isLoading.observe(this,{
            binding.progressBar.visibility = if (it) View.VISIBLE else View.GONE
        })
        registerViewModel.toastText.observe(this,{
            Toast.makeText(this, it, Toast.LENGTH_SHORT).show()
        })

        binding.btnRegister.setOnClickListener {
            val username = binding.edtUsername.text.toString().trim()
            val email = binding.edtEmail.text.toString().trim()
            val password = binding.edtPassword.text.toString().trim()
            registerViewModel.registerUser(username,email,password)
            finish()
        }

    }
}