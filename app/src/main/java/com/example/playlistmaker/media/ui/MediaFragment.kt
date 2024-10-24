package com.example.playlistmaker.media.ui

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.example.playlistmaker.R
import com.example.playlistmaker.databinding.FragmentMediaBinding
import com.google.android.material.tabs.TabLayoutMediator

class MediaFragment : Fragment() {
    private lateinit var binding: FragmentMediaBinding
    private lateinit var tabMediator: TabLayoutMediator


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentMediaBinding.inflate(inflater,container,false)
        return binding.root

    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
       binding.MediaViewPager.adapter = MediaViewPagerAdapter(childFragmentManager,lifecycle)

        tabMediator =
            TabLayoutMediator(binding.MediaTabLayout, binding.MediaViewPager) { tab, position ->
                when (position) {
                    0 -> tab.text = getString(R.string.media_tab_favorites)
                    else -> tab.text = getString(R.string.media_tab_playlists)
                }
            }
        tabMediator.attach()
    }

    override fun onDestroyView() {
        super.onDestroyView()
        tabMediator.detach()
    }

    }
