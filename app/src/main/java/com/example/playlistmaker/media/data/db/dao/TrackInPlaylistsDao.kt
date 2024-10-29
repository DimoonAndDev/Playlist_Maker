package com.example.playlistmaker.media.data.db.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.example.playlistmaker.media.data.db.entity.TrackInPlaylistEntity

@Dao
interface TrackInPlaylistsDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertTrackInPlaylists(trackInPlaylists: TrackInPlaylistEntity)

    @Query("DELETE from track_in_playlists WHERE trackID = :trackID")
    suspend fun deleteTrackInPlaylists(trackID: Int)


    @Query("SELECT * from track_in_playlists WHERE trackID = :trackID")
    suspend fun getTrackInPlaylist(trackID: Int): TrackInPlaylistEntity?

    @Query("UPDATE track_in_playlists SET numberOfPlaylists =:numberOfPlaylists WHERE trackID = :trackID")
    suspend fun updatePlaylistToTrack(numberOfPlaylists: Int, trackID: Int)
}