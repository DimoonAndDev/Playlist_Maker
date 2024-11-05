package com.example.playlistmaker.search.ui


import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.playlistmaker.R
import com.example.playlistmaker.search.domain.models.Track


const val TRACK_INTENT_EXTRA = "TRACK"

class SearchTrackAdapter(private val tracks: MutableList<Track>) :
    RecyclerView.Adapter<SearchViewHolder>() {

    private var onClickListener: OnClickListener? = null
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
            onClickListener?.onClick(position, tracks[position])
        }

    }

    fun setOnClickListener(listener: OnClickListener) {
        onClickListener = listener
    }

    interface OnClickListener {
        fun onClick(position: Int, track: Track)
    }

}





