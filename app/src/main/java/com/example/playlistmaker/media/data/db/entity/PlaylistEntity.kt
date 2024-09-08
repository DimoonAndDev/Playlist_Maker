package com.example.playlistmaker.media.data.db.entity

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "playlists")
data class PlaylistEntity(
    val playlistName:String,
    val playlistDescr:String = "Без описания",
    val playlistArtUriString: String = "",
    var playlistTracksNumber:Int = 0,
    @PrimaryKey(autoGenerate = true)
    val id:Int = 0
    )