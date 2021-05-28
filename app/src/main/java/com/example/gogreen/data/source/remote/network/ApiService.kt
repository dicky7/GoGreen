package com.example.gogreen.data.source.remote.network

import com.example.gogreen.data.source.remote.response.ResponseUser
import retrofit2.Call
import retrofit2.http.Field
import retrofit2.http.FormUrlEncoded
import retrofit2.http.POST

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
}