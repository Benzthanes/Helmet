package com.helmets.presentation.model

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize

@Parcelize
data class FilterModel(
    var isBrandFuse: Boolean = false,
    var isBrandIndex: Boolean = false,
    var isBrandId: Boolean = false,
    var isBrandRd: Boolean = false,
    var isSizeS: Boolean = false,
    var isSizeM: Boolean = false,
    var isSizeL: Boolean = false,
    var isSizeXL: Boolean = false,
    var isSizeXXL: Boolean = false,
    var isHalfFace: Boolean = false,
    var isOpenFace: Boolean = false,
    var isFullFace: Boolean = false,
    var isFlipUp: Boolean = false
) : Parcelable