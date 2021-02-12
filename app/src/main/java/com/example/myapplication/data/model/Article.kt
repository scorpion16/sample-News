package com.example.myapplication.data.model

import android.os.Parcelable
import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName
import kotlinx.android.parcel.Parcelize

/**
 * @author Richi on 2/10/2021 AD.
 */
@Parcelize
data class Article (
    @Expose
    @SerializedName("content")
    var content: String? = null,

    @Expose
    @SerializedName("publishedAt")
    var publishedAt: String? = null,

    @Expose
    @SerializedName("urlToImage")
    var urlToImage: String? = null,

    @Expose
    @SerializedName("url")
    var url: String? = null,

    @Expose
    @SerializedName("description")
    var description: String? = null,

    @Expose
    @SerializedName("title")
    var title: String? = null,

    @Expose
    @SerializedName("author")
    var author: String? = null,

    @Expose
    @SerializedName("source")
    var source: Source? = null

    ): Parcelable