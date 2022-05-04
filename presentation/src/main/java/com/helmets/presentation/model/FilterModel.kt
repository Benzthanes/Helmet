package com.helmets.presentation.model

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize

@Parcelize
data class FilterModel(
    var isBrandFuse: Boolean = false,
    var isBrandIndex: Boolean = false,
    var isBrandId: Boolean = false,
    var isBrandRd: Boolean = false,
    var isBrandSpaceCrown: Boolean = false,
    var isSizeS: Boolean = false,
    var isSizeM: Boolean = false,
    var isSizeL: Boolean = false,
    var isSizeXL: Boolean = false,
    var isSizeXXL: Boolean = false,
    var isHalfFace: Boolean = false,
    var isOpenFace: Boolean = false,
    var isFullFace: Boolean = false,
    var isFlipUp: Boolean = false,
    var minPrice: Int? = null,
    var maxPrice: Int? = null,
    var isShield1: Boolean = false,
    var isShield2: Boolean = false
) : Parcelable