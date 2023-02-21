package com.maximvs.trackingtravel.di.modules

import android.content.Context
import com.maximvs.trackingtravel.data.MainRepository
import com.maximvs.trackingtravel.data.TrackingTravelAPI
import com.maximvs.trackingtravel.data.preferences.PreferenceProvider
import com.maximvs.trackingtravel.domain.Interactor
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object DomainModule {
    //Нам нужно контекст как-то провайдить, поэтому создаем такой метод
    @Provides
    @Singleton
    fun provideContext(@ApplicationContext context: Context
    ) = context

    //Создаем экземпляр SharedPreferences
    @Singleton
    @Provides
    fun providePreferences(context: Context) = PreferenceProvider(context)

    @Provides
    fun provideInteractor(
        repository: MainRepository, travelAPI: TrackingTravelAPI,
        preferenceProvider: PreferenceProvider
    ) =
        Interactor(repo = repository, retrofitService = travelAPI, preferences = preferenceProvider)
}

