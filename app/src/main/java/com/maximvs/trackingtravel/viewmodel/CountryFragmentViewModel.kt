package com.maximvs.trackingtravel.viewmodel

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.maximvs.trackingtravel.domain.Interactor
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class CountryFragmentViewModel @Inject constructor(): ViewModel() {
    //Внедряем интерактор
     var interactor: Interactor? = null
    @Inject set

    val countryPropertyLifeData: MutableLiveData<Int> = MutableLiveData()

    init {
        //Получаем страну при инициализации, чтобы у нас сразу подтягивалась страна
        getCountryProperty()
    }

    private fun getCountryProperty() {
        //Кладем страну в LiveData
        countryPropertyLifeData.value = interactor?.getDefaultCountryFromPreferences()
    }

    fun putCountryProperty(countryId: Int) {
        //Сохраняем в настройки
        interactor?.saveDefaultCountryToPreferences(countryId)
        //И сразу забираем, чтобы сохранить состояние в модели
        getCountryProperty()
    }
}
