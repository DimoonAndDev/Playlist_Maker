package com.example.playlistmaker.media.data.db.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.example.playlistmaker.media.data.db.entity.TrackEntity

@Dao
interface TrackDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertTrack(track:TrackEntity)

    @Query("DELETE from favorite_tracks WHERE trackId LIKE :trackId")
    suspend fun deleteTrackFromFavorites(trackId:Int)

    @Query("SELECT * from favorite_tracks")
    suspend fun getFavoriteTracks():List<TrackEntity>

    @Query("SELECT * from favorite_tracks WHERE trackId LIKE :trackId")
    suspend fun checkTrackInFavorites(trackId: Int):TrackEntity?
}