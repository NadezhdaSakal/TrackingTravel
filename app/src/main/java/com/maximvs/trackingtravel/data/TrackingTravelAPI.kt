package com.maximvs.trackingtravel.data

import com.maximvs.trackingtravel.data.entity.TT_Route
import retrofit2.Call
import retrofit2.http.GET

interface TrackingTravelAPI {
    @GET("test-routes")
    fun getAllRoutes(

    ): Call<List<TT_Route>>
}
