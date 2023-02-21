package com.maximvs.trackingtravel.viewmodel

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.maximvs.trackingtravel.App
import com.maximvs.trackingtravel.domain.Interactor
import javax.inject.Inject

class CountryFragmentViewModel : ViewModel() {
    //Внедряем интерактор
    @Inject
    lateinit var interactor: Interactor
    val countryPropertyLifeData: MutableLiveData<String> = MutableLiveData()

    init {
        //Получаем страну при инициализации, чтобы у нас сразу подтягивалась страна
        getCountryProperty()
    }

    private fun getCountryProperty() {
        //Кладем страну в LiveData
        countryPropertyLifeData.value = interactor.getDefaultCountryFromPreferences()
    }

    fun putCountryProperty(country: Int) {
        //Сохраняем в настройки
        interactor.saveDefaultCountryToPreferences(country)
        //И сразу забираем, чтобы сохранить состояние в модели
        getCountryProperty()
    }
}
