package com.example.playlistmaker.media.data.repository

import com.example.playlistmaker.media.data.db.AppDatabase
import com.example.playlistmaker.media.data.db.converter.PlaylistDBConverter
import com.example.playlistmaker.media.data.db.converter.TrackInPlaylistDBConverter
import com.example.playlistmaker.media.data.db.entity.PlaylistEntity
import com.example.playlistmaker.media.domain.repository.PlaylistControlDBRepository
import com.example.playlistmaker.media.playlist_control.domain.models.Playlist
import com.example.playlistmaker.search.domain.models.Track
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow

class PlaylistControlDBRepositoryImpl(
    private val appDatabase: AppDatabase,
    private val playlistDBConverter: PlaylistDBConverter,
    private val trackInPlaylistDBConverter: TrackInPlaylistDBConverter
) : PlaylistControlDBRepository {
    override suspend fun addPlaylist(playlist: Playlist) {
        appDatabase.playlistDao().insertPlaylist(convertToEntity(playlist))
    }

    override suspend fun deletePlaylist(playlistID: Int) {
        appDatabase.playlistDao().deletePlaylist(playlistID)
    }

    override suspend fun getPlaylists(): Flow<List<Playlist?>> = flow {
        val playlists = appDatabase.playlistDao().getPlaylists()
        emit(convertToPlaylist(playlists))
    }

    override suspend fun addTrackToPlaylist(
        trackRegister: List<Int>,
        playlistID: Int,
        track: Track
    ) {
        appDatabase.playlistDao()
            .updateTrackToPlaylist(playlistDBConverter.fromListToString(trackRegister), playlistID)
        if (appDatabase.trackInPlaylists().getTrackInPlaylist(trackRegister.last()) == null) {
            appDatabase.trackInPlaylists()
                .insertTrackInPlaylists(trackInPlaylistDBConverter.map(track, 1))
        } else {
            appDatabase.trackInPlaylists().updatePlaylistToTrack(
                appDatabase.trackInPlaylists()
                    .getTrackInPlaylist(trackRegister.last())!!.numberOfPlaylists + 1,
                trackRegister.last()
            )
        }

    }

    private fun convertToEntity(playlist: Playlist): PlaylistEntity {
        return playlistDBConverter.map(playlist)
    }

    private fun convertToPlaylist(playlists: List<PlaylistEntity>): List<Playlist?> {
        return playlists.map { playlistEntity -> playlistDBConverter.map(playlistEntity) }
    }
}