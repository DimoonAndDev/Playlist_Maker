package com.example.playlistmaker.media.ui

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.example.playlistmaker.databinding.MediaPlaylistsFragmentBinding
import org.koin.androidx.viewmodel.ext.android.viewModel

class PlaylistFragment : Fragment() {
    private lateinit var binding: MediaPlaylistsFragmentBinding
    private val viewModel by viewModel<MediaPlaylistsViewModel>()

    companion object {

        fun newInstance() = PlaylistFragment()
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = MediaPlaylistsFragmentBinding.inflate(inflater, container, false)
        return binding.root
    }
}