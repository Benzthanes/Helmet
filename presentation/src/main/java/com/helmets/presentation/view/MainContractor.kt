package com.helmets.presentation.view

import com.helmets.presentation.BaseView

interface MainContractor {

    interface View : BaseView {
        fun displayText(text: String)
    }

    interface Presenter {
        fun onStart()
    }
}