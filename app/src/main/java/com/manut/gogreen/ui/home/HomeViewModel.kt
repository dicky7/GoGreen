package com.manut.gogreen.ui.home

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.manut.gogreen.data.source.remote.network.recommendation.ApiRecommendation
import com.manut.gogreen.data.source.remote.response.ResponseRecommendation
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class HomeViewModel : ViewModel() {

    private val listRecommendation = MutableLiveData<ArrayList<ResponseRecommendation>>()

    private val _isLoading = MutableLiveData<Boolean>()
    val isLoading: LiveData<Boolean> = _isLoading

    private val _toastText = MutableLiveData<String>()
    val toastText: LiveData<String> = _toastText

    fun setRecommendation(){
        _isLoading.value = true
        val lista = ApiRecommendation.getApiService().getRecommendation()
        lista.enqueue(object : Callback<ArrayList<ResponseRecommendation>> {
            override fun onResponse(call: Call<ArrayList<ResponseRecommendation>>, response: Response<ArrayList<ResponseRecommendation>>) {
             if (response.code() == 200){
                 _isLoading.value = false
                 val result = response.body()
                 result?.let {
                     listRecommendation.postValue(it)
                 }
             }else{
                 _isLoading.value = false
                 _toastText.value = "Gagal Load Item!"
             }
            }

            override fun onFailure(call: Call<ArrayList<ResponseRecommendation>>, t: Throwable) {
                Log.d("STATUS", " ${t.message}")
                _isLoading.value = false
            }
        })

    }

    fun getRecommend() : LiveData<ArrayList<ResponseRecommendation>> = listRecommendation
}