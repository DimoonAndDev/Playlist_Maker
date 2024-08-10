package com.example.playlistmaker.search.domain.impl

import com.example.playlistmaker.search.data.dto.Response

interface NetworkClient {
    suspend fun doRequest(dto:Any):Response
}