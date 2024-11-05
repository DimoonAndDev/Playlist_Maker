package com.example.playlistmaker.media.ui.playlists.pl_info


import android.content.Context
import android.util.TypedValue
import android.view.View
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.bumptech.glide.load.resource.bitmap.RoundedCorners
import com.example.playlistmaker.R
import com.example.playlistmaker.search.domain.models.Track
import java.text.SimpleDateFormat
import java.util.Locale

class PlaylistsInfoViewHolder(parentView: View) : RecyclerView.ViewHolder(parentView) {

    private var trackNameTextView: TextView = parentView.findViewById(R.id.SearchTrackUnitName)
    private var artistTextView: TextView = parentView.findViewById(R.id.SearchTrackUnitArtist)
    private var timeTextView: TextView = parentView.findViewById(R.id.SearchTrackUnitTime)
    private var trackArtImageView: ImageView = parentView.findViewById(R.id.SearchTrackUnitImage)


    fun bind(
        track: Track
    ) {

        trackNameTextView.text =
            track.trackName.ifEmpty { itemView.context.getString(R.string.track_name_placeholder) }
        artistTextView.text =
            track.artistName.ifEmpty { itemView.context.getString(R.string.track_artist_placeholder) }
        timeTextView.text =
            SimpleDateFormat("mm:ss", Locale.getDefault()).format(track.trackTimeMillis)
                .ifEmpty { itemView.context.getString(R.string.track_time_placeholder) }

        Glide.with(itemView)
            .load(track.artworkUrl100)
            .placeholder(R.drawable.placeholder)
            .error(R.drawable.placeholder)
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

