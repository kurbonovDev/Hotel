package com.example.hotel.presentation.main.rooms.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.bumptech.glide.load.engine.DiskCacheStrategy
import com.example.hotel.R
import com.example.hotel.data.models.apartaments.RoomItem
import com.example.hotel.databinding.RoomItemAllBinding
import com.example.hotel.data.models.apartaments.RoomsDTO


class RoomsAdapter(private val rooms:RoomsDTO, private val onClick:(RoomItem)->Unit):RecyclerView.Adapter<RoomsAdapter.RoomsViewHolder>() {
    class RoomsViewHolder(val binding:RoomItemAllBinding):RecyclerView.ViewHolder(binding.root)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RoomsViewHolder {
        val binding = RoomItemAllBinding.inflate(LayoutInflater.from(parent.context),parent,false)
        return RoomsViewHolder(binding)
    }

    override fun getItemCount(): Int = rooms.data.records.size

    override fun onBindViewHolder(holder: RoomsViewHolder, position: Int) {
        val currentItem = rooms.data.records[position]
        with(holder.binding){

            Glide.with(holder.itemView)
                .load(currentItem.image)
                .diskCacheStrategy(DiskCacheStrategy.ALL)
                .error(R.drawable.not_found)
                .into(imRoom)

            tvRoomNumber.text = currentItem.name
            tvDescRoom.text = currentItem.description
            tvPriceRoom.text = currentItem.price.toString()
            root.setOnClickListener {
                onClick(currentItem)
            }
        }
    }
}