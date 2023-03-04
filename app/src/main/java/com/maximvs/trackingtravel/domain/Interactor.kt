package com.maximvs.trackingtravel.domain

import com.maximvs.trackingtravel.data.MainRepository
import com.maximvs.trackingtravel.data.TrackingTravelAPI
import com.maximvs.trackingtravel.data.entity.Route
import com.maximvs.trackingtravel.data.entity.TT_Route
import com.maximvs.trackingtravel.data.preferences.PreferenceProvider
import com.maximvs.trackingtravel.utils.Converter
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.launch
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response


class Interactor(
    private val repo: MainRepository, private val retrofitService: TrackingTravelAPI,
    private val preferences: PreferenceProvider
) {
    val scope: CoroutineScope = CoroutineScope(Dispatchers.IO)
    var progressBarState = Channel<Boolean>(Channel.CONFLATED)


    fun getRoutesFromApi() {

        //Показываем ProgressBar
        scope.launch {
            progressBarState.send(true)
        }

        //Метод getCountryFromPreferences() будет нам получать при каждом запросе нужный список
        retrofitService.getAllRoutes(getCountryFromPreferences())
            .enqueue(object : Callback<List<TT_Route>> {
                override fun onResponse(
                    call: Call<List<TT_Route>>,
                    response: Response<List<TT_Route>>
                ) {
                    val list = Converter.convertApiListToDtoList(response.body())

                    //Кладем в бд
                    //В случае успешного ответа кладем фильмы в БД и выключаем ProgressBar
                    scope.launch {
                        repo.putToDb(list)
                        progressBarState.send(false)
                    }
                }

                override fun onFailure(call: Call<List<TT_Route>>, t: Throwable) {
                    //В случае провала выключаем ProgressBar
                    scope.launch {
                        progressBarState.send(false)
                    }
                }
            })
    }

    //Метод для сохранения настроек
    fun saveCountryToPreferences(countryId: Int) {
        preferences.saveDefaultCountry(countryId)
    }

    //Метод для получения настроек
    fun getCountryFromPreferences() = preferences.getDefaultCountry()

    fun getRoutesFromDB(): Flow<List<Route>> = repo.getAllFromDB()

}
