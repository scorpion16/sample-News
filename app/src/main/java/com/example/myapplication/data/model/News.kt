package com.example.myapplication.data.model

import android.os.Parcelable
import com.google.gson.annotations.SerializedName
import kotlinx.android.parcel.Parcelize

@Parcelize data class News (
    @SerializedName("articles") var articles: List<Article>? = null,
    @SerializedName("totalResults") var totalResults : Int = 0,
    @SerializedName("status") var status: String? = null
) : Parcelable