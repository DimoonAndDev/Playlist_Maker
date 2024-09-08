package com.example.playlistmaker.media.player.ui


import android.content.Context
import android.os.Bundle
import android.util.TypedValue
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.content.res.AppCompatResources
import androidx.core.os.bundleOf
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import com.bumptech.glide.Glide
import com.bumptech.glide.load.resource.bitmap.RoundedCorners
import com.example.playlistmaker.R
import com.example.playlistmaker.databinding.FragmentPlayerTrackBinding
import com.example.playlistmaker.media.player.ui.mapper.PlayerStatusMapper
import com.example.playlistmaker.media.player.ui.models.PlayerStatus
import com.example.playlistmaker.media.player.ui.models.PlayerTrack
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import org.koin.androidx.viewmodel.ext.android.viewModel

class PlayerTrackFragment : Fragment() {
    private lateinit var track: PlayerTrack
    private lateinit var binding: FragmentPlayerTrackBinding
    private val viewModel by viewModel<PlayTrackFragmentViewModel>()

    companion object {
        const val TRACK_JSON = "TRACK_JSON"
        const val CLICK_DEBOUNCE_DELAY = 1000L
        fun createArgs(trackJson: String) = bundleOf(TRACK_JSON to trackJson)

    }


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?
    ): View {
        binding = FragmentPlayerTrackBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.TrackBackArrowImage.setOnClickListener { findNavController().popBackStack() }

        val trackJson = requireArguments().getString(TRACK_JSON)
        track = viewModel.getPlayerTrack(trackJson)
        viewModel.checkFavorite(track.trackId)
        renderTrack(track)
        unprepared()

        viewModel.getFavoriteLiveData().observe(viewLifecycleOwner) {
            when (it) {
                true -> binding.TrackPlayButtonLike.setCompoundDrawablesWithIntrinsicBounds(R.drawable.filled_heart_icon,0,0,0)

                else -> binding.TrackPlayButtonLike.setCompoundDrawablesWithIntrinsicBounds(R.drawable.empty_hearct_icon,0,0,0)
            }
        }

        viewModel.getMediaPlayerLiveData().observe(viewLifecycleOwner) {
            when (PlayerStatusMapper.map(it)) {
                PlayerStatus.STATE_PLAYING -> play()
                PlayerStatus.STATE_PAUSED -> pause()
                PlayerStatus.STATE_PREPARED -> prepared()
                else -> unprepared()
            }
        }
        viewModel.getTimerLiveData().observe(viewLifecycleOwner) {
            binding.TrackCurrTimeTextView.text =
                String.format("%d:%02d", it / 60, it % 60)
        }
        binding.TrackPlayButtonPlay.setOnClickListener {
            viewModel.clickMediaPlayerControl()
        }
        binding.TrackPlayButtonLike.setOnClickListener {
            if (clickDebounce()) {
                viewModel.favoriteClickControl(track)
            }
        }
    }
    private fun play() {

        binding.TrackPlayButtonPlay.setImageDrawable(
            AppCompatResources.getDrawable(
                requireContext(),
                R.drawable.pause_button_track
            )
        )
    }
    private fun pause() {
        binding.TrackPlayButtonPlay.setImageDrawable(
            AppCompatResources.getDrawable(
                requireContext(),
                R.drawable.play_button_track
            )
        )
    }
    private fun prepared() {
        pause()
        binding.TrackCurrTimeTextView.text =
            String.format("%d:%02d", 0, 0)
        binding.TrackPlayButtonPlay.isEnabled = true
    }
    private fun unprepared() {
        binding.TrackCurrTimeTextView.text =
            String.format("%d:%02d", 0, 0)
        binding.TrackPlayButtonPlay.isEnabled = false
    }
    override fun onPause() {
        super.onPause()
        if (PlayerStatusMapper.map(viewModel.getPlayerStatus()) == PlayerStatus.STATE_PLAYING) viewModel.pauseTrack()
    }

    override fun onDestroyView() {
        super.onDestroyView()
        viewModel.releasePlayer()
    }

    private fun renderTrack(track: PlayerTrack) {
        Glide.with(requireContext())
            .load(track.artworkUrl512)
            .placeholder(R.drawable.placeholder)
            .error(R.drawable.placeholder)
            .fitCenter()
            .transform(RoundedCorners(dpToPx(8f, binding.TrackArtImageView.context)))
            .into(binding.TrackArtImageView)

        binding.CountryTextView.text = track.country
        binding.GenreTextView.text = track.primaryGenreName
        binding.YearTextView.text = track.releaseDate?.take(4)
        if (!track.collectionName.isNullOrEmpty()) binding.AlbumTextView.text = track.collectionName
        else {
            binding.AlbumTextView.visibility = View.GONE
            binding.AlbumTitleTextView.visibility = View.GONE
        }
        binding.TrackMaxTimeTextView.text = track.trackTimeMillis
        binding.TrackArtistTextView.text = track.artistName
        binding.TrackNameTextView.text = track.trackName
    }

    private fun dpToPx(dp: Float, context: Context): Int {
        return TypedValue.applyDimension(
            TypedValue.COMPLEX_UNIT_DIP,
            dp,
            context.resources.displayMetrics
        ).toInt()
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