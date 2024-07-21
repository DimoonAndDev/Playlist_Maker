package com.example.playlistmaker.media.ui

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.example.playlistmaker.databinding.MediaFavoritesFragmentBinding
import org.koin.androidx.viewmodel.ext.android.viewModel

class FavoritesFragment : Fragment() {
    private lateinit var binding: MediaFavoritesFragmentBinding
    private val viewModel by viewModel<MediaFavoritesViewModel>()
    companion object {
        fun newInstance() = FavoritesFragment()
        }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        binding = MediaFavoritesFragmentBinding.inflate(inflater, container, false)
        return binding.root
    }

}