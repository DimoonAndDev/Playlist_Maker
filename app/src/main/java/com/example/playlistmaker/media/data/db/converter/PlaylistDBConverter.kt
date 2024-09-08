package com.example.playlistmaker.media.data.db.converter

import com.example.playlistmaker.media.data.db.entity.PlaylistEntity
import com.example.playlistmaker.media.playlist_control.domain.models.Playlist


class PlaylistDBConverter() {
    fun map(playlist: Playlist):PlaylistEntity{
        return PlaylistEntity(playlist.name, playlist.description,playlist.artLink,playlist.tracksNumber)
    }
    fun map(playlistEntity: PlaylistEntity):Playlist{
        return Playlist(playlistEntity.playlistName,playlistEntity.playlistDescr,playlistEntity.playlistArtUriString,playlistEntity.playlistTracksNumber)
    }
}