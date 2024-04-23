package com.example.playlistmaker.netconnection

import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Query

interface ItunesApi {
    @GET("/search?entity=song&")
    fun findTrack(@Query("term") term:String): Call<TracksResponse>
}