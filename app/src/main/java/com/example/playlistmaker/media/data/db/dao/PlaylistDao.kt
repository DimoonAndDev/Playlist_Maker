package com.example.playlistmaker.media.data.db.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.example.playlistmaker.media.data.db.entity.PlaylistEntity

@Dao
interface PlaylistDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertPlaylist(playlist: PlaylistEntity)

    @Query("DELETE from playlists WHERE playlist_id = :playlistID")
    suspend fun deletePlaylist(playlistID: Int)


    @Query("SELECT * from playlists")
    suspend fun getPlaylists():List<PlaylistEntity>

    @Query("UPDATE playlists SET playlistTracksRegister =:newRegister WHERE playlist_id = :playlistID")
    suspend fun updateTrackToPlaylist(newRegister:String, playlistID:Int)


}