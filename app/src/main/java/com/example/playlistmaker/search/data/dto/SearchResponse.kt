package com.example.playlistmaker.search.data.dto

import com.example.playlistmaker.search.domain.models.Track


class SearchResponse(val results: List<Track>) : Response()