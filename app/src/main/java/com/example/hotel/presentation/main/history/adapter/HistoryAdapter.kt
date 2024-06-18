package com.example.hotel.presentation.main.history.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.bumptech.glide.load.engine.DiskCacheStrategy
import com.example.hotel.R
import com.example.hotel.data.models.history.HistoryData
import com.example.hotel.databinding.HistoryItemBinding

class HistoryAdapter(private val listApartments:List<HistoryData>):RecyclerView.Adapter<HistoryAdapter.HistoryViewHolder>() {
    class HistoryViewHolder(val binding:HistoryItemBinding):RecyclerView.ViewHolder(binding.root)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): HistoryViewHolder {
        val binding = HistoryItemBinding.inflate(LayoutInflater.from(parent.context),parent,false)
        return HistoryViewHolder(binding)
    }

    override fun getItemCount(): Int  = listApartments.size

    override fun onBindViewHolder(holder: HistoryViewHolder, position: Int) {
       with(holder.binding){
           Glide.with(root)
               .load(listApartments[position].apartments.image)
               .diskCacheStrategy(DiskCacheStrategy.ALL)
               .error(R.drawable.not_found)
               .into(imHistory)

           nameHistory.text = listApartments[position].apartments.name
           dateHistory.text ="${listApartments[position].dateFrom} --- ${listApartments[position].dateTo}"
           priceHistory.text = listApartments[position].price.toString()

       }
    }
}