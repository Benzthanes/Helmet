package com.helmets.application.view.custom

import android.app.Dialog
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.DialogFragment
import com.helmets.application.databinding.CustomLodingAnimationDialogBinding

class FullScreenProgressDialog : DialogFragment() {

    private lateinit var binding: CustomLodingAnimationDialogBinding

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = CustomLodingAnimationDialogBinding.inflate(layoutInflater)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setupDialog()
    }

    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {
        val dialogTheme = theme
        activity?.apply {
            return object : Dialog(this, dialogTheme) {
                override fun onBackPressed() {
                    //do nothing
                }
            }
        }
        return super.onCreateDialog(savedInstanceState)
    }

    private fun setupDialog() {
        dialog?.apply {
            window?.setBackgroundDrawableResource(android.R.color.transparent)
            setCanceledOnTouchOutside(false)
            setCancelable(false)
        }
    }

    companion object {
        @JvmStatic
        fun newInstance(): FullScreenProgressDialog {
            return FullScreenProgressDialog()
        }
    }
}