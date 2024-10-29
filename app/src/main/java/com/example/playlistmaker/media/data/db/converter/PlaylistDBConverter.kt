package com.example.playlistmaker.media.data.db.converter

import com.example.playlistmaker.media.data.db.entity.PlaylistEntity
import com.example.playlistmaker.media.domain.models.Playlist
import com.google.gson.Gson


class PlaylistDBConverter(private val gson: Gson) {
    fun map(playlist: Playlist): PlaylistEntity {
        return PlaylistEntity(
            playlist.name,
            playlist.description,
            playlist.artLink,
            fromListToString(playlist.tracksRegister)
        )
    }

    fun map(playlistEntity: PlaylistEntity): Playlist {
        return Playlist(
            playlistEntity.playlistName,
            playlistEntity.playlistDescr,
            playlistEntity.playlistArtUriString,
            fromStringToList(playlistEntity.playlistTracksRegister),
            playlistEntity.id
        )
    }

    private fun fromStringToList(register: String): List<Int> {
        return gson.fromJson(register, Array<Int>::class.java).toList()
    }

    fun fromListToString(listRegister: List<Int>): String {
        return gson.toJson(listRegister)
    }
}