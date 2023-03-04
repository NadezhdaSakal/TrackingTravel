package com.maximvs.trackingtravel.utils

import com.maximvs.trackingtravel.data.entity.TT_Route
import com.maximvs.trackingtravel.data.entity.Route

object Converter {

    fun convertApiListToDtoList(list: List<TT_Route>?): List<Route> {
        val result = mutableListOf<Route>()
        list?.forEach {
            result.add(
                Route(
                    id = it.id,
                    title = it.title,
                    description = it.description,
                    linkToMap = it.linkToMap,
                    heightPeak = it.heightPeak,
                    distanceRoute = it.distanceRoute,
                    duration = it.durationRoute,
                    mapPhoto = it.mapPhoto,
                    photos = it.photo,
                    country = it.country

                )
            )
        }
        return result
    }
}
