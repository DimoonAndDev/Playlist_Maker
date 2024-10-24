package com.example.playlistmaker.media.playlist_info.ui


import android.content.Context
import android.content.res.Resources
import android.net.Uri
import android.os.Bundle
import android.util.TypedValue
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.Toast
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
import com.example.playlistmaker.media.playlist_control.ui.EditPlaylistFragment
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
    private lateinit var tracksInPlaylist: MutableList<Track>
    private lateinit var confirmDeleteTrackDialog: MaterialAlertDialogBuilder
    private lateinit var confirmDeletePLDialog: MaterialAlertDialogBuilder
    private lateinit var choosenTrack: Track
    private lateinit var bottomSheetTrackListBehavior: BottomSheetBehavior<android.widget.LinearLayout>
    private lateinit var bottomSheetMenuBehavior: BottomSheetBehavior<android.widget.LinearLayout>


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
        binding.PLInfoShareButton.setOnClickListener {
            if (tracksInPlaylist.isEmpty()) {

                Toast.makeText(
                    requireContext(),
                    R.string.pl_info_empty_tracklist,
                    Toast.LENGTH_SHORT
                ).show()
            } else viewModel.sharePlaylist(requireContext())
        }

        binding.PLInfoMenuShareButton.setOnClickListener {
            if (tracksInPlaylist.isEmpty()) {
                bottomSheetMenuBehavior.state = BottomSheetBehavior.STATE_HIDDEN
                Toast.makeText(
                    requireContext(),
                    R.string.pl_info_empty_tracklist,
                    Toast.LENGTH_SHORT
                ).show()
            } else viewModel.sharePlaylist(requireContext())
        }
        binding.PLInfoMenuChangePlButton.setOnClickListener {
            findNavController().navigate(R.id.action_playlistInfoFragment_to_editPlaylistFragment,
                EditPlaylistFragment.createArgs(viewModel.plToJson(playlist)))
        }
        requireActivity().onBackPressedDispatcher.addCallback(object : OnBackPressedCallback(true) {
            override fun handleOnBackPressed() {
                backHandle()
            }
        })
        val playlistJson = requireArguments().getString(PLAYLIST_JSON)
        playlist = viewModel.getPlaylistFromJson(playlistJson)
        tracksInPlaylist = mutableListOf(Track(), Track())
        tracksInPlaylist.clear()


        viewModel.getScreenStateLiveData().observe(viewLifecycleOwner) {
            playlist = it.playlist
            renderPlaylist(it.playlist, it.trackList)

        }
        confirmDeleteTrackDialog =
            MaterialAlertDialogBuilder(requireContext(), R.style.Theme_PlDelTr_Dialog_Alert)
                .setTitle(getString(R.string.plinfo_delete_track_confrimdialog_title))
                .setPositiveButton(getString(R.string.yes)) { dialog, which ->
                    viewModel.deleteTrackFromPlaylist(choosenTrack, playlist)
                    binding.PLInfoBottomsheetOverlay.visibility = View.GONE

                }.setNegativeButton(R.string.no) { dialog, which ->
                    binding.PLInfoBottomsheetOverlay.visibility = View.GONE
                }
        confirmDeletePLDialog =
            MaterialAlertDialogBuilder(requireContext(), R.style.Theme_PlDelTr_Dialog_Alert)
                .setTitle(getString(R.string.plinfo_delete_pl_confrimdialog_title, playlist.name))
                .setPositiveButton(getString(R.string.yes)) { dialog, which ->
                    viewModel.deletePlaylist(playlist)
                    findNavController().popBackStack(R.id.mediaFragment, false)
                    binding.PLInfoBottomsheetOverlay.visibility = View.GONE

                }.setNegativeButton(R.string.no) { dialog, which ->
                    binding.PLInfoBottomsheetOverlay.visibility = View.GONE
                }
        binding.PLInfoMenuDeletePlButton.setOnClickListener {
            binding.PLInfoBottomsheetOverlay.visibility = View.VISIBLE
            confirmDeletePLDialog.show()
        }


        binding.PLInfoBottomsheetTrackListRecyclerView.layoutManager =
            LinearLayoutManager(requireContext(), LinearLayoutManager.VERTICAL, false)
        recyclerPlaylistTrackListAdapter = PlaylistsInfoAdapter(tracksInPlaylist.asReversed())
        binding.PLInfoBottomsheetTrackListRecyclerView.adapter = recyclerPlaylistTrackListAdapter


        viewModel.getTracksInPlaylist(playlist)

        bottomSheetTrackListBehavior =
            BottomSheetBehavior.from(binding.PlInfoTrackListBottomsheet)
        bottomSheetMenuBehavior = BottomSheetBehavior.from(binding.PLInfoMenuBottomsheet)


        bottomSheetTrackListBehavior.peekHeight =
            Resources.getSystem().displayMetrics.heightPixels - (Resources.getSystem().displayMetrics.widthPixels + dpToPx(
                198f,
                requireContext()
            ))
        bottomSheetTrackListBehavior.state = BottomSheetBehavior.STATE_COLLAPSED

        bottomSheetMenuBehavior.state = BottomSheetBehavior.STATE_HIDDEN
        binding.PLInfoMenuButton.setOnClickListener {
            bottomSheetMenuBehavior.state = BottomSheetBehavior.STATE_EXPANDED
            disableUnderButtons()
            binding.PLInfoBottomsheetOverlay.visibility = View.VISIBLE
        }
        bottomSheetMenuBehavior.addBottomSheetCallback(object :
            BottomSheetBehavior.BottomSheetCallback() {
            override fun onStateChanged(bottomSheet: View, newState: Int) {
                if (newState == BottomSheetBehavior.STATE_HIDDEN) {
                    enableUnderButtons()
                    binding.PLInfoBottomsheetOverlay.visibility =
                        View.GONE
                }
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
                if (clickDebounce()){
                findNavController().navigate(
                    R.id.action_playlistInfoFragment_to_playerTrackFragment,
                    PlayerTrackFragment.createArgs(viewModel.getTrackJson(track), 4)
                )}
            }
        })

    }

    private fun backHandle() {
        findNavController().popBackStack(R.id.mediaFragment, false)
    }

    private fun disableUnderButtons() {
        binding.PLInfoMenuButton.isEnabled = false
        binding.PLInfoShareButton.isEnabled = false
        binding.PLInfoArrowImage.isEnabled = false

        bottomSheetTrackListBehavior.isHideable = true
        bottomSheetTrackListBehavior.state = BottomSheetBehavior.STATE_HIDDEN
    }

    private fun enableUnderButtons() {
        binding.PLInfoMenuButton.isEnabled = true
        binding.PLInfoShareButton.isEnabled = true
        binding.PLInfoArrowImage.isEnabled = true

        bottomSheetTrackListBehavior.state = BottomSheetBehavior.STATE_COLLAPSED
        bottomSheetTrackListBehavior.isHideable = false
    }


    private fun renderPlaylist(playlist: Playlist, tracks: List<Track>) {
        tracksInPlaylist.clear()
        tracksInPlaylist.addAll(tracks)
        recyclerPlaylistTrackListAdapter.notifyDataSetChanged()
        if (playlist.artLink.isNotEmpty()) {
            binding.PLInfoArtImageView.setPadding(0)
            binding.PLInfoArtImageView.setImageURI(Uri.parse(playlist.artLink))
            binding.PLInfoMenuImage.setPadding(0)
            binding.PLInfoMenuImage.scaleType = ImageView.ScaleType.CENTER_CROP
            binding.PLInfoMenuImage.setImageURI(Uri.parse(playlist.artLink))
        }
        binding.PLInfoNameTextView.text = playlist.name
        binding.PLInfoMenuName.text = playlist.name
        binding.PLInfoDescrTextView.text = playlist.description
        val trackNumber = tracksInPlaylist.size
        val trackNumberString =
            if (trackNumber % 100 == 11 || trackNumber % 100 == 12) "$trackNumber треков"
            else {
                when (trackNumber % 10) {
                    0, 5, 6, 7, 8, 9 -> "$trackNumber треков"
                    1 -> "$trackNumber трек"
                    else -> "$trackNumber трека"
                }
            }
        binding.PLInfoTrackNumber.text = trackNumberString
        binding.PLInfoMenuTrackNumber.text = trackNumberString

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

    private fun dpToPx(dp: Float, context: Context): Int {
        return TypedValue.applyDimension(
            TypedValue.COMPLEX_UNIT_DIP,
            dp,
            context.resources.displayMetrics
        ).toInt()
    }
}