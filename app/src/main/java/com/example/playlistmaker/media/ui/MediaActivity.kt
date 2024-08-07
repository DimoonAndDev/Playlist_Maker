package com.example.playlistmaker.media.ui

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.example.playlistmaker.R
import com.example.playlistmaker.databinding.ActivityMediaBinding
import com.google.android.material.tabs.TabLayoutMediator

class MediaActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMediaBinding
    private lateinit var tabMediator: TabLayoutMediator
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMediaBinding.inflate(layoutInflater)
        setContentView(binding.root)
        binding.MediaBackArrowImage.setOnClickListener {
            this.finish()
        }

        binding.MediaViewPager.adapter = MediaViewPagerAdapter(supportFragmentManager, lifecycle)

        tabMediator =
            TabLayoutMediator(binding.MediaTabLayout, binding.MediaViewPager) { tab, position ->
                when (position) {
                    0 -> tab.text = getString(R.string.media_tab_favorites)
                    else -> tab.text = getString(R.string.media_tab_playlists)
                }
            }
        tabMediator.attach()


    }

    override fun onDestroy() {
        super.onDestroy()
        tabMediator.detach()
    }
}