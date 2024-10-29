package com.example.playlistmaker.media.ui.playlists.pl_list

import android.net.Uri
import android.view.View
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.playlistmaker.R
import com.example.playlistmaker.media.domain.models.Playlist


class PlaylistViewHolder(parentView: View) :
    RecyclerView.ViewHolder(parentView) {

    private var playlistNameTextView: TextView = parentView.findViewById(R.id.PlaylistUnitNameText)
    private var tracksNumberTextView: TextView =
        parentView.findViewById(R.id.PlaylistTrackNumberText)
    private var trackArtImageView: ImageView = parentView.findViewById(R.id.PlaylistUnitImage)

    fun bind(
        playlist: Playlist
    ) {

        playlistNameTextView.text = playlist.name
        val trackNumber = playlist.tracksRegister.size
        tracksNumberTextView.text =
            if (trackNumber % 100 == 11 || trackNumber % 100 == 12) "$trackNumber треков"
            else {
                when (trackNumber % 10) {
                    0, 5, 6, 7, 8, 9 -> "$trackNumber треков"
                    1 -> "$trackNumber трек"
                    else -> "$trackNumber трека"
                }
            }


        trackArtImageView.scaleType = ImageView.ScaleType.CENTER_CROP
        Glide.with(itemView)
            .load(Uri.parse(playlist.artLink))
            .placeholder(R.drawable.placeholder)
            .error(R.drawable.placeholder)
            .fitCenter()
            .into(trackArtImageView)

    }

}

