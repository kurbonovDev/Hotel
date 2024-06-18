package com.example.hotel.presentation.main.home.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import androidx.annotation.DrawableRes
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.hotel.R
import com.example.hotel.data.models.apartaments.RoomInfoItem

class ViewPagerAdapter(@DrawableRes private val listImages: List<Int>) :
    RecyclerView.Adapter<ViewPagerAdapter.ViewPagerHolder>() {

    class ViewPagerHolder(view: View) : RecyclerView.ViewHolder(view) {
        val imageView: ImageView = view.findViewById(R.id.image_viewHolder)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewPagerHolder {
        val itemView = LayoutInflater.from(parent.context)
            .inflate(R.layout.image_container_viewpager, parent, false)
        return ViewPagerHolder(itemView)
    }

    override fun getItemCount(): Int {
        return listImages.size
    }

    override fun onBindViewHolder(holder: ViewPagerHolder, position: Int) {
        holder.imageView.setImageResource(listImages[position])
    }

}