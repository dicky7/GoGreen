package com.manut.gogreen.ui.register

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.manut.gogreen.data.source.remote.network.ApiConfig
import com.manut.gogreen.data.source.remote.response.ResponseUser
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class RegisterViewModel : ViewModel() {
    private val _isLoading = MutableLiveData<Boolean>()
    val isLoading: LiveData<Boolean> = _isLoading

    private val _toastText = MutableLiveData<String>()
    val toastText: LiveData<String> = _toastText



    fun registerUser(username : String, email :String, password: String){
        _isLoading.value = true
        val client = ApiConfig.getApiService().registerUser(username,email,password)
        client.enqueue(object  : Callback<ResponseUser> {
            override fun onResponse(call: Call<ResponseUser>, response: Response<ResponseUser>) {
                _isLoading.value = false
                when {
                    response.isSuccessful -> {
                        _toastText.value = "Registrasi Sukses!"
                    }
                    response.code()== 422 -> {
                        _toastText.value = "User sudah ada, silahkan masuk!"
                    }
                    else -> {
                        _toastText.value = "Registrasi gagal!"
                    }
                }
            }

            override fun onFailure(call: Call<ResponseUser>, t: Throwable) {
                _isLoading.value = false
                Log.e("STATUS_RESPONSE", "onFailure: ${t.message.toString()}")

            }
        })
    }
}