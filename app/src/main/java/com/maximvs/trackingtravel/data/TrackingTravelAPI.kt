package com.maximvs.trackingtravel.data

import com.maximvs.trackingtravel.data.entity.TT_Route
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Path

interface TrackingTravelAPI {
    @GET("test-routes/{country_id}")
    fun getAllRoutes(
        @Path("country_id") country: String

    ) : Call<List<TT_Route>>


}
