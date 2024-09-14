package com.example.playlistmaker.media.player.ui


import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.playlistmaker.R
import com.example.playlistmaker.media.playlist_control.domain.models.Playlist
import com.example.playlistmaker.media.ui.playlists.PlaylistsListViewModel
import com.example.playlistmaker.search.domain.models.Track

class PlayerPlaylistsAdapter(private val playlists: List<Playlist>) :
    RecyclerView.Adapter<PlayerPlaylistsViewHolder>() {

    private var onClickListener: OnClickListener? = null
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): PlayerPlaylistsViewHolder {
        val view =
            LayoutInflater.from(parent.context).inflate(R.layout.playlist_addtrack_unit, parent, false)
        return PlayerPlaylistsViewHolder(view)
    }

    override fun getItemCount(): Int {
        return playlists.size
    }

    override fun onBindViewHolder(holder: PlayerPlaylistsViewHolder, position: Int) {

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





