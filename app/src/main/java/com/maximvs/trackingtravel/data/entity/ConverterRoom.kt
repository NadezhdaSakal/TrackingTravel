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
        val nameOfCountry = value.substringAfter(':')

        return Country(id, nameOfCountry)
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

}



