package com.manut.gogreen.ui.login

import android.content.Intent
import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import com.manut.gogreen.MainActivity
import com.manut.gogreen.R
import com.manut.gogreen.data.utils.SessionManager
import com.manut.gogreen.data.utils.UserRepository
import com.manut.gogreen.databinding.ActivityLoginBinding
import com.manut.gogreen.ui.register.RegisterActivity


class LoginActivity : AppCompatActivity(), View.OnClickListener {

    private  val loginViewModel: LoginViewModel by viewModels()
    private lateinit var binding: ActivityLoginBinding
    lateinit var userRepository: UserRepository

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityLoginBinding.inflate(layoutInflater)
        setContentView(binding.root)


        val sesi = SessionManager(this)
        userRepository = UserRepository.getInstance(sesi)


        loginViewModel.isLoading.observe(this, {
            binding.progressBar.visibility = if (it) View.VISIBLE else View.GONE
        })
        if (userRepository.isUserLogin()) {
            startActivity(Intent(this,MainActivity::class.java))
            finish()
        }

        loginViewModel.toastText.observe(this,{
            Toast.makeText(this, it, Toast.LENGTH_SHORT).show()
        })
        loginViewModel.toMainActivity.observe(this,{

                val toMain  =Intent(this,MainActivity::class.java); if (it) startActivity(toMain)
                finish()
        })

        binding.skipLogin.setOnClickListener(this)
        binding.btnRegister.setOnClickListener(this)
        binding.btnLogin.setOnClickListener(this)
    }

    override fun onClick(v: View?) {
        when(v?.id){
            R.id.btn_login ->{
                val email = binding.edtEmail.text.toString().trim()
                val password = binding.edtPassword.text.toString().trim()
                loginViewModel.userLogin(email,password)
                saveSession()
            }
            R.id.btn_register-> {
                startActivity(Intent(this@LoginActivity, RegisterActivity::class.java))

            }

            R.id.skip_login -> {
                startActivity(Intent(this@LoginActivity, MainActivity::class.java))
                finish()
            }
        }
    }
    //menyimpan info login ke sharedpreferences
    private fun saveSession() {
        userRepository.loginUser(binding.edtEmail.text.toString())
    }
}