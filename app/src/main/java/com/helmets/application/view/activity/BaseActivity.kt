package com.helmets.application.view.activity

import android.content.Context
import android.os.Bundle
import android.os.PersistableBundle
import android.view.inputmethod.InputMethodManager
import com.helmets.application.view.custom.FullScreenProgressDialog
import com.helmets.presentation.BaseView
import dagger.android.AndroidInjection
import dagger.android.support.DaggerAppCompatActivity

open class BaseActivity : DaggerAppCompatActivity(), BaseView {

    override fun onCreate(savedInstanceState: Bundle?, persistentState: PersistableBundle?) {
        super.onCreate(savedInstanceState, persistentState)
        AndroidInjection.inject(this)
    }

    private val fullScreenLoadingDialog = FullScreenProgressDialog.newInstance()

    override fun hideKeyboard() {
        val view = currentFocus
        view?.run {
            val imm = getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
            imm.hideSoftInputFromWindow(this.windowToken, 0)
        }
    }

    override fun showLoading() {
        runCatching {
            if (!fullScreenLoadingDialog.isAdded) {
                fullScreenLoadingDialog.show(
                    supportFragmentManager.beginTransaction(),
                    "FULL_SCREEN_LOADING_DIALOG_TAG"
                )
            }
        }.onFailure {

        }
    }

    override fun hideLoading() {
        runCatching {
            if (fullScreenLoadingDialog.isAdded) {
                fullScreenLoadingDialog.dismiss()
            }
        }.onFailure {

        }
    }
}