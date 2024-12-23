package com.example.playlistmaker.di

import android.content.Context
import android.content.SharedPreferences
import android.media.MediaPlayer
import androidx.room.Room
import com.example.playlistmaker.PLAYLIST_SHARED_PREFS
import com.example.playlistmaker.media.data.db.AppDatabase
import com.example.playlistmaker.search.data.network.ItunesApi
import com.example.playlistmaker.search.data.network.RetrofitNetworkClient
import com.example.playlistmaker.search.data.shpr.SearchTrackHistoryHelper
import com.google.gson.Gson
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.Dispatchers
import org.koin.android.ext.koin.androidContext
import org.koin.dsl.module
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory


val dataModule = module {
    single<ItunesApi> {

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
    single {
        Room.databaseBuilder(androidContext(), AppDatabase::class.java, "database.db")
            .fallbackToDestructiveMigration()
            .build()
    }
    single { Gson() }
    factory<CoroutineDispatcher> { Dispatchers.IO }

}