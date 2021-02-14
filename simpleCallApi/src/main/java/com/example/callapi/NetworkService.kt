package com.example.simplecallnetwork

import com.example.simplecallnetwork.utils.DateUtils.DATE_FORMAT
import com.google.gson.Gson
import com.google.gson.GsonBuilder
import com.squareup.moshi.Moshi
import com.squareup.moshi.kotlin.reflect.KotlinJsonAdapterFactory
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.converter.moshi.MoshiConverterFactory

object NetworkService {
    inline fun <reified T> createClientByService(
        baseUrl: String = "",
        okHttpClient: OkHttpClient = OkHttpClient.Builder().build(),
        moshiConverter: Boolean = false
    ): T = Retrofit.Builder()
        .baseUrl(baseUrl)
        .client(okHttpClient)
        .addConverterFactory(
            if (moshiConverter) MoshiConverterFactory.create(moshiFactory()) else GsonConverterFactory.create(
                getDefaultGson()
            ))
        .build()
        .create(T::class.java)

    inline fun <reified T> createClientByServiceRx(
        baseUrl: String,
        okHttpClient: OkHttpClient = OkHttpClient.Builder().build(),
        moshiConverter: Boolean = false
    ): T = Retrofit.Builder()
        .baseUrl(baseUrl)
        .client(okHttpClient)
        .addConverterFactory(
            if (moshiConverter) MoshiConverterFactory.create(moshiFactory()) else GsonConverterFactory.create(
                getDefaultGson()
            ))
        .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
        .build()
        .create(T::class.java)


    fun moshiFactory(): Moshi {
        return Moshi.Builder()
            .add(KotlinJsonAdapterFactory())
            .build()
    }

    fun getDefaultGson(): Gson? {
        return GsonBuilder()
            .setDateFormat(DATE_FORMAT).create()
    }

}