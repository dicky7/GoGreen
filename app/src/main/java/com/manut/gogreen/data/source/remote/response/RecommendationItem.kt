package com.manut.gogreen.data.source.remote.response

import com.google.gson.annotations.SerializedName

data class RecommendationItem(
    @field:SerializedName("image")
    val image: String,

    @field:SerializedName("name")
    val name: String,

    @field:SerializedName("id")
    val id: Int,

    @field:SerializedName("desc")
    val desc: String
)