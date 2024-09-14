package com.example.playlistmaker.media.player.ui

import android.content.Context
import android.net.Uri
import android.util.TypedValue
import android.view.View
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.bumptech.glide.load.resource.bitmap.RoundedCorners
import com.example.playlistmaker.R
import com.example.playlistmaker.media.playlist_control.domain.models.Playlist
import com.example.playlistmaker.search.domain.models.Track
import java.text.SimpleDateFormat
import java.util.Locale

class PlayerPlaylistsViewHolder(parentView: View) : RecyclerView.ViewHolder(parentView) {

    private var playlistArtImage: ImageView = parentView.findViewById(R.id.PLAddTrackUnitImage)
    private var playlistNameText: TextView = parentView.findViewById(R.id.PLAddTrackUnitName)
    private var playlistTrackNumber: TextView = parentView.findViewById(R.id.PLAddTrackUnitNumber)


    fun bind(
        playlist: Playlist
    ) {

        playlistNameText.text = playlist.name
        playlistTrackNumber.text =
            if (playlist.tracksNumber % 100 == 11 || playlist.tracksNumber % 100 == 11) "${playlist.tracksNumber} треков"
            else {
                when (playlist.tracksNumber % 10) {
                    0, 5, 6, 7, 8, 9 -> "${playlist.tracksNumber} треков"
                    1 -> "${playlist.tracksNumber} трек"
                    else -> "${playlist.tracksNumber} трека"
                }
            }


        playlistArtImage.scaleType = ImageView.ScaleType.CENTER_CROP
        Glide.with(itemView)
            .load(Uri.parse(playlist.artLink))
            .placeholder(R.drawable.placeholder)
            .error(R.drawable.placeholder)
            .fitCenter()
            .into(playlistArtImage)
    }



}

