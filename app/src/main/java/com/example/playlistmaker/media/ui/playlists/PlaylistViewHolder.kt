package com.example.playlistmaker.media.ui.playlists

import android.net.Uri
import android.view.View
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.playlistmaker.R
import com.example.playlistmaker.media.playlist_control.domain.models.Playlist


class PlaylistViewHolder(parentView: View,private val viewModel: PlaylistsListViewModel) : RecyclerView.ViewHolder(parentView) {

    private var playlistNameTextView: TextView = parentView.findViewById(R.id.PlaylistUnitNameText)
    private var tracksNumberTextView: TextView =
        parentView.findViewById(R.id.PlaylistTrackNumberText)
    private var trackArtImageView: ImageView = parentView.findViewById(R.id.PlaylistUnitImage)

    fun bind(
        playlist: Playlist
    ) {

        playlistNameTextView.text = playlist.name
        tracksNumberTextView.text =
            if (playlist.tracksNumber % 100 == 11 || playlist.tracksNumber % 100 == 11) "${playlist.tracksNumber} треков"
            else {
                when (playlist.tracksNumber % 10) {
                    0, 5, 6, 7, 8, 9 -> "${playlist.tracksNumber} треков"
                    1 -> "${playlist.tracksNumber} трек"
                    else -> "${playlist.tracksNumber} трека"
                }
            }

        if (playlist.artLink.isNotEmpty()) {
            val localArt = Uri.parse(viewModel.getPLArt(playlist.artLink))
            trackArtImageView.setImageURI(localArt)
            trackArtImageView.scaleType = ImageView.ScaleType.CENTER_CROP
        } else trackArtImageView.setImageResource(R.drawable.placeholder)


    }

}

