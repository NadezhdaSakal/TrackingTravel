package com.maximvs.trackingtravel.view.viewHolders

import android.view.View
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.maximvs.trackingtravel.data.entity.TT_Photo
import com.maximvs.trackingtravel.databinding.ItemViewPagerBinding


class PagerViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
    private val binding = ItemViewPagerBinding.bind(itemView)

    //В этом методе мы передаем данные из TT_Photo в нашу верстку item.xml
    fun bind(item: TT_Photo) = with(binding) {

        Glide
            .with(itemView.context) //передается контекст из айтемвью
            .load(item.uri) //Указываем, что будем загружать
            .centerCrop() //Масштабируем с учетом пропорций и отрезаем все, что не влезло
            .into(ivImage) //Указываем, куда будем загружать
    }
}