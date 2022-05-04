package com.helmets.application.view.activity

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.helmets.application.databinding.ActivityFilterBinding
import com.helmets.presentation.model.FilterModel


class FilterActivity : AppCompatActivity() {

    private lateinit var binding: ActivityFilterBinding

    companion object {
        const val FILTER_KEY = "FILTER"
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityFilterBinding.inflate(layoutInflater)
        setContentView(binding.root)
        binding.apply {
            btnApply.setOnClickListener {
                val filterModel = FilterModel(
                    isBrandFuse = cbBrandFuse.isChecked,
                    isBrandIndex = cbBrandIndex.isChecked,
                    isBrandId = cbBrandId.isChecked,
                    isBrandRd = cbBrandRd.isChecked,
                    isBrandSpaceCrown = cbBrandSpaceCrown.isChecked,
                    isSizeS = cbSizeS.isChecked,
                    isSizeM = cbSizeM.isChecked,
                    isSizeL = cbSizeL.isChecked,
                    isSizeXL = cbSizeXL.isChecked,
                    isSizeXXL = cbSizeXXL.isChecked,
                    isHalfFace = cbHalfFace.isChecked,
                    isOpenFace = cbOpenFace.isChecked,
                    isFullFace = cbFullFace.isChecked,
                    isFlipUp = cbFlipUp.isChecked,
                    isShield1 = cbShieldOne.isChecked,
                    isShield2 = cbShieldTwo.isChecked
                )
                if (!edMinPrice.text.isNullOrBlank() && !edMaxPrice.text.isNullOrBlank()) {
                    filterModel.minPrice = edMinPrice.text.toString().toInt()
                    filterModel.maxPrice = edMaxPrice.text.toString().toInt()
                } else if (!edMinPrice.text.isNullOrBlank()) {
                    filterModel.minPrice = edMinPrice.text.toString().toInt()
                    filterModel.maxPrice = 999999
                } else if (!edMaxPrice.text.isNullOrBlank()) {
                    filterModel.minPrice = 0
                    filterModel.maxPrice = edMaxPrice.text.toString().toInt()
                }

                Intent().apply {
                    putExtra(FILTER_KEY, filterModel)
                    setResult(RESULT_OK, this)
                    finish()
                }
            }
        }

    }
}