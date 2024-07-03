package com.example.playlistmaker.player.ui

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
import androidx.lifecycle.ViewModelProvider
import com.bumptech.glide.Glide
import com.bumptech.glide.load.resource.bitmap.RoundedCorners
import com.example.playlistmaker.Creator
import com.example.playlistmaker.R
import com.example.playlistmaker.player.ui.models.PlayerStatus
import com.example.playlistmaker.player.ui.models.PlayerTrack
import com.example.playlistmaker.player.ui.mapper.PlayerStatusMapper
import com.example.playlistmaker.player.ui.models.PlayerStatus.STATE_DEFAULT
import com.example.playlistmaker.player.ui.models.PlayerStatus.STATE_PAUSED
import com.example.playlistmaker.player.ui.models.PlayerStatus.STATE_PLAYING
import com.example.playlistmaker.player.ui.models.PlayerStatus.STATE_PREPARED


class PlayTrackActivity : AppCompatActivity() {
    private lateinit var track: PlayerTrack
    private lateinit var playTrackButton: ImageView
    private lateinit var mainThreadHandler: Handler
    private val getPlayerTrack = Creator.provideGetPlayerTrackUseCase()
    private val mediaPlayerInteractor = Creator.provideMediaPlayerInteractor()
    private lateinit var trackCurrTimeTextView: TextView
    private lateinit var playerStatus: PlayerStatus

    private lateinit var countryTextView:TextView
    private lateinit var genreTextView:TextView
    private lateinit var yearTextView:TextView
    private lateinit var albumTextView:TextView
    private lateinit var trackMaxTimeTextView:TextView

    private lateinit var artistTextView:TextView
    private lateinit var trackNameTimeTextView:TextView

    private lateinit var trackArtImageView:ImageView

    private lateinit var albumTitleTextView:TextView

    private lateinit var viewModel: PlayTrackActivityViewModel

    private var seconds = 0L
    private val DELAY = 500L
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

        playBackArrowImage.setOnClickListener { this.finish() }

        val trackString = intent.getStringExtra("TRACK")
        track = getPlayerTrack.execute(trackString)

        renderTrack(track)
        unprepared()

        viewModel = ViewModelProvider(this,PlayTrackActivityViewModel.getViewModelFactory(track))[PlayTrackActivityViewModel::class.java]
        viewModel.preparePlayer(track.previewUrl)

        viewModel.getMediaPlayerLiveData().observe(this){
            when (PlayerStatusMapper.map(it)){
                STATE_PLAYING->play()
                STATE_PAUSED->pause()
                STATE_PREPARED->prepared()
                else ->unprepared()
            }
        }
        mainThreadHandler.post(getMediaPlayerPrepared())
        playTrackButton.setOnClickListener {
            viewModel.clickMediaPlayerControl()
        }
    }

    private fun getMediaPlayerPrepared(): Runnable {
        return object : Runnable {
            override fun run() {
                playerStatus = PlayerStatusMapper.map(mediaPlayerInteractor.getStatus())
                if (playerStatus != STATE_PREPARED) {
                    mainThreadHandler.postDelayed(this, DELAY)
                } else {
                    playTrackButton.isEnabled = true
                    mainThreadHandler.removeCallbacks(this)
                }
            }
        }
    }

    private fun play(){

        playTrackButton.setImageDrawable(
            AppCompatResources.getDrawable(
                this,
                R.drawable.pause_button_track
            )
        )
        startTrackTimer()
    }
    private fun pause(){
        playTrackButton.setImageDrawable(
            AppCompatResources.getDrawable(
                this,
                R.drawable.play_button_track
            )
        )
    }
    private fun prepared(){
        pause()
        trackCurrTimeTextView.text =
            String.format("%d:%02d", 0, 0)
        playTrackButton.isEnabled = true
    }
    private fun unprepared(){
        trackCurrTimeTextView.text =
            String.format("%d:%02d", 0, 0)
        playTrackButton.isEnabled = false
    }
//    private fun playbackControl() {
//        when (playerStatus) {
//            STATE_PLAYING -> {
//                playTrackButton.setImageDrawable(
//                    AppCompatResources.getDrawable(
//                        this,
//                        R.drawable.play_button_track
//                    )
//                )
//                playerStatus = STATE_PAUSED
//                mediaPlayerInteractor.clickPauseTrack()
//            }
//
//            STATE_PREPARED, STATE_PAUSED -> {
//                playTrackButton.setImageDrawable(
//                    AppCompatResources.getDrawable(
//                        this,
//                        R.drawable.pause_button_track
//                    )
//                )
//                startTrackTimer()
//                playerStatus = STATE_PLAYING
//                mediaPlayerInteractor.clickPlayTrack()
//            }
//
//            STATE_DEFAULT -> mediaPlayerInteractor.preparePlayer(track.previewUrl)
//        }
//    }

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
                if (PlayerStatusMapper.map(viewModel.getPlayerStatus()) == STATE_PLAYING) {
                    seconds = elapsedTime / 1000
                    trackCurrTimeTextView.text =
                        String.format("%d:%02d", seconds / 60, seconds % 60)

                    mainThreadHandler.postDelayed(this, DELAY)
                }
                if (PlayerStatusMapper.map(viewModel.getPlayerStatus()) == STATE_PREPARED) {
                    prepared()
                }
            }
        }
    }

    fun renderTrack(track: PlayerTrack) {
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
}