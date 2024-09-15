package com.example.playlistmaker.media.player.ui


import android.net.Uri
import android.view.View
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.playlistmaker.R
import com.example.playlistmaker.media.playlist_control.domain.models.Playlist

class PlayerPlaylistsViewHolder(parentView: View) : RecyclerView.ViewHolder(parentView) {

    private var playlistArtImage: ImageView = parentView.findViewById(R.id.PLAddTrackUnitImage)
    private var playlistNameText: TextView = parentView.findViewById(R.id.PLAddTrackUnitName)
    private var playlistTrackNumber: TextView = parentView.findViewById(R.id.PLAddTrackUnitNumber)


    fun bind(
        playlist: Playlist
    ) {

        playlistNameText.text = playlist.name
        val trackNumber = playlist.tracksRegister.size
        playlistTrackNumber.text =
            if (trackNumber % 100 == 11 || trackNumber % 100 == 12) "$trackNumber треков"
            else {
                when (trackNumber % 10) {
                    0, 5, 6, 7, 8, 9 -> "$trackNumber треков"
                    1 -> "$trackNumber трек"
                    else -> "$trackNumber трека"
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

