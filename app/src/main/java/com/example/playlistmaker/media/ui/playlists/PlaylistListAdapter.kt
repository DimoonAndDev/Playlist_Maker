package com.example.playlistmaker.media.ui.playlists


import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.playlistmaker.R
import com.example.playlistmaker.media.playlist_control.domain.models.Playlist

class PlaylistListAdapter(private val playlists: List<Playlist>) :
    RecyclerView.Adapter<PlaylistViewHolder>() {

    private var onClickListener: OnClickListener? = null
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): PlaylistViewHolder {
        val view =
            LayoutInflater.from(parent.context).inflate(R.layout.playlist_list_unit, parent, false)
        return PlaylistViewHolder(view)
    }

    override fun getItemCount(): Int {
        return playlists.size
    }

    override fun onBindViewHolder(holder: PlaylistViewHolder, position: Int) {

        holder.bind(playlists[position])
        holder.itemView.setOnClickListener {
            onClickListener?.onClick(position, playlists[position])
        }

    }

    fun setOnClickListener(listener: OnClickListener) {
        onClickListener = listener
    }

    interface OnClickListener {
        fun onClick(position: Int, playlist: Playlist)
    }

}





