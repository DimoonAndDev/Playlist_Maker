package com.example.playlistmaker.media.playlist_control.data.repository

import android.content.Context
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.net.Uri
import android.os.Environment
import androidx.core.net.toUri
import com.example.playlistmaker.media.playlist_control.domain.repository.PlaylistArtRepository
import java.io.File
import java.io.FileOutputStream

class PlaylistArtRepositroyImpl(private val contextApp: Context) : PlaylistArtRepository {
    override fun savePLArt(uriString: String):String {
        val uri = Uri.parse(uriString)
        val filePath = File(
            contextApp.getExternalFilesDir(Environment.DIRECTORY_PICTURES),
            "PlaylistArts_album"
        )
        if (!filePath.exists()) {filePath.mkdirs()}
        val file = File(filePath, "${uriString.takeLast(10)}.jpg")
        val inputStream = contextApp.contentResolver.openInputStream(uri)
        val outputStream = FileOutputStream(file)
        BitmapFactory.decodeStream(inputStream)
            .compress(Bitmap.CompressFormat.JPEG, 30, outputStream)
        return file.toUri().toString()
    }

    override suspend fun getPLArt(playlistArtUriString: String): String {
        val filePath = File(contextApp.getExternalFilesDir(Environment.DIRECTORY_PICTURES),"Playlist_album")
        val file = File (filePath,"${playlistArtUriString.takeLast(10)}.jpg")
        //return BitmapFactory.decodeFile(file.absolutePath).toString()
        return file.toUri().toString()
    }



}