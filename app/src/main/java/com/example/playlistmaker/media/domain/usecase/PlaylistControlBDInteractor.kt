package com.example.playlistmaker.media.domain.usecase

import com.example.playlistmaker.media.domain.repository.PlaylistControlDBRepository
import com.example.playlistmaker.media.playlist_control.domain.models.Playlist
import kotlinx.coroutines.flow.Flow

class PlaylistControlBDInteractor(private val playlistControlDBRepository: PlaylistControlDBRepository) {
    suspend fun getPlaylistList():Flow<List<Playlist?>>{
       return playlistControlDBRepository.getPlaylists()
    }
    suspend fun deletePlaylist(playlistId: Int){
        playlistControlDBRepository.deletePlaylist(playlistId)
    }
    suspend fun addTrackToPlaylist(trackRegister:List<Int>,playlistId:Int){
        playlistControlDBRepository.addTrackToPlaylist(trackRegister,playlistId)
    }
}