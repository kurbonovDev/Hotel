package com.example.hotel.presentation.main.rooms.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.bumptech.glide.load.engine.DiskCacheStrategy
import com.example.hotel.R
import com.example.hotel.data.models.apartaments.RoomInfoItem
import com.example.hotel.databinding.ImageContainerViewpagerBinding

class RoomViewPagerAdapter(private val roomInfoItem: RoomInfoItem) : RecyclerView.Adapter<RoomViewPagerAdapter.RoomViewPagerHolder>() {

    class RoomViewPagerHolder(val binding:ImageContainerViewpagerBinding) : RecyclerView.ViewHolder(binding.root)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RoomViewPagerHolder {
        val binding = ImageContainerViewpagerBinding.inflate(LayoutInflater.from(parent.context),parent,false)
        return RoomViewPagerHolder(binding)
    }

    override fun getItemCount(): Int = roomInfoItem.data.size

    override fun onBindViewHolder(holder: RoomViewPagerHolder, position: Int) {
        val currentImage = roomInfoItem.data[position].image
        with(holder.binding){
            Glide.with(root)
                .load(currentImage)
                .diskCacheStrategy(DiskCacheStrategy.ALL)
                .error(R.drawable.not_found)
                .into(imageViewHolder)
        }
    }


}