package com.example.playlistmaker.media.data.db.entity

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "playlists")
data class PlaylistEntity(
    val playlistName:String,
    val playlistDescr:String = "Без описания",
    val playlistArtUriString: String = "",
    var playlistTracksRegister:String= "",
    @PrimaryKey(autoGenerate = true)
    @ColumnInfo(name = "playlist_id")
    val id:Int = 0
    )