package com.maximvs.trackingtravel.view.fragments

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.transition.ChangeBounds
import android.transition.ChangeImageTransform
import android.transition.TransitionManager
import android.transition.TransitionSet
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.bumptech.glide.Glide
import com.google.android.material.tabs.TabLayoutMediator
import com.maximvs.trackingtravel.R
import com.maximvs.trackingtravel.data.entity.Route
import com.maximvs.trackingtravel.databinding.FragmentDetailsBinding
import com.maximvs.trackingtravel.view.MainActivity
import com.maximvs.trackingtravel.view.adapters.ViewPagerAdapter
import com.maximvs.trackingtravel.view.fragments.FavoritesFragment


// проблема1: иконка избранное по клику меняет окраску, но маршрут не попадает в избранное
// проблема2: по повторному нажатию не скрывается расширенное описание маршрута
// желателен прогресс бар при загрузке фоток в ресайклер

class DetailsFragment : Fragment() {
    private lateinit var route: Route
    private lateinit var binding: FragmentDetailsBinding


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentDetailsBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        setRoutesDetails()

        var expanded = false // создана переменная для поочередной работы кнопки tvDetShowDesc
        val transitionSet =
            TransitionSet() // TransitionSet() - наследник Transition, представляет из себя набор других Transition, выполняющихся по очереди или одновременно.
                .addTransition(ChangeBounds()) // ChangeBounds() - Transition, который отвечает за изменение координат View внутри layout и его размеров.
                .addTransition(ChangeImageTransform()) // ChangeImageTransform() - анимирует матричный переход изображения для плавного изменения размеров

        binding.tvDetShowDesc.setOnClickListener {
            expanded = !expanded // при каждом клике expanded меняет значение на противоположное
            TransitionManager.beginDelayedTransition(binding.sceneRoot, transitionSet)
            val params: ViewGroup.LayoutParams = binding.description.layoutParams // здесь в переменную закладываются параметры description
            if (expanded) { // если expanded == true...
                params.height = ViewGroup.LayoutParams.WRAP_CONTENT // ...то высота description в соответствии с содержимым, т.е. полным описанием
                binding.tvDetShowDesc.setText(R.string.hide_description) // и tvDetShowDesc меняет текст на "скрыть описание"
            } else { // в противном случае (т.е. если expanded == false...
                params.height = getResources().getDimensionPixelSize(R.dimen.text_view_height) // высота description будет как указано в dimen, т.е.в изначальной верстке
                binding.tvDetShowDesc.setText(R.string.show_description) // и tvDetShowDesc меняет текст на "Показать описание"
            }
            binding.description.layoutParams = params // применяем выбранноу высоту к description
        }

        binding.btnDetailFavorite.setOnClickListener {
            if (it != null) {
                binding.btnDetailFavorite.setImageResource(R.drawable.ic_baseline_favorite_white_24)
                route.isInFavorites = true
            } else {
                binding.btnDetailFavorite.setImageResource(R.drawable.ic_baseline_favorite_white_border_24)
                route.isInFavorites = false
            }
        }

        binding.btnDetailBack.setOnClickListener {
            (activity as MainActivity).removeDetailsFragment()
        }

        //Создаем адаптер
        val pagerAdapter = ViewPagerAdapter()

        //Привязываем созданный адаптер к нашему ViewPager, который у нас в разметке
        binding.viewPager2.adapter = pagerAdapter

        //Создаем список элементов, который передадим в адаптер
        val pagerItems = route.photos

        //Передаем список в адаптер
        pagerAdapter.addItems(pagerItems)

        TabLayoutMediator(
            binding.fDetTabs,
            binding.viewPager2
        ) { tab, position -> // полоса прокрутки под фотками ресайклер, узенькая оранжевая
            tab.text = "TAB ${(position + 1)}"
        }.attach()
    }

    private fun setRoutesDetails() {
        route = arguments?.get("route") as Route

        binding.tvSearch.text = route.title
        binding.title.text = route.title
        binding.duration.text = route.duration
        binding.distanceRoute.text = route.distanceRoute
        binding.heightPeak.text = route.heightPeak
        binding.description.text = route.description

        Glide.with(this)
            .load(route.mapPhoto[0].uri)
            .centerCrop()
            .into(binding.detMap)

        binding.btnOpenMap.setOnClickListener {
            val geoUri = Uri.parse(route.linkToMap)
            val mapIntent = Intent(Intent.ACTION_VIEW, geoUri)
            startActivity(mapIntent)
        }

        binding.detMap.setOnClickListener {
            val geoUri = Uri.parse(route.linkToMap)
            val mapIntent = Intent(Intent.ACTION_VIEW, geoUri)
            startActivity(mapIntent)
        }

        binding.btnDetailFavorite.setImageResource( // ??? Вроде есть уже
            if (route.isInFavorites) R.drawable.ic_baseline_favorite_white_24
            else R.drawable.ic_baseline_favorite_white_border_24
        )

    }
}
