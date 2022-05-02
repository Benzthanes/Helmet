package com.helmets.presentation.view

import com.helmets.presentation.BaseView
import com.helmets.presentation.model.HelmetModel

interface MainContractor {

    interface View : BaseView {
        fun updateHelmetList(helmetsList: ArrayList<HelmetModel>)
    }

    interface Presenter {
        fun onStart()
    }
}