package com.example.playlistmaker.media.data.db.entity

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "track_in_playlists")
class TrackInPlaylistEntity(
    val trackID: Int = 0,
    val trackJson: String = "",
    val numberOfPlaylists:Int = 0,
    @PrimaryKey(autoGenerate = true)
    val id: Int = 0
)