package com.example.playlistmaker.media.data.repository

import com.example.playlistmaker.media.data.db.AppDatabase
import com.example.playlistmaker.media.data.db.converter.PlaylistDBConverter
import com.example.playlistmaker.media.data.db.entity.PlaylistEntity
import com.example.playlistmaker.media.domain.repository.PlaylistControlDBRepository
import com.example.playlistmaker.media.playlist_control.domain.models.Playlist
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow

class PlaylistControlDBRepositoryImpl(
    private val appDatabase: AppDatabase,
    private val dbConverter: PlaylistDBConverter
) : PlaylistControlDBRepository {
    override suspend fun addPlaylist(playlist: Playlist) {
        appDatabase.playlistDao().insertPlaylist(convertToEntity(playlist))
    }

    override suspend fun getPlaylists(): Flow<List<Playlist?>> = flow {
        val playlists = appDatabase.playlistDao().getPlaylists()
        emit(convertToPlaylist(playlists))
    }

    private fun convertToEntity(playlist: Playlist):PlaylistEntity{
        return dbConverter.map(playlist)
    }
    private fun convertToPlaylist(playlists:List<PlaylistEntity>):List<Playlist?>{
        return playlists.map { playlistEntity -> dbConverter.map(playlistEntity) }
    }
}