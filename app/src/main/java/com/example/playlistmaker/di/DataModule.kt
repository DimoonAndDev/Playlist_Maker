package com.example.playlistmaker.di

import android.content.Context
import android.content.SharedPreferences
import android.media.MediaPlayer
import com.example.playlistmaker.PLAYLIST_SHARED_PREFS
import com.example.playlistmaker.search.data.network.ItunesApi
import com.example.playlistmaker.search.data.network.RetrofitNetworkClient
import com.example.playlistmaker.search.data.shpr.SearchTrackHistoryHelper
import org.koin.android.ext.koin.androidContext
import org.koin.dsl.module
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory


val dataModule = module {
    single<ItunesApi>{

        Retrofit.Builder()
            .baseUrl("https://itunes.apple.com")
            .addConverterFactory(GsonConverterFactory.create())
            .build()
            .create(ItunesApi::class.java)
    }
    single<SharedPreferences> {
        androidContext().getSharedPreferences(PLAYLIST_SHARED_PREFS, Context.MODE_PRIVATE)
    }
    single { RetrofitNetworkClient(get()) }
    single { SearchTrackHistoryHelper(get()) }
    factory { MediaPlayer() }
}