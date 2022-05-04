package com.helmets.application.view.adapter

import android.annotation.SuppressLint
import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.helmets.application.R
import com.helmets.application.databinding.ItemBrandHeaderBinding
import com.helmets.application.databinding.ItemHelmetListBinding
import com.helmets.application.databinding.ItemModelHeaderBinding
import com.helmets.application.view.custom.CustomZoomImageDialog
import com.helmets.application.view.extension.loadImageFormUrl
import com.helmets.application.view.listener.OnItemClickListenerSingleData
import com.helmets.presentation.model.HelmetModel
import com.helmets.presentation.model.ViewType

class HelmetListAdapter constructor(
    private val listener: OnItemClickListenerSingleData<HelmetModel>,
    private val itemList: ArrayList<HelmetModel> = arrayListOf()
) : RecyclerView.Adapter<RecyclerView.ViewHolder>() {

    fun updateList(items: ArrayList<HelmetModel>) {
        itemList.clear()
        itemList.addAll(items)
        notifyDataSetChanged()
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        return when (viewType) {
            ViewType.ITEM.ordinal -> {
                val itemBinding =
                    ItemHelmetListBinding.inflate(
                        LayoutInflater.from(parent.context),
                        parent, false
                    )
                HelmetItemViewHolder(itemBinding, listener)
            }
            ViewType.BRAND.ordinal -> {
                val itemBinding =
                    ItemBrandHeaderBinding.inflate(
                        LayoutInflater.from(parent.context),
                        parent, false
                    )
                BrandViewHolder(itemBinding)
            }
            else -> {
                val itemBinding =
                    ItemModelHeaderBinding.inflate(
                        LayoutInflater.from(parent.context),
                        parent, false
                    )
                ModelViewHolder(itemBinding)
            }
        }
    }

    override fun getItemCount(): Int {
        return itemList.size
    }

    override fun getItemViewType(position: Int): Int {
        return itemList[position].viewType.ordinal
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        val item = itemList[position]
        when (holder) {
            is HelmetItemViewHolder -> {
                holder.bind(item)
            }
            is BrandViewHolder -> {
                holder.bind(item)
            }
            is ModelViewHolder -> {
                holder.bind(item)
            }

        }
    }

    inner class HelmetItemViewHolder(
        private val itemBinding: ItemHelmetListBinding,
        private val listener: OnItemClickListenerSingleData<HelmetModel>
    ) : RecyclerView.ViewHolder(itemBinding.root) {

        @SuppressLint("SetTextI18n")
        fun bind(display: HelmetModel) {
            itemBinding.apply {
                display.apply {
                    val costFormat = COMMA_FORMAT.format(cost.toInt())
                    val sellingPriceFormat = COMMA_FORMAT.format(sellPrice.toInt())

                    tvHelmetName.text = name
                    root.context.apply {
                        tvPrice.text =
                            getString(R.string.price) + " $costFormat/$sellingPriceFormat"
                        tvSizeS.text = getString(R.string.s) + "$EQUAL$s"
                        tvSizeM.text = getString(R.string.m) + "$EQUAL$m"
                        tvSizeL.text = getString(R.string.l) + "$EQUAL$l"
                        tvSizeXL.text = getString(R.string.xl) + "$EQUAL$xl"
                        tvSizeXXL.text = getString(R.string.xxl) + "$EQUAL$xxl"
                        ivIcon.loadImageFormUrl(
                            this,
                            display.imgUrl,
                            R.drawable.ic_waiting,
                            R.drawable.ic_no_img
                        )

                        ivIcon.setOnClickListener {
                            showCustomImageMenuDialog(this, display.imgUrl)
                        }
                    }

                }

                itemView.setOnClickListener {
                    listener.onCallbackItemData(display)
                }

            }
        }

        private fun showCustomImageMenuDialog(context: Context, menuPhoto: String) {
            CustomZoomImageDialog(context)
                .build()
                .withImage(menuPhoto)
                .withCloseDialog()
                .show()
        }
    }

    inner class BrandViewHolder(
        private val itemBinding: ItemBrandHeaderBinding
    ) : RecyclerView.ViewHolder(itemBinding.root) {

        fun bind(display: HelmetModel) {
            itemBinding.apply {
                display.apply {
                    tvBrand.text = brand
                    ivBrand.loadImageFormUrl(
                        root.context,
                        display.imgUrl,
                        R.drawable.ic_waiting,
                        R.drawable.ic_no_img
                    )
                }
            }
        }
    }

    inner class ModelViewHolder(
        private val itemBinding: ItemModelHeaderBinding
    ) : RecyclerView.ViewHolder(itemBinding.root) {

        fun bind(display: HelmetModel) {
            itemBinding.apply {
                display.apply {
                    tvModel.text = model
                }
            }
        }
    }

    companion object {
        private const val COMMA_FORMAT = "%,d"
        private const val EQUAL = "="
    }

}