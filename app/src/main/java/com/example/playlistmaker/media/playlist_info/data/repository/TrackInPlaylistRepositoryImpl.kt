package com.example.playlistmaker.media.playlist_info.data.repository

import com.example.playlistmaker.media.data.db.AppDatabase
import com.example.playlistmaker.media.data.db.converter.PlaylistDBConverter
import com.example.playlistmaker.media.data.db.converter.TrackInPlaylistDBConverter
import com.example.playlistmaker.media.data.db.entity.TrackInPlaylistEntity
import com.example.playlistmaker.media.playlist_control.domain.models.Playlist
import com.example.playlistmaker.media.playlist_info.domain.repository.TrackInPlaylistRepository
import com.example.playlistmaker.search.domain.models.Track
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow

class TrackInPlaylistRepositoryImpl(
    private val appDatabase: AppDatabase,
    private val trackInPlaylistDBConverter: TrackInPlaylistDBConverter,
    private val playlistDBConverter: PlaylistDBConverter
) :
    TrackInPlaylistRepository {
    override suspend fun getTracksInPlaylist(playlist: Playlist): Flow<List<Track>> =flow {
        val listOfTracks = mutableListOf<Track>()
        for (trackId in playlist.tracksRegister){
            listOfTracks.add(trackInPlaylistDBConverter.map(appDatabase.trackInPlaylists().getTrackInPlaylist(trackId)?: TrackInPlaylistEntity()) )
        }
        emit(listOfTracks)
    }

    override suspend fun deleteTrackFromPlaylist(trackId: Int,playlist: Playlist): Playlist {
        val newRegister = mutableListOf<Int>()
        newRegister.addAll(playlist.tracksRegister)
        newRegister.remove(trackId)
        appDatabase.playlistDao().updateTrackToPlaylist(playlistDBConverter.fromListToString(newRegister),playlist.innerId)
        val numberOfPlaylist = appDatabase.trackInPlaylists().getTrackInPlaylist(trackId)!!.numberOfPlaylists
        if (numberOfPlaylist == 1){
            appDatabase.trackInPlaylists().deleteTrackInPlaylists(trackId)
        } else {
            appDatabase.trackInPlaylists().updatePlaylistToTrack(numberOfPlaylist-1,trackId)
        }
        return Playlist(playlist.name,playlist.description,playlist.artLink,newRegister,playlist.innerId)
    }
}