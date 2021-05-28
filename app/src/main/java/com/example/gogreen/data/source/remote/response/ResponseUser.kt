package com.example.gogreen.data.source.remote.response

import com.google.gson.annotations.SerializedName

data class ResponseUser(

	@field:SerializedName("userId")
	val userId: String,

	@field:SerializedName("name")
	val name: String,

	@field:SerializedName("email")
	val email: String,

	@field:SerializedName("password")
	val password: String,

	@field:SerializedName("token")
	val token: String

)
