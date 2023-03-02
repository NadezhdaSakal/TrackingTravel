package com.maximvs.trackingtravel.data

import com.maximvs.trackingtravel.data.dao.RouteDao
import com.maximvs.trackingtravel.data.entity.Route
import java.util.concurrent.Executors
import kotlinx.coroutines.flow.Flow



class MainRepository
constructor(
    private val routeDao: RouteDao
) {
    fun putToDb(routes: List<Route>) {
        //Запросы в бд должны быть в отдельном потоке
        Executors.newSingleThreadExecutor().execute {
            routeDao.insertAll(routes)
        }
    }

    fun getAllFromDB(): Flow<List<Route>> {
        return routeDao.getCachedRoutes()
    }

}
