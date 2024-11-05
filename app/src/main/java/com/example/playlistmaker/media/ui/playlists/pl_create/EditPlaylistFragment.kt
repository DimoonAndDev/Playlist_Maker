package com.example.playlistmaker.media.ui.playlists.pl_create


import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.WindowManager
import android.widget.ImageView
import android.widget.Toast
import androidx.activity.addCallback
import androidx.activity.result.ActivityResultLauncher
import androidx.activity.result.PickVisualMediaRequest
import androidx.activity.result.contract.ActivityResultContracts
import androidx.appcompat.content.res.AppCompatResources
import androidx.core.os.bundleOf
import androidx.core.widget.addTextChangedListener
import androidx.navigation.fragment.findNavController
import com.example.playlistmaker.R
import com.example.playlistmaker.databinding.FragmentCreatePlaylistBinding
import com.example.playlistmaker.media.domain.models.Playlist
import com.example.playlistmaker.media.ui.playlists.pl_info.PlaylistInfoFragment
import org.koin.androidx.viewmodel.ext.android.viewModel

class EditPlaylistFragment : CreatePlaylistFragment() {
    private val viewModel by viewModel<EditPlaylistViewModel>()

    companion object {
        const val PL_JSON = "PL_JSON"

        fun createArgs(playlist: String?) = bundleOf(
            PL_JSON to playlist
        )
    }

    private lateinit var binding: FragmentCreatePlaylistBinding
    private lateinit var pickMedia: ActivityResultLauncher<PickVisualMediaRequest>
    private lateinit var playlist: Playlist


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        super.onCreateView(inflater, container, savedInstanceState)
        binding = FragmentCreatePlaylistBinding.inflate(inflater, container, false)
        return binding.root

    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        requireActivity().window.setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_ADJUST_PAN)
        with(binding) {
            CrPLBackArrowImage.setOnClickListener { backHandle() }
            CrPlCreateButton.isEnabled = false
            CrPlTextViewPlaylistNameHint.setShadowLayer(0f, 0f, 0f, 0)
            CrPlTextViewPlaylistDescrHint.setShadowLayer(0f, 0f, 0f, 0)
            CrPLHeadTextBar.text = getString(R.string.edpl_edit)
            CrPlCreateButton.text = getString(R.string.edpl_save_button_text)
        }
        playlist = viewModel.getPlfromJson(requireArguments().getString(PL_JSON) ?: "")
        var artStorageUriString = playlist.artLink
        requireActivity().onBackPressedDispatcher.addCallback { backHandle() }
        binding.CrPLPlaylistName.addTextChangedListener {
            if (it.isNullOrEmpty()) makeEditNameStart()
            else makeEditNameFilled()
        }
        binding.CrPLPlaylistDescr.addTextChangedListener {
            if (it.isNullOrEmpty()) makeEditDescrStart()
            else makeEditDescrFilled()
        }
        binding.CrPLPlaylistName.setText(playlist.name)
        binding.CrPLPlaylistDescr.setText(playlist.description)
        if (playlist.artLink.isNotEmpty()) {
            binding.CrPLArtImageView.scaleType = ImageView.ScaleType.CENTER_CROP
            binding.CrPLArtImageView.setImageURI(Uri.parse(playlist.artLink))
        }
        pickMedia =
            registerForActivityResult(ActivityResultContracts.PickVisualMedia()) { uri ->
                if (uri != null) {
                    artStorageUriString = viewModel.savePLArt(uri)
                    val flag = Intent.FLAG_GRANT_READ_URI_PERMISSION
                    requireContext().contentResolver.takePersistableUriPermission(uri, flag)
                    binding.CrPLArtImageView.scaleType = ImageView.ScaleType.CENTER_CROP
                    binding.CrPLArtImageView.setImageURI(Uri.parse(artStorageUriString))
                } else {
                    Toast.makeText(
                        requireContext(),
                        getString(R.string.crpl_toast_plart_notselected),
                        Toast.LENGTH_SHORT
                    ).show()
                }
            }

        binding.CrPLArtImageView.setOnClickListener {

            pickMedia.launch(PickVisualMediaRequest(ActivityResultContracts.PickVisualMedia.ImageOnly))
        }

        binding.CrPlCreateButton.setOnClickListener {
            val playlistNew = Playlist(
                binding.CrPLPlaylistName.text.toString(),
                binding.CrPLPlaylistDescr.text.toString(),
                artStorageUriString,
                playlist.tracksRegister,
                playlist.innerId
            )
            playlist = playlistNew
            viewModel.updatePlaylist(playlist)
            Toast.makeText(
                requireContext(),
                getString(R.string.edpl_toast_pledited),
                Toast.LENGTH_SHORT
            ).show()
            backHandle()
        }


    }


    private fun backHandle() {
        findNavController().navigate(
            R.id.action_editPlaylistFragment_to_playlistInfoFragment,
            PlaylistInfoFragment.createArgs(viewModel.plToJson(playlist))
        )
    }

    private fun makeEditNameStart() {
        if (viewModel.getTheme()) binding.CrPLPlaylistName.background =
            AppCompatResources.getDrawable(
                requireContext(),
                R.drawable.crpl_edittext_background_unused_dark
            )
        else binding.CrPLPlaylistName.background =
            AppCompatResources.getDrawable(
                requireContext(),
                R.drawable.crpl_edittext_background_unused_light
            )
        binding.CrPlCreateButton.isEnabled = false
        binding.CrPlTextViewPlaylistNameHint.visibility = View.GONE
    }

    private fun makeEditNameFilled() {
        binding.CrPLPlaylistName.background =
            AppCompatResources.getDrawable(
                requireContext(),
                R.drawable.crpl_edittext_used
            )
        binding.CrPlTextViewPlaylistNameHint.visibility = View.VISIBLE
        binding.CrPlCreateButton.isEnabled = true
    }

    private fun makeEditDescrStart() {
        if (viewModel.getTheme()) binding.CrPLPlaylistDescr.background =
            AppCompatResources.getDrawable(
                requireContext(),
                R.drawable.crpl_edittext_background_unused_dark
            )
        else binding.CrPLPlaylistDescr.background =
            AppCompatResources.getDrawable(
                requireContext(),
                R.drawable.crpl_edittext_background_unused_light
            )
        binding.CrPlTextViewPlaylistDescrHint.visibility = View.GONE
    }

    private fun makeEditDescrFilled() {
        binding.CrPLPlaylistDescr.background =
            AppCompatResources.getDrawable(
                requireContext(),
                R.drawable.crpl_edittext_used
            )
        binding.CrPlTextViewPlaylistDescrHint.visibility = View.VISIBLE

    }

}