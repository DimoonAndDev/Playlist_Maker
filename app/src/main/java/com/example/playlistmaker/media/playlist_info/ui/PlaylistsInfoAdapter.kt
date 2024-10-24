package com.example.playlistmaker.media.playlist_info.ui


import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.playlistmaker.R
import com.example.playlistmaker.search.domain.models.Track


class PlaylistsInfoAdapter(private val trackInPL: MutableList<Track>) :
    RecyclerView.Adapter<PlaylistsInfoViewHolder>() {

    private var onClickListener: OnClickListener? = null
    private var onLongClickListener: OnLongClickListener? = null
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): PlaylistsInfoViewHolder {
        val view =
            LayoutInflater.from(parent.context).inflate(R.layout.search_track_unit, parent, false)
        return PlaylistsInfoViewHolder(view)
    }

    override fun getItemCount(): Int {
        return trackInPL.size
    }

    override fun onBindViewHolder(holder: PlaylistsInfoViewHolder, position: Int){

        holder.bind(trackInPL[position])
        holder.itemView.setOnClickListener {
            onClickListener?.onClick(position, trackInPL[position])
        }
        holder.itemView.setOnLongClickListener {
            onLongClickListener?.onLongClick(position,trackInPL[position])
            return@setOnLongClickListener true
        }

    }

    fun setOnClickListener(listener: OnClickListener) {
        onClickListener = listener
    }
    fun setOnLongListener(listener: OnLongClickListener){
        onLongClickListener = listener
    }

    interface OnClickListener {
        fun onClick(position: Int, track: Track)
    }
    interface OnLongClickListener{
        fun onLongClick(position: Int,track: Track)
    }

}





