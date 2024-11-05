package com.example.playlistmaker.media.ui.player.models

data class PlayerTrack(
    val trackName: String = "",      // Название композиции
    val artistName: String = "",     // Имя исполнителя
    val trackTimeMillis: String = "",   // Продолжительность трека
    val artworkUrl512: String? = "",  // Ссылка на изображение обложки
    val collectionName: String? = "", //Альбом, может не быть
    val releaseDate: String? = "",       //год релиза
    val primaryGenreName: String? = "",//жанр
    val country: String? = "",         // страна исполнителя
    val previewUrl: String = "",
    val trackId: Int = 0,
    val trackTimeMillisInt: Int = 0
)
