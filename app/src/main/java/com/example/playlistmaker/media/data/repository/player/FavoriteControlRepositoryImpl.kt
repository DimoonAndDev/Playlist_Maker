package com.example.playlistmaker.media.data.repository.player

import com.example.playlistmaker.media.data.db.AppDatabase
import com.example.playlistmaker.media.data.db.converter.TrackDbConverter
import com.example.playlistmaker.media.data.db.entity.TrackEntity
import com.example.playlistmaker.media.domain.repository.FavoritesControlRepository
import com.example.playlistmaker.media.ui.player.models.PlayerTrack
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow

class FavoriteControlRepositoryImpl(
    private val appDatabase: AppDatabase,
    private val dbConverter: TrackDbConverter
) : FavoritesControlRepository {
    override suspend fun addTrack(track: PlayerTrack) {
        appDatabase.trackDao().insertTrack(convertToEntity(track))
    }

    override suspend fun deleteTrack(trackId: Int) {
        appDatabase.trackDao().deleteTrackFromFavorites(trackId)
    }

    override suspend fun checkFavorites(trackId: Int): Flow<PlayerTrack?> = flow {
        val track = appDatabase.trackDao().checkTrackInFavorites(trackId)
        emit(convertToTrack(track))
    }

    private fun convertToEntity(track: PlayerTrack): TrackEntity {
        return dbConverter.map(track)
    }

    private fun convertToTrack(track: TrackEntity?): PlayerTrack? {
        return dbConverter.map(track)
    }
}