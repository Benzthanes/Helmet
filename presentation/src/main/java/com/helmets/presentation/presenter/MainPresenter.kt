package com.helmets.presentation.presenter

import com.helmets.presentation.controller.MainController
import com.helmets.presentation.view.MainContractor
import com.helmets.presentation.BasePresenter
import javax.inject.Inject

class MainPresenter @Inject constructor(
    private val controller: MainController
) : BasePresenter<MainContractor.View>(), MainContractor.Presenter {

    override fun onStart() {
        controller.callGetPhone(
            {
                doInView { view -> view.displayText("Call phone list success::") }
            },
            {
                doInView { view -> view.displayText("Exception::") }
            }
        )
    }

}