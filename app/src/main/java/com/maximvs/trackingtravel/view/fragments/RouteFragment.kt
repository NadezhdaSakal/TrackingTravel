package com.maximvs.trackingtravel.view.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.widget.SearchView
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.LinearLayoutManager
import com.maximvs.trackingtravel.R
import com.maximvs.trackingtravel.data.entity.Route
import com.maximvs.trackingtravel.databinding.FragmentRouteBinding
import com.maximvs.trackingtravel.view.MainActivity
import com.maximvs.trackingtravel.view.TopSpacingItemDecoration
import com.maximvs.trackingtravel.view.adapters.RouteListRecyclerAdapter
import com.maximvs.trackingtravel.viewmodel.RouteFragmentViewModel
import dagger.hilt.android.AndroidEntryPoint
import java.util.*

@AndroidEntryPoint
class RouteFragment : Fragment() {
    private val routeFragmentViewModel: RouteFragmentViewModel by viewModels()
    private lateinit var routesAdapter: RouteListRecyclerAdapter
    private lateinit var binding: FragmentRouteBinding
    private var routesDataBase = listOf<Route>()
        //Используем backing field
        set(value) {
            //Если придет такое же значение то мы выходим из метода
            if (field == value) return
            //Если пришло другое значение, то кладем его в переменную
            field = value
            //Обновляем RV адаптер
            routesAdapter.addItems(field)
        }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentRouteBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)


        initSearchView()

        initRecycler()
        routeFragmentViewModel.routesListLiveData.observe(
            viewLifecycleOwner
        ) {
            routesDataBase = it
        }
    }

    private fun initSearchView() {
        binding.search.setOnClickListener {
            binding.search.isIconified = false
        }

        binding.search.setOnQueryTextListener(object : SearchView.OnQueryTextListener {
            override fun onQueryTextSubmit(query: String?): Boolean {
                return true
            }

            override fun onQueryTextChange(newText: String): Boolean {
                if (newText.isEmpty()) {
                    routesAdapter.addItems(routesDataBase)
                    return true
                }
                val result = routesDataBase.filter {
                    it.title.lowercase(Locale.getDefault())
                        .contains(newText.lowercase(Locale.getDefault()))
                }
                routesAdapter.addItems(result)
                return true
            }
        })
    }

    private fun initRecycler() {
        binding.recyclerView.apply {
            routesAdapter =
                RouteListRecyclerAdapter(object : RouteListRecyclerAdapter.OnItemClickListener {
                    override fun click(route: Route) {
                        (requireActivity() as MainActivity).launchDetailsFragment(route)
                    }
                })
            adapter = routesAdapter
            addItemDecoration(TopSpacingItemDecoration(5))  //Применяю декоратор для отступов
            layoutManager = LinearLayoutManager(requireContext())
        }
    }
}
