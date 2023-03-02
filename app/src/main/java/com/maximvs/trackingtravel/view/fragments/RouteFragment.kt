package com.maximvs.trackingtravel.view.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.widget.SearchView
import androidx.core.view.isVisible
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.LinearLayoutManager
import com.maximvs.trackingtravel.data.entity.Route
import com.maximvs.trackingtravel.databinding.FragmentRouteBinding
import com.maximvs.trackingtravel.view.MainActivity
import com.maximvs.trackingtravel.view.TopSpacingItemDecoration
import com.maximvs.trackingtravel.view.adapters.RouteListRecyclerAdapter
import com.maximvs.trackingtravel.viewmodel.RouteFragmentViewModel
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.*
import java.util.*

@AndroidEntryPoint
class RouteFragment : Fragment() {

    private val routeViewModel: RouteFragmentViewModel by viewModels()
    private lateinit var routesAdapter: RouteListRecyclerAdapter
    private lateinit var binding: FragmentRouteBinding
    private lateinit var scope: CoroutineScope

    private var routesDataBase = listOf<Route>()
        set(value) {
            //Если придет такое же значение то мы выходим из метода
            if (field == value) return
            //Если прило другое значение, то кладем его в переменную
            field = value
            //Обновляем RV адаптер
            routesAdapter.addItems(field)
        }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?
    ): View {
        binding = FragmentRouteBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        initSearchView()

        initPullToRefresh()

        initRecycler()

        //Кладем нашу БД в RV

        scope = CoroutineScope(Dispatchers.IO).also { scope ->
            scope.launch {
                routeViewModel.routesListData.collect {
                    withContext(Dispatchers.Main) {
                        routesAdapter.addItems(it)
                        routesDataBase = it
                    }
                }
            }
            scope.launch {
                for (element in routeViewModel.showProgressBar) {
                    launch(Dispatchers.Main) {
                        binding.progressBar.isVisible = element
                    }
                }
            }
        }
    }

    override fun onStop() {
        super.onStop()
        scope.cancel()
    }

    private fun initSearchView() {
        binding.search.setOnClickListener {
            binding.search.isIconified = false
        }

        binding.search.setOnQueryTextListener(object : SearchView.OnQueryTextListener {
            // setOnQueryTextListener - слушатель того, что набирается в поисковой строке, ищет
            // совпадения в базе данных, последовательно по каждой новой написанной букве
            override fun onQueryTextSubmit(query: String?): Boolean {  // этот метод - для поиска по
                //  нажатию на лупу на клавиатуре, query - просто имя переменной, может быть любым
                return false  // return true - если используем этот метод, сейчас при нажатии лупы
                // клавиатура скрывается
            }

            override fun onQueryTextChange(newText: String): Boolean { // этот метод - для поиска по
                // добавленным буквам, сначало по одной, затем - по двум - и сразу выдант результат
                // newText - переменная, в зависимости от версии Андроид может называться по-разному, ни на что не влияет
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

    private fun initPullToRefresh() {
        //Вешаем слушатель, чтобы вызвался pull to refresh
        binding.pullToRefresh.setOnRefreshListener {
            //Чистим адаптер(items нужно будет сделать паблик или создать для этого публичный метод)
            routesAdapter.items.clear()
            //Делаем новый запрос на сервер
            routeViewModel.getRoutes()
            //Убираем крутящиеся колечко
            binding.pullToRefresh.isRefreshing = false
        }
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



