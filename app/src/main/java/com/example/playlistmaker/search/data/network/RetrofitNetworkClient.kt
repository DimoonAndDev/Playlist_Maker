package com.example.playlistmaker.search.data.network

import android.content.Context
import android.net.ConnectivityManager
import android.net.NetworkCapabilities
import com.example.playlistmaker.search.data.dto.Response
import com.example.playlistmaker.search.data.dto.SearchRequest
import com.example.playlistmaker.search.domain.impl.NetworkClient
import org.koin.java.KoinJavaComponent.getKoin


class RetrofitNetworkClient:NetworkClient {

    private val itunesService = getKoin().get<ItunesApi>()

    override fun doRequest(dto:Any): Response{
        if (!isConnected()){
            return Response().apply { resultCode=-1 }
        }
        return if (dto is SearchRequest) {
            val resp = itunesService.findTrack(dto.expression).execute()

            val body = resp.body() ?: Response()

            body.apply { resultCode = resp.code() }
        } else {
            Response().apply { resultCode = 400 }
        }
    }
    private fun isConnected(): Boolean {

        val connectivityManager = getKoin().get<Context>().getSystemService(
            Context.CONNECTIVITY_SERVICE) as ConnectivityManager
        val capabilities = connectivityManager.getNetworkCapabilities(connectivityManager.activeNetwork)
        if (capabilities != null) {
            when {
                capabilities.hasTransport(NetworkCapabilities.TRANSPORT_CELLULAR) -> return true
                capabilities.hasTransport(NetworkCapabilities.TRANSPORT_WIFI) -> return true
                capabilities.hasTransport(NetworkCapabilities.TRANSPORT_ETHERNET) -> return true
            }
        }
        return false
    }
}