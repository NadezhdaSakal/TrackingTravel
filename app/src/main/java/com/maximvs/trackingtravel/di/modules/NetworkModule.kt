package com.maximvs.trackingtravel.di.modules

import com.maximvs.trackingtravel.BuildConfig
import com.maximvs.trackingtravel.Constants
import com.maximvs.trackingtravel.data.ApiConstants
import com.maximvs.trackingtravel.data.TrackingTravelAPI
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.util.concurrent.TimeUnit

@Module
@InstallIn(SingletonComponent::class)
object NetworkModule {

    @Provides
    fun provideOkHttpClient(): OkHttpClient = OkHttpClient.Builder()
        .callTimeout(Constants.CALL_TIMEOUT.toLong(), TimeUnit.SECONDS)
        .readTimeout(Constants.READ_TIMEOUT.toLong(), TimeUnit.SECONDS)
        .addInterceptor(HttpLoggingInterceptor().apply {
            if (BuildConfig.DEBUG) {
                level = HttpLoggingInterceptor.Level.BASIC
            }
        })
        .build()

    @Provides
    fun provideRetrofit(okHttpClient: OkHttpClient): Retrofit = Retrofit.Builder()
        .baseUrl(ApiConstants.BASE_URL)
        .addConverterFactory(GsonConverterFactory.create())
        .client(okHttpClient)
        .build()

    @Provides
    fun provideTrackingTravelApi(retrofit: Retrofit): TrackingTravelAPI = retrofit.create(TrackingTravelAPI::class.java)
}
