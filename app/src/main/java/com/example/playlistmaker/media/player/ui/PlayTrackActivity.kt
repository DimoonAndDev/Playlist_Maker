package com.example.playlistmaker.media.player.ui

import android.content.Context
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.util.TypedValue
import android.view.View
import android.widget.ImageView
import android.widget.TextView
import androidx.appcompat.content.res.AppCompatResources
import androidx.appcompat.widget.AppCompatButton
import androidx.lifecycle.lifecycleScope
import com.bumptech.glide.Glide
import com.bumptech.glide.load.resource.bitmap.RoundedCorners
import com.example.playlistmaker.R
import com.example.playlistmaker.media.player.ui.models.PlayerTrack
import com.example.playlistmaker.media.player.ui.mapper.PlayerStatusMapper
import com.example.playlistmaker.media.player.ui.models.PlayerStatus.STATE_PAUSED
import com.example.playlistmaker.media.player.ui.models.PlayerStatus.STATE_PLAYING
import com.example.playlistmaker.media.player.ui.models.PlayerStatus.STATE_PREPARED
import com.example.playlistmaker.search.ui.SearchFragment
import com.example.playlistmaker.search.ui.TRACK_INTENT_EXTRA
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import org.koin.androidx.viewmodel.ext.android.viewModel


class PlayTrackActivity : AppCompatActivity() {
    private lateinit var track: PlayerTrack
    private lateinit var playTrackButton: ImageView
    private lateinit var mainThreadHandler: Handler
    private lateinit var trackCurrTimeTextView: TextView

    private lateinit var countryTextView: TextView
    private lateinit var genreTextView: TextView
    private lateinit var yearTextView: TextView
    private lateinit var albumTextView: TextView
    private lateinit var trackMaxTimeTextView: TextView

    private lateinit var artistTextView: TextView
    private lateinit var trackNameTimeTextView: TextView

    private lateinit var trackArtImageView: ImageView

    private lateinit var albumTitleTextView: TextView

    private val viewModel by viewModel<PlayTrackActivityViewModel>()


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_play_track)

        mainThreadHandler = Handler(Looper.getMainLooper())

        val playBackArrowImage = findViewById<ImageView>(R.id.TrackBackArrowImage)

        countryTextView = findViewById(R.id.CountryTextView)
        genreTextView = findViewById(R.id.GenreTextView)
        yearTextView = findViewById(R.id.YearTextView)
        albumTextView = findViewById(R.id.AlbumTextView)
        trackMaxTimeTextView = findViewById(R.id.TrackMaxTimeTextView)

        artistTextView = findViewById(R.id.TrackArtistTextView)
        trackNameTimeTextView = findViewById(R.id.TrackNameTextView)

        trackArtImageView = findViewById(R.id.TrackArtImageView)

        albumTitleTextView = findViewById(R.id.AlbumTitleTextView)

        playTrackButton = findViewById(R.id.TrackPlayButtonPlay)
        trackCurrTimeTextView = findViewById(R.id.TrackCurrTimeTextView)

        val trackPlayButtonLike = findViewById<AppCompatButton>(R.id.TrackPlayButtonLike)


        playBackArrowImage.setOnClickListener { this.finish() }
        val trackString = intent.getStringExtra(TRACK_INTENT_EXTRA)
        track = viewModel.getPlayerTrack(trackString)
        viewModel.checkFavorite(track.trackId)
        renderTrack(track)
        unprepared()

        viewModel.getFavoriteLiveData().observe(this) {
            when (it) {
                true -> trackPlayButtonLike.setCompoundDrawablesWithIntrinsicBounds(
                    R.drawable.filled_heart_icon,
                    0,
                    0,
                    0
                )

                else -> trackPlayButtonLike.setCompoundDrawablesWithIntrinsicBounds(
                    R.drawable.empty_hearct_icon,
                    0,
                    0,
                    0
                )
            }
        }


        viewModel.getMediaPlayerLiveData().observe(this) {
            when (PlayerStatusMapper.map(it)) {
                STATE_PLAYING -> play()
                STATE_PAUSED -> pause()
                STATE_PREPARED -> prepared()
                else -> unprepared()
            }
        }
        viewModel.getTimerLiveData().observe(this) {
            trackCurrTimeTextView.text =
                String.format("%d:%02d", it / 60, it % 60)
        }
        playTrackButton.setOnClickListener {
            viewModel.clickMediaPlayerControl()
        }

        trackPlayButtonLike.setOnClickListener {
            if (clickDebounce()){
                viewModel.favoriteClickControl(track)
            }
        }
    }

    private fun play() {

        playTrackButton.setImageDrawable(
            AppCompatResources.getDrawable(
                this,
                R.drawable.pause_button_track
            )
        )
    }

    private fun pause() {
        playTrackButton.setImageDrawable(
            AppCompatResources.getDrawable(
                this,
                R.drawable.play_button_track
            )
        )
    }

    private fun prepared() {
        pause()
        trackCurrTimeTextView.text =
            String.format("%d:%02d", 0, 0)
        playTrackButton.isEnabled = true
    }

    private fun unprepared() {
        trackCurrTimeTextView.text =
            String.format("%d:%02d", 0, 0)
        playTrackButton.isEnabled = false
    }

    override fun onPause() {
        super.onPause()
        if (PlayerStatusMapper.map(viewModel.getPlayerStatus()) == STATE_PLAYING) {
            viewModel.pauseTrack()
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        viewModel.releasePlayer()
    }

    private fun renderTrack(track: PlayerTrack) {
        Glide.with(applicationContext)
            .load(track.artworkUrl512)
            .placeholder(R.drawable.placeholder)
            .error(R.drawable.placeholder)
            .fitCenter()
            .transform(RoundedCorners(dpToPx(8f, trackArtImageView.context)))
            .into(trackArtImageView)

        countryTextView.text = track.country
        genreTextView.text = track.primaryGenreName
        yearTextView.text = track.releaseDate?.take(4)
        if (!track.collectionName.isNullOrEmpty()) albumTextView.text = track.collectionName
        else {
            albumTextView.visibility = View.GONE
            albumTitleTextView.visibility = View.GONE
        }
        trackMaxTimeTextView.text = track.trackTimeMillis
        artistTextView.text = track.artistName
        trackNameTimeTextView.text = track.trackName
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
                delay(SearchFragment.CLICK_DEBOUNCE_DELAY)
                isClickAllowed = true
            }
        }
        return current
    }

}