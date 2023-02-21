package com.maximvs.trackingtravel.data.preferences

import android.content.Context
import android.content.SharedPreferences
import androidx.core.content.edit

class PreferenceProvider(context: Context) {
    //Нам нужен контекст приложения
    private val appContext = context.applicationContext

    //Создаем экземпляр SharedPreferences
    private val preference: SharedPreferences =
        appContext.getSharedPreferences("settings", Context.MODE_PRIVATE)

    init {
        //Логика для первого запуска приложения, чтобы положить наши настройки,
        //Сюда потом можно добавить и другие настройки
        if (preference.getBoolean(KEY_FIRST_LAUNCH, false)) {
            preference.edit { putInt(KEY_DEFAULT_COUNTRY, DEFAULT_COUNTRY) }
            preference.edit { putBoolean(KEY_FIRST_LAUNCH, false) }
        }
    }

    //Category prefs
    //Сохраняем категорию
    fun saveDefaultCountry(country_id: Int) {
        preference.edit { putInt(KEY_DEFAULT_COUNTRY, country_id) }
    }

    //Забираем категорию
    fun getDefaultCountry(): Int {
        return preference.getInt(KEY_DEFAULT_COUNTRY, DEFAULT_COUNTRY) ?: DEFAULT_COUNTRY
    }

    //Ключи для наших настроек, по ним мы их будем получать
    companion object {
        private const val KEY_FIRST_LAUNCH = "first_launch"
        private const val KEY_DEFAULT_COUNTRY = "default_country"
        private const val DEFAULT_COUNTRY = 1
    }
}
