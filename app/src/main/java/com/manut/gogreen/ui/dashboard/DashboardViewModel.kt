package com.manut.gogreen.ui.dashboard

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.manut.gogreen.data.source.remote.network.classification.ApiClassification
import com.manut.gogreen.data.source.remote.network.recommendation.ApiRecommendation
import com.manut.gogreen.data.source.remote.response.RecommendationItem
import com.manut.gogreen.data.source.remote.response.ResponseClassification
import okhttp3.MultipartBody
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class DashboardViewModel : ViewModel() {

    private val listItem = MutableLiveData<ArrayList<RecommendationItem>>()

    private val _isLoading = MutableLiveData<Boolean>()
    val isLoading: LiveData<Boolean> = _isLoading

    private val _textView = MutableLiveData<String>()
    val textView: LiveData<String> = _textView

    private val _toastText = MutableLiveData<String>()
    val toastText: LiveData<String> = _toastText

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

    fun setRecommendation(category : String){
        _isLoading.value = true
        val lista = ApiRecommendation.getApiService().getItemRecommendation(category)
        lista.enqueue(object : Callback<ArrayList<RecommendationItem>> {
            override fun onResponse(call: Call<ArrayList<RecommendationItem>>, response: Response<ArrayList<RecommendationItem>>) {
                if (response.code() == 200){
                    _isLoading.value = false
                    val result = response.body()
                    result?.let {
                        listItem.postValue(it)
                    }
                }else{
                    _isLoading.value = false
                    _toastText.value = "Gagal Load Item!"
                }
            }

            override fun onFailure(call: Call<ArrayList<RecommendationItem>>, t: Throwable) {
                Log.d("STATUS", " ${t.message}")
                _isLoading.value = false
            }
        })

    }

    fun getRecommend() : LiveData<ArrayList<RecommendationItem>> = listItem


}