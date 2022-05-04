package com.helmets.application.view.custom

import android.app.Dialog
import android.content.Context
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.view.Window
import android.view.WindowManager
import com.helmets.application.R
import com.helmets.application.databinding.CustomZoomImageDialogBinding
import com.helmets.application.view.extension.loadImageFormUrl

class CustomZoomImageDialog(
    context: Context,
    private val customDialogContext: Context = context
) : Dialog(context) {

    private val binding = CustomZoomImageDialogBinding.inflate(layoutInflater)

    init {
        requestWindowFeature(Window.FEATURE_NO_TITLE)
        setCancelable(false)
        setContentView(binding.root)
    }

    fun build(): CustomZoomImageDialog {
        return CustomZoomImageDialog(customDialogContext).apply {
            window?.setLayout(
                WindowManager.LayoutParams.MATCH_PARENT,
                WindowManager.LayoutParams.MATCH_PARENT
            )
            window?.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))
        }
    }

    fun withImage(menuPhoto: String): CustomZoomImageDialog {
        binding.ivMenu.apply {
            loadImageFormUrl(
                context,
                menuPhoto,
                R.drawable.ic_waiting,
                R.drawable.ic_no_img
            )
        }
        return this
    }

    fun withCloseDialog(): CustomZoomImageDialog {
        binding.apply {
            csLayoutCloseDialog.setOnClickListener {
                dismiss()
            }
        }
        return this
    }
}
