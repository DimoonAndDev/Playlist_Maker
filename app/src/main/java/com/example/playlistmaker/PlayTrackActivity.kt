package com.example.playlistmaker

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
import com.bumptech.glide.Glide
import com.bumptech.glide.load.resource.bitmap.RoundedCorners
import com.example.playlistmaker.presentation.models.PlayerStatus
import com.example.playlistmaker.presentation.models.PlayerTrack
import com.example.playlistmaker.presentation.mapper.PlayerStatusMapper
import com.example.playlistmaker.presentation.models.PlayerStatus.STATE_DEFAULT
import com.example.playlistmaker.presentation.models.PlayerStatus.STATE_PAUSED
import com.example.playlistmaker.presentation.models.PlayerStatus.STATE_PLAYING
import com.example.playlistmaker.presentation.models.PlayerStatus.STATE_PREPARED


class PlayTrackActivity : AppCompatActivity() {
    private lateinit var track: PlayerTrack
    private lateinit var playTrackButton: ImageView
    private lateinit var mainThreadHandler: Handler
    private val getPlayerTrack = Creator.provideGetPlayerTrackUseCase()
    private val mediaPlayerInteractor = Creator.provideMediaPlayerInteractor()
    private lateinit var trackCurrTimeTextView: TextView
    private lateinit var playerStatus: PlayerStatus
    private var seconds = 0L
    private val DELAY = 500L
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
        playTrackButton.isEnabled = false
        playerStatus = STATE_DEFAULT
        mediaPlayerInteractor.preparePlayer(track.previewUrl)
        mainThreadHandler.post(getMediaPlayerPrepared())
        playTrackButton.setOnClickListener {
            playbackControl()
            }
    }

    private fun getMediaPlayerPrepared(): Runnable {
        return object :Runnable{
            override fun run() {
                playerStatus = PlayerStatusMapper.map(mediaPlayerInteractor.getStatus())
                if (playerStatus!=STATE_PREPARED){
                    mainThreadHandler.postDelayed(this,DELAY)
                }
                else{
                    playTrackButton.isEnabled = true
                    mainThreadHandler.removeCallbacks(this)
                    }
            }
        }
    }


    private fun playbackControl() {
        when (playerStatus) {
            STATE_PLAYING -> {
                playTrackButton.setImageDrawable(
                    AppCompatResources.getDrawable(
                        this,
                        R.drawable.play_button_track
                    )
                )
                playerStatus = STATE_PAUSED
                mediaPlayerInteractor.clickPauseTrack()
            }

            STATE_PREPARED, STATE_PAUSED -> {
                playTrackButton.setImageDrawable(
                    AppCompatResources.getDrawable(
                        this,
                        R.drawable.pause_button_track
                    )
                )
                startTrackTimer()
                playerStatus = STATE_PLAYING
                mediaPlayerInteractor.clickPlayTrack()
            }

            STATE_DEFAULT -> mediaPlayerInteractor.preparePlayer(track.previewUrl)
        }
    }

    override fun onPause() {
        super.onPause()
        if (playerStatus == STATE_PLAYING) mediaPlayerInteractor.clickPauseTrack()
    }

    override fun onDestroy() {
        super.onDestroy()
        mediaPlayerInteractor.releasePlayer()
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
                playerStatus = PlayerStatusMapper.map(mediaPlayerInteractor.getStatus())
                if (playerStatus == STATE_PLAYING) {
                    seconds = elapsedTime / 1000
                    trackCurrTimeTextView.text =
                        String.format("%d:%02d", seconds / 60, seconds % 60)

                    mainThreadHandler.postDelayed(this, DELAY)
                }
                if (playerStatus == STATE_PREPARED) {
                    seconds = 0L
                    trackCurrTimeTextView.text =
                        String.format("%d:%02d", seconds / 60, seconds % 60)
                    playTrackButton.setImageDrawable(
                        AppCompatResources.getDrawable(
                            applicationContext.applicationContext,
                            R.drawable.play_button_track
                        )
                    )
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