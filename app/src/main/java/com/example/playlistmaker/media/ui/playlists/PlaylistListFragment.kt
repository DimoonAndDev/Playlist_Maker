package com.example.playlistmaker.media.ui.playlists

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.activity.addCallback
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.GridLayoutManager
import com.example.playlistmaker.R
import com.example.playlistmaker.databinding.MediaPlaylistsFragmentBinding
import com.example.playlistmaker.media.playlist_control.domain.models.Playlist
import com.example.playlistmaker.media.playlist_info.ui.PlaylistInfoFragment
import com.example.playlistmaker.media.ui.playlists.models.PlaylistListScreenStates
import com.google.android.material.dialog.MaterialAlertDialogBuilder
import org.koin.androidx.viewmodel.ext.android.viewModel

class PlaylistListFragment : Fragment() {
    private lateinit var binding: MediaPlaylistsFragmentBinding
    private val viewModel by viewModel<PlaylistsListViewModel>()
    private val playlists = mutableListOf(Playlist(""))
    private lateinit var playlistAdapter: PlaylistListAdapter
    private lateinit var exitDialog:MaterialAlertDialogBuilder

    companion object {

        fun newInstance() = PlaylistListFragment()
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = MediaPlaylistsFragmentBinding.inflate(inflater, container, false)
        return binding.root

    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        requireActivity().onBackPressedDispatcher.addCallback { exitDialog.show() }
        exitDialog =
            MaterialAlertDialogBuilder(requireContext(), R.style.Theme_PlDelTr_Dialog_Alert)
                .setTitle(getString(R.string.exit_dialog))
                .setPositiveButton(getString(R.string.yes)) { dialog, which ->
                    requireActivity().finish()

                }.setNegativeButton(R.string.no) { dialog, which ->

                }
        binding.MediaNewPlaylistButton.setOnClickListener {
            findNavController().navigate(R.id.action_mediaFragment_to_createPlaylistFragment)
        }
        binding.PlaylistListRecyclerView.layoutManager = GridLayoutManager(requireContext(), 2)
        playlistAdapter = PlaylistListAdapter(playlists)
        binding.PlaylistListRecyclerView.adapter = playlistAdapter

        viewModel.getPlaylistScreenStateLiveData().observe(viewLifecycleOwner) {

        }
        viewModel.getPlaylistScreenStateLiveData().observe(viewLifecycleOwner) {
            when (it) {
                is PlaylistListScreenStates.Empty -> showNoResults()
                is PlaylistListScreenStates.Loading -> showLoading()
                is PlaylistListScreenStates.HavePlaylists -> showResults(it.listOfPlaylists)
            }
        }
        viewModel.getPlaylistList()

        playlistAdapter.setOnClickListener(object : PlaylistListAdapter.OnClickListener{
            override fun onClick(position: Int, playlist: Playlist) {
                val playlistJson = viewModel.convertPLtoJson(playlist)
                findNavController().navigate(R.id.action_mediaFragment_to_playlistInfoFragment,PlaylistInfoFragment.createArgs(playlistJson))
            }

        })
    }

    private fun showLoading() {
        binding.PlaylistListRecyclerView.visibility = View.GONE
        binding.MediaNewPlaylistButton.visibility = View.GONE
        binding.MediaEmptyPlaylistsImage.visibility = View.GONE
        binding.MediaEmptyPlaylistsText.visibility = View.GONE

        binding.PlaylistsProgressBar.visibility = View.VISIBLE
    }

    private fun showResults(playlistsTaken: List<Playlist?>) {
        binding.PlaylistsProgressBar.visibility = View.GONE
        binding.MediaEmptyPlaylistsImage.visibility = View.GONE
        binding.MediaEmptyPlaylistsText.visibility = View.GONE

        playlists.clear()
        val playlistListWithoutNulls: List<Playlist> = playlistsTaken.filterNotNull()
        playlists.addAll(playlistListWithoutNulls.reversed())
        playlistAdapter.notifyDataSetChanged()
        binding.PlaylistListRecyclerView.visibility = View.VISIBLE
        binding.MediaNewPlaylistButton.visibility = View.VISIBLE
    }

    private fun showNoResults() {
        binding.PlaylistListRecyclerView.visibility = View.GONE
        binding.PlaylistsProgressBar.visibility = View.GONE

        binding.MediaNewPlaylistButton.visibility = View.VISIBLE
        binding.MediaEmptyPlaylistsImage.visibility = View.VISIBLE
        binding.MediaEmptyPlaylistsText.visibility = View.VISIBLE

    }

}