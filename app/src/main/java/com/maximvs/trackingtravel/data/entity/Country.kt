package com.maximvs.trackingtravel.data.entity

import android.os.Parcelable
import com.google.gson.annotations.SerializedName
import kotlinx.parcelize.Parcelize

@Parcelize
data class Country(
    @SerializedName("id")
    val id: Int,
    @SerializedName("nameOfCountry")
    val name: String
    ) : Parcelable


