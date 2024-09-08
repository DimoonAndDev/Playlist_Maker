package com.example.playlistmaker.media.ui.favorites

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.playlistmaker.R
import com.example.playlistmaker.databinding.MediaFavoritesFragmentBinding
import com.example.playlistmaker.media.player.ui.PlayerTrackFragment
import com.example.playlistmaker.media.ui.favorites.models.FavoritesScreenStates
import com.example.playlistmaker.search.domain.models.Track
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import org.koin.androidx.viewmodel.ext.android.viewModel

class FavoritesFragment : Fragment() {
    private lateinit var binding: MediaFavoritesFragmentBinding
    private val viewModel by viewModel<MediaFavoritesViewModel>()
    private val tracks = mutableListOf(Track())
    private lateinit var recyclerFavoritesTrackAdapter: FavoritesTrackAdapter

    companion object {
        fun newInstance() = FavoritesFragment()
        const val CLICK_DEBOUNCE_DELAY = 1000L
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {

        binding = MediaFavoritesFragmentBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onResume() {
        super.onResume()
        viewModel.getFavoriteTracks()
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.FavoritesRecyclerView.layoutManager =
            LinearLayoutManager(requireContext(), LinearLayoutManager.VERTICAL, false)
        recyclerFavoritesTrackAdapter = FavoritesTrackAdapter(tracks)
        binding.FavoritesRecyclerView.adapter = recyclerFavoritesTrackAdapter

        viewModel.getFavoritesScreenStateLiveData().observe(viewLifecycleOwner) {
            when (it) {
                is FavoritesScreenStates.Empty -> showNoResult()
                is FavoritesScreenStates.HaveFavorites -> showFavorites(it.listOfFavoriteTracks)
                is FavoritesScreenStates.Loading -> showLoading()
            }
        }
        recyclerFavoritesTrackAdapter.setOnClickListener(object :
            FavoritesTrackAdapter.OnClickListener {
            override fun onClick(position: Int, track: Track) {
                if (clickDebounce()) {
                    val savedTrack = viewModel.getGsonString(track)
                    findNavController().navigate(R.id.action_mediaFragment_to_playerTrackFragment,PlayerTrackFragment.createArgs(savedTrack))
                }
            }

        })
    }

    private var isClickAllowed = true
    private fun clickDebounce(): Boolean {
        val current = isClickAllowed
        if (isClickAllowed) {
            isClickAllowed = false
            viewLifecycleOwner.lifecycleScope.launch {
                delay(CLICK_DEBOUNCE_DELAY)
                isClickAllowed = true
            }
        }
        return current
    }
    override fun onStop() {
        super.onStop()
        isClickAllowed = true
    }

    private fun showNoResult() {
        binding.FavoritesRecyclerView.visibility = View.GONE
        binding.FavoritesProgressBar.visibility = View.GONE

        binding.MediaEmptyFavoritesImage.visibility = View.VISIBLE
        binding.MediaEmptyFavoritesText.visibility = View.VISIBLE
    }
    private fun showLoading(){
        binding.FavoritesRecyclerView.visibility = View.GONE
        binding.MediaEmptyFavoritesImage.visibility = View.GONE
        binding.MediaEmptyFavoritesText.visibility = View.GONE

        binding.FavoritesProgressBar.visibility = View.VISIBLE
    }

    private fun showFavorites(favoriteTracksList: List<Track?>) {
        binding.MediaEmptyFavoritesImage.visibility = View.GONE
        binding.MediaEmptyFavoritesText.visibility = View.GONE
        binding.FavoritesProgressBar.visibility = View.GONE

        tracks.clear()
        val favoritesWithoutNulls:List<Track> = favoriteTracksList.filterNotNull()
        tracks.addAll(favoritesWithoutNulls.reversed())
        recyclerFavoritesTrackAdapter.notifyDataSetChanged()
        binding.FavoritesRecyclerView.visibility = View.VISIBLE
    }
}
