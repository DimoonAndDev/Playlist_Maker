package com.example.playlistmaker.search.domain.impl

import com.example.playlistmaker.search.data.dto.Response

interface NetworkClient {
    fun doRequest(dto:Any):Response
}