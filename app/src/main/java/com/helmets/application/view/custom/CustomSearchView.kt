package com.coin.coinapplication.view.custom

import android.content.Context
import android.util.AttributeSet
import android.view.LayoutInflater
import androidx.constraintlayout.widget.ConstraintLayout
import com.helmets.application.databinding.CustomSearchBinding

class CustomSearchView @JvmOverloads constructor(
    context: Context, attrs: AttributeSet? = null, defStyleAttr: Int = 0
) : ConstraintLayout(context, attrs, defStyleAttr) {

    private lateinit var binding: CustomSearchBinding

    init {
        initView()
    }

    private fun initView() {
        binding = CustomSearchBinding.inflate(LayoutInflater.from(context), this, true)
        binding.apply {
            ivClear.setOnClickListener {
                if (tvAutoComplete.text.toString() != EMPTY_STRING) {
                    tvAutoComplete.setText(EMPTY_STRING)
                }
            }
        }
    }

    companion object {
        private const val EMPTY_STRING = ""
    }
}