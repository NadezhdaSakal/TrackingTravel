package com.maximvs.trackingtravel.data

import com.maximvs.trackingtravel.data.dao.RouteDao
import com.maximvs.trackingtravel.data.entity.Route
import kotlinx.coroutines.flow.Flow


class MainRepository
constructor(
    private val routeDao: RouteDao
) {
    fun putToDb(routes: List<Route>) {
        routeDao.insertAll(routes)
    }

    fun getAllFromDB(): Flow<List<Route>> {
        return routeDao.getCachedRoutes()
    }

}
