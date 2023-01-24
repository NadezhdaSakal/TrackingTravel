package com.maximvs.trackingtravel.viewmodel

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.maximvs.trackingtravel.data.entity.Route
import com.maximvs.trackingtravel.domain.Interactor
import dagger.hilt.android.lifecycle.HiltViewModel
import java.util.concurrent.Executors
import javax.inject.Inject

@HiltViewModel
class RouteFragmentViewModel @Inject constructor(
    interactor: Interactor,

    ) : ViewModel() {

    val routesListLiveData: MutableLiveData<List<Route>> = MutableLiveData()


    init {
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
