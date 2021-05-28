package com.example.gogreen.ui.login

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.gogreen.data.source.remote.network.ApiConfig
import com.example.gogreen.data.source.remote.response.ResponseUser
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class LoginViewModel :ViewModel() {

    private val _isLoading = MutableLiveData<Boolean>()
    val isLoading: LiveData<Boolean> = _isLoading

    private val _toMainActivity = MutableLiveData<Boolean>()
    val toMainActivity: LiveData<Boolean> = _toMainActivity

    private val _toastText = MutableLiveData<String>()
    val toastText: LiveData<String> = _toastText

    fun userLogin(email : String, password:String){
        _isLoading.value = true
        val client = ApiConfig.getApiService().loginUser(email,password)
        client.enqueue(object  : Callback<ResponseUser> {
            override fun onResponse(call: Call<ResponseUser>, response: Response<ResponseUser>) {
                when {
                    response.code() == 200 -> {
                        _isLoading.value =false
                        _toastText.value = "Login Sukses!}"
                        _toMainActivity.value = true
                    }
                    response.code() == 401 -> {
                        _isLoading.value =false
                        _toastText.value = "Credentials tidak cocok.!"
                    }
                    else -> {
                        _toastText.value = "Login gagal!"

                    }
                }
            }

            override fun onFailure(call: Call<ResponseUser>, t: Throwable) {
                _isLoading.value =false
                Log.d("STATUS_RESPONSE", "Failed! ${t.message.toString()}")
            }
        })

    }

}
