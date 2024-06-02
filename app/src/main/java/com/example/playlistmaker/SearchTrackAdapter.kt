package com.example.playlistmaker

import android.content.Intent
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.core.content.ContextCompat.startActivity
import androidx.recyclerview.widget.RecyclerView
import com.example.playlistmaker.data.shpr.SearchTrackHistoryHelper
import com.example.playlistmaker.domain.models.Track
import com.google.gson.Gson


val handler = Handler(Looper.getMainLooper())

class SearchTrackAdapter(private val tracks: MutableList<Track>) :
    RecyclerView.Adapter<SearchViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): SearchViewHolder {
        val view =
            LayoutInflater.from(parent.context).inflate(R.layout.search_track_unit, parent, false)
        return SearchViewHolder(view)
    }

    override fun getItemCount(): Int {
        return tracks.size
    }

    override fun onBindViewHolder(holder: SearchViewHolder, position: Int) {
        holder.bind(tracks[position])
        holder.itemView.setOnClickListener {
            if (clickDebounce()) {
                val searchHelper = SearchTrackHistoryHelper()
                searchHelper.saveTrack(holder.itemView.context, tracks[position])
                val intent = Intent(holder.itemView.context, PlayTrackActivity::class.java)
                val savedTrack = Gson().toJson(tracks[position])
                intent.putExtra("TRACK", savedTrack)
                startActivity(holder.itemView.context, intent, Bundle())
            }
        }

    }
}

private var isClickAllowed = true

private fun clickDebounce(): Boolean {
    val current = isClickAllowed
    if (isClickAllowed) {
        isClickAllowed = false
        handler.postDelayed({ isClickAllowed = true }, SearchActivity.CLICK_DEBOUNCE_DELAY)
    }
    return current
}


