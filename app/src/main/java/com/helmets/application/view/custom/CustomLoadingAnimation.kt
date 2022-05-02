package com.helmets.application.view.custom

import android.content.Context
import android.util.AttributeSet
import android.view.LayoutInflater
import android.widget.LinearLayout
import com.helmets.application.R
import com.helmets.application.databinding.CustomLoadingBinding


class CustomLoadingAnimation @JvmOverloads constructor(
        context: Context, attrs: AttributeSet? = null, defStyleAttr: Int = 0
) : LinearLayout(context, attrs, defStyleAttr) {

    private lateinit var binding: CustomLoadingBinding

    init {
        initView()
    }

    private fun initView() {
        binding = CustomLoadingBinding.inflate(LayoutInflater.from(context), this, true)
        playAnimationLoading()
    }

    private fun playAnimationLoading() {
        binding.vLottieLoadingAnimation.apply {
            this.setAnimation(R.raw.animation_loading)
            this.playAnimation()
        }
    }
}