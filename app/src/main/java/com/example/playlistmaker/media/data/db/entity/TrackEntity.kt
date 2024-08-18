package com.example.playlistmaker.media.data.db.entity

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "favorite_tracks")
data class TrackEntity(

    val trackName: String = "",      // Название композиции
    val artistName: String = "",     // Имя исполнителя
    val trackTimeMillis: Int = 0,   // Продолжительность трека
    val artworkUrl100: String? = "",  // Ссылка на изображение обложки
    val trackId: Int = 0,            //ID трека для идентификации в истории поиска
    val collectionName: String? = "", //Альбом, может не быть
    val releaseDate: String? = "",       //год релиза
    val primaryGenreName: String? = "",//жанр
    val country: String? = "",         // страна исполнителя
    val previewUrl: String? = "",    // ссылка на воспроизведение
    val isFavorite: Boolean = true

) {
    @PrimaryKey(autoGenerate = true)
    val id: Int = 0
}
