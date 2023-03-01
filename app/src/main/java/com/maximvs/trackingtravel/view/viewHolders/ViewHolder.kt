package com.maximvs.trackingtravel.view.viewHolders

import android.view.View
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.maximvs.trackingtravel.R
import com.maximvs.trackingtravel.databinding.RecyclerviewItemBinding
import com.maximvs.trackingtravel.data.entity.Route


class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
    private val binding = RecyclerviewItemBinding.bind(itemView)


    fun bind(route: Route) = with(binding) {
        tvRvItemTitle.text = route.title
        tvRvItem3.text = route.heightPeak
        tvRvItem2.text = route.distanceRoute
        tvRvItem1.text = route.duration

        Glide.with(itemView)
            .load(route.photos[0].uri)
            .centerCrop()
            .placeholder(R.drawable.app_icons)
            .into(photo)

    }
}
