package com.helmets.application.view.listener

import com.helmets.presentation.model.HelmetModel


interface OnItemClickListenerSingleData<T : HelmetModel> {
    fun onCallbackItemData(display: T)
}
