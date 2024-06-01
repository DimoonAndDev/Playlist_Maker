package com.example.playlistmaker.presentation.ui.player

import android.content.Context
import android.media.MediaPlayer
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.util.TypedValue
import android.view.View
import android.widget.ImageView
import android.widget.TextView
import androidx.appcompat.content.res.AppCompatResources
import com.bumptech.glide.Glide
import com.bumptech.glide.load.resource.bitmap.RoundedCorners
import com.example.playlistmaker.Creator
import com.example.playlistmaker.R

import com.example.playlistmaker.domain.models.PlayerTrack


class PlayTrackActivity : AppCompatActivity() {
    private lateinit var track: PlayerTrack
    private lateinit var playTrackButton: ImageView
    private lateinit var mainThreadHandler: Handler
    private val getPlayerTrack = Creator.provideGetPlayerTrackUseCase()
    private val mediaPlayerInteractor = Creator.provideMediaPlayerInteractor()
    private lateinit var trackCurrTimeTextView: TextView
    var seconds = 0L
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_play_track)

        mainThreadHandler = Handler(Looper.getMainLooper())

        val playBackArrowImage = findViewById<ImageView>(R.id.TrackBackArrowImage)

        val countryTextView = findViewById<TextView>(R.id.CountryTextView)
        val genreTextView = findViewById<TextView>(R.id.GenreTextView)
        val yearTextView = findViewById<TextView>(R.id.YearTextView)
        val albumTextView = findViewById<TextView>(R.id.AlbumTextView)
        val trackMaxTimeTextView = findViewById<TextView>(R.id.TrackMaxTimeTextView)

        val artistTextView = findViewById<TextView>(R.id.TrackArtistTextView)
        val trackNameTimeTextView = findViewById<TextView>(R.id.TrackNameTextView)

        val trackArtImageView = findViewById<ImageView>(R.id.TrackArtImageView)

        val albumTitleTextView = findViewById<TextView>(R.id.AlbumTitleTextView)

        playTrackButton = findViewById(R.id.TrackPlayButtonPlay)
        trackCurrTimeTextView = findViewById(R.id.TrackCurrTimeTextView)

        playBackArrowImage.setOnClickListener { this.finish() }

        val trackString = intent.getStringExtra("TRACK")
        track = getPlayerTrack.execute(trackString)
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

        val mediaPlayer = mediaPlayerInteractor.preparePlayer()

        playTrackButton.setOnClickListener {
            playbackControl()
            startTrackTimer()
        }
    }

    private var playerState = STATE_DEFAULT

    companion object {
        private const val STATE_DEFAULT = 0
        private const val STATE_PREPARED = 1
        private const val STATE_PLAYING = 2
        private const val STATE_PAUSED = 3
        private const val DELAY = 500L
    }

    private fun preparePlayer() {
        if (track.previewUrl != null) {
            mediaPlayer.setDataSource(track.previewUrl)
            mediaPlayer.prepareAsync()
            mediaPlayer.setOnPreparedListener {
                playTrackButton.isEnabled = true
                playerState = STATE_PREPARED
            }
            mediaPlayer.setOnCompletionListener {
                playTrackButton.setImageDrawable(
                    AppCompatResources.getDrawable(
                        this,
                        R.drawable.play_button_track
                    )
                )
                playerState = STATE_PREPARED
                trackCurrTimeTextView.text = String.format("%d:%02d", 0, 0)
                seconds = 0L
            }
        } else {
            playTrackButton.isEnabled = false
            playTrackButton.setImageDrawable(
                AppCompatResources.getDrawable(
                    this,
                    R.drawable.clearsearchbutton
                )
            )
        }
    }

    private fun startPlayer() {
        mediaPlayer.start()
        playTrackButton.setImageDrawable(
            AppCompatResources.getDrawable(
                this,
                R.drawable.pause_button_track
            )
        )
        playerState = STATE_PLAYING
    }

    private fun pausePlayer() {
        mediaPlayer.pause()
        playTrackButton.setImageDrawable(
            AppCompatResources.getDrawable(
                this,
                R.drawable.play_button_track
            )
        )
        playerState = STATE_PAUSED
    }

    private fun playbackControl() {
        when (playerState) {
            STATE_PLAYING -> {
                pausePlayer()
            }

            STATE_PREPARED, STATE_PAUSED -> {
                startPlayer()
            }
        }
    }

    override fun onPause() {
        super.onPause()
        if (track.previewUrl != null) pausePlayer()
    }

    override fun onDestroy() {
        super.onDestroy()
        mediaPlayer.release()
    }

    private fun startTrackTimer() {
        var startTime = System.currentTimeMillis()
        if (seconds != 0L) startTime -= (seconds * 1000)
        mainThreadHandler.post(
            createUpdateTimerTask(startTime)
        )
    }

    private fun createUpdateTimerTask(startTime: Long): Runnable {
        return object : Runnable {
            override fun run() {
                val elapsedTime = System.currentTimeMillis() - startTime
                if (playerState == STATE_PLAYING) {
                    seconds = elapsedTime / 1000
                    trackCurrTimeTextView.text =
                        String.format("%d:%02d", seconds / 60, seconds % 60)
                    mainThreadHandler.postDelayed(this, DELAY)
                }
            }
        }
    }


    private fun dpToPx(dp: Float, context: Context): Int {
        return TypedValue.applyDimension(
            TypedValue.COMPLEX_UNIT_DIP,
            dp,
            context.resources.displayMetrics
        ).toInt()
    }
}