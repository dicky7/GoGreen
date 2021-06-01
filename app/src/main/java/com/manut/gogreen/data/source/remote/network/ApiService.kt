package com.manut.gogreen.data.source.remote.network

import com.manut.gogreen.data.source.remote.response.ResponseClassification
import com.manut.gogreen.data.source.remote.response.ResponseUser
import okhttp3.MultipartBody
import retrofit2.Call
import retrofit2.http.*

interface ApiService {
    @FormUrlEncoded
    @POST("user/signup")
    fun registerUser(
        @Field("name") name: String,
        @Field("email") email: String,
        @Field("password") password: String
    ): Call<ResponseUser>


    @FormUrlEncoded
    @POST("user/login")
    fun loginUser(
        @Field("email") email: String,
        @Field("password") password: String
    ): Call<ResponseUser>


    @Multipart
    @POST("process")
    fun classificationImage(
        @Part img: MultipartBody.Part
    ): Call<ResponseClassification>


}