package com.helmets.application.view.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.coin.coinapplication.view.extension.loadImageFormUrl
import com.helmets.application.R
import com.helmets.application.databinding.ItemBrandHeaderBinding
import com.helmets.application.databinding.ItemHelmetListBinding
import com.helmets.application.databinding.ItemModelHeaderBinding
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

        fun bind(display: HelmetModel) {


            itemBinding.apply {
                display.apply {
                    val costFormat = COMMA_FORMAT.format(cost.toInt())
                    val sellingPriceFormat = COMMA_FORMAT.format(sell_price.toInt())

                    tvHelmetName.text = name
                    tvPrice.text = "Price $costFormat/$sellingPriceFormat"
                    tvSizeS.text = "S=$s"
                    tvSizeM.text = "M=$m"
                    tvSizeL.text = "L=$l"
                    tvSizeXL.text = "XL=$xl"
                    tvSizeXXL.text = "XXL=$xxl"

                    ivIcon.loadImageFormUrl(
                        root.context,
                        display.imgUrl,
                        R.drawable.ic_waiting,
                        R.drawable.ic_error
                    )
                }

                itemView.setOnClickListener {
                    listener.onCallbackItemData(display)
                }
            }
        }
    }

    inner class BrandViewHolder(
        private val itemBinding: ItemBrandHeaderBinding
    ) : RecyclerView.ViewHolder(itemBinding.root) {

        fun bind(display: HelmetModel) {
            itemBinding.apply {
                display.apply {
                    tvBrand.text = brand
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
    }

}