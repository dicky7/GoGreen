package com.manut.gogreen.data.source.remote.response

import android.os.Parcelable
import com.google.gson.annotations.SerializedName
import kotlinx.parcelize.Parcelize

@Parcelize
data class ResponseRecommendation(
    @field:SerializedName("name")
    val name: String,

    @field:SerializedName("icon")
    val icon: String,

    @field:SerializedName("id")
    val id: Int
):Parcelable