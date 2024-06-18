package com.example.hotel.presentation.main.home.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.bumptech.glide.load.engine.DiskCacheStrategy
import com.example.hotel.R
import com.example.hotel.databinding.RoomItemBinding
import com.example.hotel.data.models.apartaments.RoomsDTO

class TopRoomsAdapter(private val rooms:RoomsDTO):RecyclerView.Adapter<TopRoomsAdapter.TopRoomsViewHolder>() {
    class TopRoomsViewHolder(val binding:RoomItemBinding):RecyclerView.ViewHolder(binding.root)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): TopRoomsViewHolder {
        val binding = RoomItemBinding.inflate(LayoutInflater.from(parent.context),parent,false)
        return TopRoomsViewHolder(binding)
    }

    override fun getItemCount(): Int = rooms.data.size

    override fun onBindViewHolder(holder: TopRoomsViewHolder, position: Int){
        val currentItem = rooms.data[position]
        with(holder.binding){

            Glide.with(holder.itemView)
                .load(currentItem.image)
                .diskCacheStrategy(DiskCacheStrategy.ALL)
                .error(R.drawable.not_found)
                .into(hotelImage)

            hotelName.text = currentItem.name
            hotelPrice.text = currentItem.price.toString()
            hotelTitle.text = currentItem.description
        }
    }


}