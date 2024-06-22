package com.example.hotel.presentation.main.rooms.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.paging.PagingDataAdapter
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.bumptech.glide.load.engine.DiskCacheStrategy
import com.example.hotel.R
import com.example.hotel.data.models.apartaments.RoomItem
import com.example.hotel.databinding.RoomItemAllBinding

class ApartmentsAdapter(private val onClickListener: (RoomItem) -> Unit) :
    PagingDataAdapter<RoomItem, ApartmentsAdapter.ApartmentViewHolder>(RoomItemDiffCallback()) {

    class ApartmentViewHolder(private val binding: RoomItemAllBinding,private val onClickListener: (RoomItem) -> Unit) :
        RecyclerView.ViewHolder(binding.root) {
        fun bind(roomItem: RoomItem) {
            binding.apply {
                Glide.with(binding.root)
                    .load(roomItem.image)
                    .diskCacheStrategy(DiskCacheStrategy.ALL)
                    .error(R.drawable.not_found)
                    .into(imRoom)

                tvRoomNumber.text = roomItem.name
                tvDescRoom.text = roomItem.description
                tvPriceRoom.text = roomItem.price.toString()
                root.setOnClickListener {
                    onClickListener(roomItem)
                }
            }

        }
    }

    override fun onBindViewHolder(holder: ApartmentViewHolder, position: Int) {
        val item = getItem(position)
        item?.let {
            holder.bind(it)
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ApartmentViewHolder {
        val binding = RoomItemAllBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ApartmentViewHolder(binding,onClickListener)
    }


    private class RoomItemDiffCallback : DiffUtil.ItemCallback<RoomItem>() {
        override fun areItemsTheSame(oldItem: RoomItem, newItem: RoomItem): Boolean {
            return oldItem.id == newItem.id
        }

        override fun areContentsTheSame(oldItem: RoomItem, newItem: RoomItem): Boolean {
            return oldItem == newItem
        }
    }
}
