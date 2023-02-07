package com.maximvs.trackingtravel.view.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.maximvs.trackingtravel.data.entity.Route
import com.maximvs.trackingtravel.databinding.FragmentFavoritesBinding
import com.maximvs.trackingtravel.view.MainActivity
import com.maximvs.trackingtravel.view.TopSpacingItemDecoration
import com.maximvs.trackingtravel.view.adapters.RouteListRecyclerAdapter
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager

class FavoritesFragment : Fragment() {
    private lateinit var routesAdapter: RouteListRecyclerAdapter
    private lateinit var binding: FragmentFavoritesBinding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentFavoritesBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val favoritesList: List<Route> = emptyList()

        binding.favoritesRecycler.apply {
            routesAdapter = RouteListRecyclerAdapter(object : RouteListRecyclerAdapter.OnItemClickListener {
                override fun click(route: Route) {
                    (requireActivity() as MainActivity).launchDetailsFragment(route)
                }
            })
            //Присваиваем адаптер
            adapter = routesAdapter
            //Присвои layoutmanager
            layoutManager = LinearLayoutManager(requireContext())
            //Применяем декоратор для отступов
            val decorator = TopSpacingItemDecoration(8)
            addItemDecoration(decorator)
        }
        //Кладем нашу БД в RV
        routesAdapter.addItems(favoritesList)
    }
}
