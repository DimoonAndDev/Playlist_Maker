package com.example.playlistmaker.media.playlist_control.ui


import android.content.Intent
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.WindowManager
import android.widget.ImageView
import android.widget.Toast
import androidx.activity.result.ActivityResultLauncher
import androidx.activity.result.PickVisualMediaRequest
import androidx.activity.result.contract.ActivityResultContracts
import androidx.appcompat.content.res.AppCompatResources
import androidx.core.widget.addTextChangedListener
import androidx.navigation.fragment.findNavController
import com.example.playlistmaker.R
import com.example.playlistmaker.databinding.FragmentCreatePlaylistBinding
import com.example.playlistmaker.media.playlist_control.domain.models.Playlist
import org.koin.androidx.viewmodel.ext.android.viewModel

class CreatePlaylistFragment : Fragment() {
    private val viewModel by viewModel<CreatePlaylistViewModel>()

    companion object {}

    private lateinit var binding: FragmentCreatePlaylistBinding
    private lateinit var pickMedia: ActivityResultLauncher<PickVisualMediaRequest>

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentCreatePlaylistBinding.inflate(inflater, container, false)
        return binding.root

    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        requireActivity().window.setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_ADJUST_PAN)
        binding.CrPLBackArrowImage.setOnClickListener { findNavController().popBackStack() }
        binding.CrPlCreateButton.isEnabled = false
        binding.CrPlTextViewPlaylistNameHint.setShadowLayer(0f, 0f, 0f, 0)
        binding.CrPlTextViewPlaylistDescrHint.setShadowLayer(0f, 0f, 0f, 0)
        var artUriString:String = ""

        binding.CrPLPlaylistName.addTextChangedListener {
            if (it.isNullOrEmpty()) makeEditNameStart()
            else makeEditNameFilled()
        }
        binding.CrPLPlaylistDescr.addTextChangedListener {
            if (it.isNullOrEmpty()) makeEditDescrStart()
            else makeEditDescrFilled()
        }
        pickMedia =
            registerForActivityResult(ActivityResultContracts.PickVisualMedia()) { uri ->
                if (uri != null) {
                    binding.CrPLArtImageView.setImageURI(uri)
                    binding.CrPLArtImageView.scaleType = ImageView.ScaleType.CENTER_CROP
                    viewModel.savePLArt(uri)
                    artUriString = uri.toString()
                    val flag = Intent.FLAG_GRANT_READ_URI_PERMISSION
                    requireContext().contentResolver.takePersistableUriPermission(uri,flag)
                } else {
                    Toast.makeText(requireContext(), "Арт не выбран", Toast.LENGTH_SHORT).show()
                }
            }

        binding.CrPLArtImageView.setOnClickListener {

            pickMedia.launch(PickVisualMediaRequest(ActivityResultContracts.PickVisualMedia.ImageOnly))
        }

        binding.CrPlCreateButton.setOnClickListener {
            val playlist = Playlist(binding.CrPLPlaylistName.text.toString(),binding.CrPLPlaylistDescr.text.toString(),artUriString)
            viewModel.savePlaylist(playlist)
            Toast.makeText(requireContext(),"Плейлист создан",Toast.LENGTH_SHORT).show()
            findNavController().popBackStack()
        }
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