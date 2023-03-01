package com.maximvs.trackingtravel.data.preferences

import android.content.Context
import android.content.SharedPreferences
import androidx.core.content.edit

class PreferenceProvider(context: Context) {

    //Ключи для настроек, по ним мы их будем получать
    companion object {
        private const val KEY_FIRST_LAUNCH = "first_launch"
        private const val KEY_DEFAULT_COUNTRY = "default_country"
        private const val DEFAULT_COUNTRY = 1
    }

    //контекст приложения
    private val appContext = context.applicationContext

    //экземпляр SharedPreferences
    private val preference: SharedPreferences =
        appContext.getSharedPreferences("country", Context.MODE_PRIVATE)

    init {
        //Логика для первого запуска приложения, чтобы положить настройки,
        //Сюда потом можно добавить и другие настройки
        if (preference.getBoolean(KEY_FIRST_LAUNCH, false)) {
            preference.edit { putInt(KEY_DEFAULT_COUNTRY, DEFAULT_COUNTRY) }
            preference.edit { putBoolean(KEY_FIRST_LAUNCH, false) }
        }
    }

    //Country prefs
    //Сохраняем страну
    fun saveCountry(countryId: Int) {
        preference.edit { putInt(KEY_DEFAULT_COUNTRY, countryId) }
    }


    //Забираем страну
    fun getCountry(): Int {
        return preference.getInt(KEY_DEFAULT_COUNTRY, DEFAULT_COUNTRY)
    }


}
