package com.maximvs.trackingtravel.domain

import com.maximvs.trackingtravel.data.MainRepository
import com.maximvs.trackingtravel.data.TrackingTravelAPI
import com.maximvs.trackingtravel.data.entity.Route
import com.maximvs.trackingtravel.data.entity.TT_Route
import com.maximvs.trackingtravel.data.preferences.PreferenceProvider
import com.maximvs.trackingtravel.utils.Converter
import com.maximvs.trackingtravel.viewmodel.RouteFragmentViewModel
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class Interactor(private val repo: MainRepository, private val retrofitService: TrackingTravelAPI,
                 private val preferences: PreferenceProvider) {
    fun getRoutesFromApi(callback: RouteFragmentViewModel.ApiCallback) {
        //Метод getDefaultCountryFromPreferences() будет нам получать при каждом запросе нужный список
        retrofitService.getAllRoutes(getDefaultCountryFromPreferences()).enqueue(object : Callback<List<TT_Route>> {
            override fun onResponse(
                call: Call<List<TT_Route>>,
                response: Response<List<TT_Route>>
            ) {
                val list = Converter.convertApiListToDtoList(response.body())
                repo.putToDb(list)
                callback.onSuccess(list)
            }

            override fun onFailure(call: Call<List<TT_Route>>, t: Throwable) {
                callback.onFailure()
            }
        })
    }

    //Метод для сохранения настроек
    fun saveDefaultCountryToPreferences(country_id: Int) {
        preferences.saveDefaultCountry(country_id)
    }
    //Метод для получения настроек
    fun getDefaultCountryFromPreferences() = preferences.getDefaultCountry()

    fun getRoutesFromDB(): List<Route> = repo.getAllFromDB()

}
