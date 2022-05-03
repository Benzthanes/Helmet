package com.helmets.presentation.model

import android.os.Parcelable
import com.google.gson.annotations.SerializedName
import kotlinx.android.parcel.Parcelize

@Parcelize
data class HelmetModel(
    @SerializedName("name") var name: String = "",
    @SerializedName("imgUrl") var imgUrl: String = "",
    @SerializedName("brand") var brand: String = "",
    @SerializedName("model") var model: String = "",
    @SerializedName("s") var s: String = "0",
    @SerializedName("m") var m: String = "0",
    @SerializedName("l") var l: String = "0",
    @SerializedName("xl") var xl: String = "0",
    @SerializedName("xxl") var xxl: String = "0",
    @SerializedName("cost") var cost: String = "0",
    @SerializedName("sell_price") var sell_price: String = "0",
    @SerializedName("product_type") var product_type: String = "",
    @SerializedName("viewType") var viewType: ViewType = ViewType.ITEM
) : Parcelable