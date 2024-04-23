package com.example.playlistmaker

import android.content.Context
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.TypedValue
import android.view.View
import android.widget.ImageView
import android.widget.TextView
import com.bumptech.glide.Glide
import com.bumptech.glide.load.resource.bitmap.RoundedCorners
import com.google.gson.Gson
import java.text.SimpleDateFormat
import java.util.Locale


class PlayTrackActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_play_track)

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

        playBackArrowImage.setOnClickListener { this.finish() }
        val trackString = intent.getStringExtra("TRACK")
        val track = Gson().fromJson(trackString, Track::class.java)
        val newArtUrl = track.artworkUrl100?.replaceAfterLast("/", "512x512bb.jpg")
        Glide.with(applicationContext)
            .load(newArtUrl)
            .placeholder(R.drawable.placeholder)
            .error(R.drawable.placeholder)
            .fitCenter()
            .transform(RoundedCorners(dpToPx(8f, trackArtImageView.context)))
            .into(trackArtImageView)

        countryTextView.text = track.country ?: getString(R.string.trackplay_title_country)
        genreTextView.text =
            track.primaryGenreName?: getString(R.string.trackplay_title_genre)
        yearTextView.text =
            track.releaseDate?.take(4) ?: getString(R.string.trackplay_title_year)
        if (!track.collectionName.isNullOrEmpty()) albumTextView.text = track.collectionName
        else {
            albumTextView.visibility = View.GONE
            albumTitleTextView.visibility = View.GONE
        }

        trackMaxTimeTextView.text = SimpleDateFormat("mm:ss", Locale.getDefault()).format(track.trackTimeMillis)
            .ifEmpty { getString(R.string.track_time_placeholder) }

        artistTextView.text = track.artistName.ifEmpty {getString(R.string.track_artist_placeholder)  }
        trackNameTimeTextView.text = track.trackName.ifEmpty {getString(R.string.track_name_placeholder)  }
    }

    private fun dpToPx(dp: Float, context: Context): Int {
        return TypedValue.applyDimension(
            TypedValue.COMPLEX_UNIT_DIP,
            dp,
            context.resources.displayMetrics
        ).toInt()
    }
}