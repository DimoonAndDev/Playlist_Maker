package com.example.playlistmaker.media.playlist_info.ui


import android.net.Uri
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.View.OnLongClickListener
import android.view.ViewGroup
import androidx.activity.OnBackPressedCallback
import androidx.core.os.bundleOf
import androidx.core.view.setPadding
import androidx.fragment.app.Fragment
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.playlistmaker.R
import com.example.playlistmaker.databinding.FragmentPlaylistInfoBinding
import com.example.playlistmaker.media.player.ui.PlayerTrackFragment
import com.example.playlistmaker.media.playlist_control.domain.models.Playlist
import com.example.playlistmaker.search.domain.models.Track
import com.google.android.material.bottomsheet.BottomSheetBehavior
import com.google.android.material.dialog.MaterialAlertDialogBuilder
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import org.koin.androidx.viewmodel.ext.android.viewModel


class PlaylistInfoFragment : Fragment() {
    private lateinit var playlist: Playlist
    private lateinit var binding: FragmentPlaylistInfoBinding
    private val viewModel by viewModel<PlaylistInfoFragmentViewModel>()
    private lateinit var recyclerPlaylistTrackListAdapter: PlaylistsInfoAdapter
    lateinit var tracksInPlaylist: MutableList<Track>
    private lateinit var confirmDeleteTrackDialog: MaterialAlertDialogBuilder
    private lateinit var choosenTrack: Track


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
        tracksInPlaylist.clear()


        viewModel.getScreenStateLiveData().observe(viewLifecycleOwner){
            playlist = it.playlist
            renderPlaylist(it.playlist,it.trackList)

        }
        confirmDeleteTrackDialog = MaterialAlertDialogBuilder(requireContext())
            .setTitle(getString(R.string.delete_track_confrimdialog_title))
            .setPositiveButton(getString(R.string.yes)) { dialog, which ->
                viewModel.deleteTrackFromPlaylist(choosenTrack, playlist)
                binding.PLInfoBottomsheetOverlay.visibility = View.GONE

            }.setNegativeButton(R.string.no) { dialog, which ->
                binding.PLInfoBottomsheetOverlay.visibility = View.GONE
            }


        binding.PLInfoBottomsheetTrackListRecyclerView.layoutManager =
            LinearLayoutManager(requireContext(), LinearLayoutManager.VERTICAL, false)
        recyclerPlaylistTrackListAdapter = PlaylistsInfoAdapter(tracksInPlaylist.asReversed())
        binding.PLInfoBottomsheetTrackListRecyclerView.adapter = recyclerPlaylistTrackListAdapter


        viewModel.getTracksInPlaylist(playlist)

        val bottomSheetTrackListBehavior =
            BottomSheetBehavior.from(binding.PlInfoTrackListBottomsheet)
        val bottomSheetMenuBehavior = BottomSheetBehavior.from(binding.PLInfoMenuBottomsheet)


        bottomSheetTrackListBehavior.peekHeight = 600
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
        recyclerPlaylistTrackListAdapter.setOnLongListener(object :
            PlaylistsInfoAdapter.OnLongClickListener {

            override fun onLongClick(position: Int, track: Track) {
                choosenTrack = track
                binding.PLInfoBottomsheetOverlay.visibility = View.VISIBLE
                confirmDeleteTrackDialog.show()

            }

        })
        recyclerPlaylistTrackListAdapter.setOnClickListener(object :
            PlaylistsInfoAdapter.OnClickListener {
            override fun onClick(position: Int, track: Track) {
                findNavController().navigate(
                    R.id.action_playlistInfoFragment_to_playerTrackFragment,
                    PlayerTrackFragment.createArgs(viewModel.getTrackJson(track), 4)
                )
            }
        })
    }

    private fun backHandle() {
        findNavController().popBackStack()
    }


    private fun renderPlaylist(playlist: Playlist,tracks:List<Track>) {
        tracksInPlaylist.clear()
        tracksInPlaylist.addAll(tracks)
        recyclerPlaylistTrackListAdapter.notifyDataSetChanged()
        if (playlist.artLink.isNotEmpty()) {
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