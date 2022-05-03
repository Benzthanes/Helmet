package com.helmets.application.view.activity

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.helmets.application.databinding.ActivityFilterBinding
import com.helmets.presentation.model.FilterModel


class FilterActivity : AppCompatActivity() {

    private lateinit var binding: ActivityFilterBinding

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
                    isSizeS = cbSizeS.isChecked,
                    isSizeM = cbSizeM.isChecked,
                    isSizeL = cbSizeL.isChecked,
                    isSizeXL = cbSizeXL.isChecked,
                    isSizeXXL = cbSizeXXL.isChecked,
                    isHalfFace = cbHalfFace.isChecked,
                    isOpenFace = cbOpenFace.isChecked,
                    isFullFace = cbFullFace.isChecked,
                    isFlipUp = cbFlipUp.isChecked
                )
                val returnIntent = Intent()
                returnIntent.putExtra("filter", filterModel)
                setResult(RESULT_OK, returnIntent)
                finish()
            }
        }

    }
}