package com.maximvs.trackingtravel.viewmodel

import androidx.lifecycle.ViewModel
import com.maximvs.trackingtravel.data.entity.Route
import com.maximvs.trackingtravel.domain.Interactor
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

@HiltViewModel
class RouteFragmentViewModel @Inject constructor(private val interactor: Interactor) : ViewModel() {

    val routesListData: Flow<List<Route>> = interactor.getRoutesFromDB()
    val showProgressBar: Channel<Boolean> = interactor.progressBarState

    init {
        getRoutes()
    }

    fun getRoutes() {
        interactor.getRoutesFromApi()
    }
}
