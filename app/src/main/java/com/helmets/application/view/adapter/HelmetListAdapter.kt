package com.helmets.application.view.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.AsyncDifferConfig
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
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
    private val listener: OnItemClickListenerSingleData<HelmetModel>
) : ListAdapter<HelmetModel, RecyclerView.ViewHolder>(
    AsyncDifferConfig.Builder(
        CoinListDiffCallback()
    ).build()
) {
    class CoinListDiffCallback : DiffUtil.ItemCallback<HelmetModel>() {
        override fun areItemsTheSame(oldItem: HelmetModel, newItem: HelmetModel): Boolean {
            return oldItem.name == newItem.name &&
                    oldItem.model == newItem.model
        }

        override fun areContentsTheSame(oldItem: HelmetModel, newItem: HelmetModel): Boolean {
            return oldItem == newItem
        }
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

    override fun getItemViewType(position: Int): Int {
        return getItem(position).viewType.ordinal
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        val item = getItem(position)
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
                    tvHelmetName.text = name
                    tvPrice.text = "891/1,100"
                    tvSize.text = "Size S $s M $m L $l XL $xl XXL $xxl"

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
    }
}