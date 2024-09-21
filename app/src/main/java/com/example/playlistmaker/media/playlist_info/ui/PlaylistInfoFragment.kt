package com.example.playlistmaker.media.playlist_info.ui


import android.net.Uri
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.ViewTreeObserver
import androidx.activity.OnBackPressedCallback
import androidx.core.os.bundleOf
import androidx.core.view.setPadding
import androidx.fragment.app.Fragment
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.playlistmaker.databinding.FragmentPlaylistInfoBinding
import com.example.playlistmaker.media.playlist_control.domain.models.Playlist
import com.example.playlistmaker.search.domain.models.Track
import com.google.android.material.bottomsheet.BottomSheetBehavior
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import org.koin.androidx.viewmodel.ext.android.viewModel


class PlaylistInfoFragment : Fragment() {
    private lateinit var playlist: Playlist
    private lateinit var binding: FragmentPlaylistInfoBinding
    private val viewModel by viewModel<PlaylistInfoFragmentViewModel>()
    private lateinit var recyclerPlaylistTrackListAdapter: PlaylistsInfoAdapter
    lateinit var tracksInPlaylist: MutableList<Track>
    var maxY: Int = 0

    companion object {
        const val PLAYLIST_JSON = "PLAYLIST_JSON"
        const val CLICK_DEBOUNCE_DELAY = 1000L


        fun createArgs(playlistJson: String?) = bundleOf(
            PLAYLIST_JSON to playlistJson
        )


    }


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?
    ): View {
        binding = FragmentPlaylistInfoBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.PLInfoArrowImage.setOnClickListener {
            backHandle()
        }
        requireActivity().onBackPressedDispatcher.addCallback(object : OnBackPressedCallback(true) {
            override fun handleOnBackPressed() {
                backHandle()
            }
        })
        val playlistJson = requireArguments().getString(PLAYLIST_JSON)
        playlist = viewModel.getPlaylist(playlistJson)
        tracksInPlaylist = mutableListOf(Track(), Track())
        // tracksInPlaylist.clear()
        renderPlaylist(playlist)



        binding.PLInfoBottomsheetTrackListRecyclerView.layoutManager =
            LinearLayoutManager(requireContext(), LinearLayoutManager.VERTICAL, false)
        recyclerPlaylistTrackListAdapter = PlaylistsInfoAdapter(tracksInPlaylist)
        binding.PLInfoBottomsheetTrackListRecyclerView.adapter = recyclerPlaylistTrackListAdapter

        //viewModel.getTracksInPlaylist(playlist.tracksRegister)

        val bottomSheetTrackListBehavior =
            BottomSheetBehavior.from(binding.PlInfoTrackListBottomsheet)
        val bottomSheetMenuBehavior = BottomSheetBehavior.from(binding.PLInfoMenuBottomsheet)


        bottomSheetTrackListBehavior.peekHeight =
        bottomSheetTrackListBehavior.state = BottomSheetBehavior.STATE_COLLAPSED

        bottomSheetMenuBehavior.state = BottomSheetBehavior.STATE_HIDDEN
        binding.PLInfoMenuButton.setOnClickListener {
            bottomSheetMenuBehavior.state = BottomSheetBehavior.STATE_EXPANDED
            binding.PLInfoBottomsheetOverlay.visibility = View.VISIBLE
        }
        bottomSheetMenuBehavior.addBottomSheetCallback(object :
            BottomSheetBehavior.BottomSheetCallback() {
            override fun onStateChanged(bottomSheet: View, newState: Int) {
                if (newState == BottomSheetBehavior.STATE_HIDDEN) binding.PLInfoBottomsheetOverlay.visibility =
                    View.GONE
            }

            override fun onSlide(bottomSheet: View, slideOffset: Float) {
            }

        })


    }

    private fun backHandle() {

    }


    private fun renderPlaylist(playlist: Playlist) {
        if (!playlist.artLink.isNullOrEmpty()) {
            binding.PLInfoArtImageView.setPadding(0)
            binding.PLInfoArtImageView.setImageURI(Uri.parse(playlist.artLink))
        }
        binding.PLInfoNameTextView.text = playlist.name
        binding.PLInfoDescrTextView.text = playlist.description
        val trackNumber = tracksInPlaylist.size
        binding.PLInfoTrackNumber.text =
            if (trackNumber % 100 == 11 || trackNumber % 100 == 12) "$trackNumber треков"
            else {
                when (trackNumber % 10) {
                    0, 5, 6, 7, 8, 9 -> "$trackNumber треков"
                    1 -> "$trackNumber трек"
                    else -> "$trackNumber трека"
                }
            }
        var trackTimeSumMillis = 0
        for (track in tracksInPlaylist) {
            trackTimeSumMillis += track.trackTimeMillis
        }
        val trackTimeInMinutes = trackTimeSumMillis / 60000
        binding.PLInfoTimeSum.text =
            if (trackTimeInMinutes % 100 == 11 || trackTimeInMinutes % 100 == 12) "$trackTimeInMinutes минут"
            else {
                when (trackTimeInMinutes % 10) {
                    0, 5, 6, 7, 8, 9 -> "$trackTimeInMinutes минут"
                    1 -> "$trackTimeInMinutes минута"
                    else -> "$trackTimeInMinutes минуты"
                }
            }
        if (tracksInPlaylist.isEmpty()) {
            binding.PLInfoBottomsheetTrackListRecyclerView.visibility = View.GONE
            binding.PLInfoEmptyTrackListText.visibility = View.VISIBLE
        } else {
            binding.PLInfoBottomsheetTrackListRecyclerView.visibility = View.VISIBLE
            binding.PLInfoEmptyTrackListText.visibility = View.GONE
        }
    }


    private var isClickAllowed = true
    private fun clickDebounce(): Boolean {
        val current = isClickAllowed
        if (isClickAllowed) {
            isClickAllowed = false
            lifecycleScope.launch {
                delay(CLICK_DEBOUNCE_DELAY)
                isClickAllowed = true
            }
        }
        return current
    }

}