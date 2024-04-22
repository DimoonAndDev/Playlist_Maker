package com.example.playlistmaker.searchrecycler

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.core.content.ContextCompat.startActivity
import androidx.recyclerview.widget.RecyclerView
import com.example.playlistmaker.PlayTrackActivity
import com.example.playlistmaker.R
import com.example.playlistmaker.Track
import com.example.playlistmaker.searchrecycler.SearchTrackHistoryHelper

class SearchTrackAdapter(private val tracks:MutableList<Track>) :
    RecyclerView.Adapter<SearchViewHolder>() {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): SearchViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.search_track_unit,parent,false)
        return SearchViewHolder(view)
    }

    override fun getItemCount(): Int {
        return tracks.size
    }

    override fun onBindViewHolder(holder: SearchViewHolder, position: Int) {
        holder.bind(tracks[position])
        holder.itemView.setOnClickListener {
            val intent = Intent(holder.itemView.context,PlayTrackActivity::class.java)
            intent.putExtra("TRACK_ID",tracks[position].trackId)
            startActivity(holder.itemView.context,intent, Bundle())
        }
    }
}