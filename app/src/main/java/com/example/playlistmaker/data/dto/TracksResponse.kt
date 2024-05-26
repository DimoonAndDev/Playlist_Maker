package com.example.playlistmaker.data.dto


import com.example.playlistmaker.domain.models.Track

class TracksResponse(val resultCounts:Int, val results:List<Track>)