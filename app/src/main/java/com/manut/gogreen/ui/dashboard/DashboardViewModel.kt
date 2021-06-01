package com.manut.gogreen.ui.dashboard

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.manut.gogreen.data.source.remote.network.classification.ApiClassification
import com.manut.gogreen.data.source.remote.response.ResponseClassification
import okhttp3.MultipartBody
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class DashboardViewModel : ViewModel() {

    private val _isLoading = MutableLiveData<Boolean>()
    val isLoading: LiveData<Boolean> = _isLoading

    private val _textView = MutableLiveData<String>()
    val textView: LiveData<String> = _textView

    fun classificationImage(imgClass : MultipartBody.Part){
        _isLoading.value = true
        val client = ApiClassification.getApiService().classificationImage(imgClass)

        client.enqueue(object : Callback<ResponseClassification> {
            override fun onResponse(call: Call<ResponseClassification>, response: Response<ResponseClassification>) {
                if (response.code() == 200){
                    _isLoading.value =false
                    Log.d("STATUS_RESPONSE", "Upload Success! ${response.message()}")
                    _textView.value = "${response.body()?.result}"
                }
            }

            override fun onFailure(call: Call<ResponseClassification>, t: Throwable) {
                _isLoading.value =false
                Log.d("STATUS_RESPONSE", "Failed! ${t.message.toString()}")
            }
        })
    }

}