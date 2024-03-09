package com.example.playlistmaker.searchrecycler

import android.content.Context
import android.util.TypedValue
import android.view.RoundedCorner
import android.view.View
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.bumptech.glide.load.resource.bitmap.RoundedCorners
import com.example.playlistmaker.R
import com.example.playlistmaker.Track
import java.util.SimpleTimeZone

class SearchViewHolder(parentView: View) : RecyclerView.ViewHolder(parentView) {

    private var trackNameTextView: TextView = parentView.findViewById(R.id.SearchTrackUnitName)
    private var artistAndTimeTextView: TextView = parentView.findViewById(R.id.SearchTrackUnitArtistTime)
    private var trackArtImageView: ImageView = parentView.findViewById(R.id.SearchTrackUnitImage)

    fun bind(
        track:Track
    ) {

        trackNameTextView.text = track.trackName.ifEmpty { "Track" }

        val artistNameLocal: String = track.artistName.ifEmpty { "Musician" }
        val trackTimeLocal: String = track.trackTime.ifEmpty { "0:00" }
        val artistAndTime = "$artistNameLocal • $trackTimeLocal"
        artistAndTimeTextView.text = artistAndTime

        Glide.with(itemView)
            .load(track.artworkUrl100)
            .placeholder(R.drawable.placeholder)
            .fitCenter()
            .transform(RoundedCorners(dpToPx(2f, itemView.context)))
            .into(trackArtImageView)
    }

    private fun dpToPx(dp: Float, context: Context): Int {
        return TypedValue.applyDimension(
            TypedValue.COMPLEX_UNIT_DIP,
            dp,
            context.resources.displayMetrics
        ).toInt()
    }

}

