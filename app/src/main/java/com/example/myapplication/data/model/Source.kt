package com.example.myapplication.data.model

import android.os.Parcelable
import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName
import kotlinx.android.parcel.Parcelize

@Parcelize data class Source (
    @Expose
    @SerializedName("name")
    var name: String? = null,

    @Expose
    @SerializedName("id")
    var id: String? = null

) : Parcelable