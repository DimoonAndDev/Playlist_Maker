package com.example.playlistmaker

data class Track(val trackName: String,// Название композиции
                 val artistName: String, // Имя исполнителя
                 val trackTimeMillis: Int, // Продолжительность трека
                 val artworkUrl100: String,// Ссылка на изображение обложки
                 val trackId:Int                 )//ID трека для идентификации в истории поиска
