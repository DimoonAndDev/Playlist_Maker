package com.example.playlistmaker.media.data.repository

import com.example.playlistmaker.media.data.db.AppDatabase
import com.example.playlistmaker.media.data.db.converter.TrackDbConverter
import com.example.playlistmaker.media.data.db.entity.TrackEntity
import com.example.playlistmaker.media.domain.repository.GetFavoritesRep
import com.example.playlistmaker.media.player.ui.models.PlayerTrack

import com.example.playlistmaker.search.domain.models.Track
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow

class GetFavoriteTrackRepImpl(
    private val appDatabase: AppDatabase,
    private val dbConverter: TrackDbConverter
):GetFavoritesRep {
    override suspend fun getFavoriteTracks(): Flow<List<Track>> = flow {
        val tracks = appDatabase.trackDao().getFavoriteTracks()
        emit(convertFromEntities(tracks))
    }
    private fun convertFromEntities(tracks:List<TrackEntity>):List<Track>{
        return tracks.map { track -> dbConverter.map(track,true) }
    }
}