package com.example.playlistmaker.media.domain.usecase

import com.example.playlistmaker.media.domain.repository.PlaylistControlDBRepository
import com.example.playlistmaker.media.playlist_control.domain.models.Playlist
import kotlinx.coroutines.flow.Flow

class PlaylistControlBDInteractor(private val playlistControlDBRepository: PlaylistControlDBRepository) {
    suspend fun getPlaylistList():Flow<List<Playlist?>>{
       return playlistControlDBRepository.getPlaylists()
    }
    suspend fun deletePlaylist(playlistName:String){
        playlistControlDBRepository.deletePlaylist(playlistName)
    }
}