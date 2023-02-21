package com.maximvs.trackingtravel.viewmodel

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.maximvs.trackingtravel.App
import com.maximvs.trackingtravel.data.entity.Route
import com.maximvs.trackingtravel.domain.Interactor
import dagger.hilt.android.lifecycle.HiltViewModel
import java.util.concurrent.Executors
import javax.inject.Inject

@HiltViewModel
    class RouteFragmentViewModel : ViewModel() {
    val routesListLiveData: MutableLiveData<List<Route>> = MutableLiveData()

    //Инициализируем интерактор
    @Inject
    lateinit var interactor: Interactor
    fun getRoutes() {
        interactor.getRoutesFromApi(object : ApiCallback {
            override fun onSuccess(routes: List<Route>) {
                routesListLiveData.postValue(routes)
            }

            override fun onFailure() {
                Executors.newSingleThreadExecutor().execute {
                    routesListLiveData.postValue(interactor.getRoutesFromDB())
                }
            }
        })
    }

    interface ApiCallback {
        fun onSuccess(routes: List<Route>)
        fun onFailure()
    }
}
