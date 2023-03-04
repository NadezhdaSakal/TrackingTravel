package com.maximvs.trackingtravel.data.entity

import androidx.room.TypeConverter
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import java.lang.reflect.Type
import java.util.*

class ConverterRoom {

    @TypeConverter
    fun countryToString(country: Country) = "$country"

    @TypeConverter
    fun stringToCountry(value: String): Country {
        val id = value.substringBefore(':').toInt()
        val name = value.substringAfter(':')

        return Country(id, name)
    }

    @TypeConverter
    fun stringToListMapPhoto(mapPhoto: String?): List<MapPhoto>? {
        if (mapPhoto == null) {
            return Collections.emptyList()
        }
        val listType: Type = object :
            TypeToken<List<MapPhoto>?>() {}.type
        return Gson().fromJson<List<MapPhoto>>(mapPhoto, listType)

    }

    @TypeConverter
    fun listMapPhotoToString(value: List<MapPhoto>?): String? {
        return Gson().toJson(value)

    }

    @TypeConverter
    fun stringToListPhoto(photos: String?): List<TT_Photo>? {
        if (photos == null) {
            return Collections.emptyList()
        }
        val listType: Type = object :
            TypeToken<List<TT_Photo>?>() {}.type
        return Gson().fromJson<List<TT_Photo>>(photos, listType)

    }

    @TypeConverter
    fun listPhotoToString(value: List<TT_Photo>?): String? {
        return Gson().toJson(value)

    }
/*
    @TypeConverter
    fun mapPhotoToString(mapPhoto: MapPhoto) = "$mapPhoto"

    @TypeConverter
    fun stringToMapPhoto(value: String): MapPhoto {
        val id = value.substringBefore(':').toInt()
        val name = value.substringAfter(':')
        val uri = value.substringAfter(',')
        val url = value.substringAfter(',')
        val type = value.substringAfter(',')
        val size = value.substringAfter(',').toInt()

        return MapPhoto(id, name, uri, url, type, size)
    }

    @TypeConverter
    fun photoToString(photos: TT_Photo) = "$photos"



    @TypeConverter
    fun stringToPhoto(value: String): TT_Photo {
        val id = value.substringBefore(':').toInt()
        val name = value.substringAfter(':')
        val uri = value.substringAfter(',')
        val url = value.substringAfter(',')
        val type = value.substringAfter(',')
        val size = value.substringAfter(',').toInt()

        return TT_Photo(id, name, uri, url, type, size)
    } */
}



