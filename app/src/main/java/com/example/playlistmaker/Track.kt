package com.example.playlistmaker

data class Track(
    val trackName: String,      // Название композиции
    val artistName: String,     // Имя исполнителя
    val trackTimeMillis: Int,   // Продолжительность трека
    val artworkUrl100: String?,  // Ссылка на изображение обложки
    val trackId: Int,            //ID трека для идентификации в истории поиска
    val collectionName: String?, //Альбом, может не быть
    val releaseDate: String?,       //год релиза
    val primaryGenreName: String?,//жанр
    val country: String?         // страна исполнителя

)
