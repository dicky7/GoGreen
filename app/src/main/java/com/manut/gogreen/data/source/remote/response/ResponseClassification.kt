package com.manut.gogreen.data.source.remote.response

import com.google.gson.annotations.SerializedName

data class ResponseClassification(
	@field:SerializedName("result")
	val result: String
)
