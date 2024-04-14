package com.example.playlistmaker

import android.content.Context
import android.content.res.Configuration
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.view.View
import android.view.View.GONE
import android.view.View.VISIBLE
import android.view.inputmethod.EditorInfo
import android.view.inputmethod.InputMethodManager
import android.widget.EditText
import android.widget.ImageView
import android.widget.TextView
import androidx.appcompat.content.res.AppCompatResources
import androidx.appcompat.widget.AppCompatButton
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.playlistmaker.netconnection.ItunesApi
import com.example.playlistmaker.netconnection.TracksResponse
import com.example.playlistmaker.searchrecycler.SearchHistoryTrackAdapter
import com.example.playlistmaker.searchrecycler.SearchTrackAdapter
import com.example.playlistmaker.searchrecycler.SearchTrackHistoryHelper
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class SearchActivity : AppCompatActivity() {

    private val tracks = mutableListOf(Track("", "", 0, "", 0))
    private val iTunesBaseUrl = "https://itunes.apple.com"

    private val retrofit = Retrofit.Builder()
        .baseUrl(iTunesBaseUrl)
        .addConverterFactory(GsonConverterFactory.create())
        .build()

    private val itunesService = retrofit.create(ItunesApi::class.java)

    private lateinit var searchWrongImage: ImageView
    private lateinit var searchWrongText: TextView
    private lateinit var searchNowifiRefreshButton: AppCompatButton
    private lateinit var searchRecyclerView: RecyclerView
    private lateinit var searchEditText: EditText

    private lateinit var searchHistoryText: TextView
    private lateinit var searchHistoryRecyclerView: RecyclerView
    private lateinit var searchHistoryClearButton: AppCompatButton

    private lateinit var recyclerTrackAdapter: SearchTrackAdapter
    private lateinit var historyRecyclerAdapter: SearchHistoryTrackAdapter

    //    val sharedPreferences = getSharedPreferences(PLAYLIST_SHARED_PREFS, MODE_PRIVATE)
    val searchTrackHistoryHelper = SearchTrackHistoryHelper()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_search)

        if (savedInstanceState != null) {
            textValue = savedInstanceState.getString(CURRENT_TEXT, EMPTY_TXT)
        }

        val searchBackArrowImage = findViewById<ImageView>(R.id.SearchBackArrowImage)
        searchEditText = findViewById(R.id.SearchEditText)
        val searchClearTextImage = findViewById<ImageView>(R.id.SearchClearTextImage)
        searchRecyclerView = findViewById(R.id.SearchRecyclerView)
        searchWrongImage = findViewById(R.id.SearchSmthWrongImage)
        searchWrongText = findViewById(R.id.SearchSmthWrongText)
        searchNowifiRefreshButton = findViewById(R.id.SearchNowifiRefreshButton)

        searchHistoryRecyclerView = findViewById(R.id.SearchHistoryRecyclerView)
        searchHistoryText = findViewById(R.id.SearchHistoryTextView)
        searchHistoryClearButton = findViewById(R.id.SearchHistoryClear)


        searchEditText.requestFocus()
        searchEditText.setText(textValue)
        tracks.clear()

        searchBackArrowImage.setOnClickListener {
            this.finish()
        }

        searchClearTextImage.setImageDrawable(
            AppCompatResources.getDrawable(
                this,
                R.drawable.clearsearchbutton
            )
        )

        searchRecyclerView.layoutManager =
            LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false)
        recyclerTrackAdapter = SearchTrackAdapter(tracks)
        searchRecyclerView.adapter = recyclerTrackAdapter

        searchHistoryRecyclerView.layoutManager =
            LinearLayoutManager(this, LinearLayoutManager.VERTICAL, true)
        historyRecyclerAdapter =
            SearchHistoryTrackAdapter(searchTrackHistoryHelper.getHistory(this))
        searchHistoryRecyclerView.adapter = historyRecyclerAdapter
        if (searchTrackHistoryHelper.getHistory(this).isEmpty()) showResult() else showHistory()

        val inputMethodManager =
            //keyboard on start
            getSystemService(Context.INPUT_METHOD_SERVICE) as? InputMethodManager
        inputMethodManager?.showSoftInput(searchEditText, 0)
        val searchTextWatcher = object : TextWatcher {
            override fun beforeTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) { //nothing
            }

            override fun onTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {
                if (p0.isNullOrEmpty()) {
                    searchClearTextImage.visibility = View.INVISIBLE
                } else searchClearTextImage.visibility = VISIBLE
                textValue = p0.toString()
                if (savedInstanceState != null) {
                    onSaveInstanceState(savedInstanceState)
                }
            }

            override fun afterTextChanged(p0: Editable?) {//nothing
            }
        }
        searchEditText.addTextChangedListener(searchTextWatcher)

        searchClearTextImage.setOnClickListener {
            searchEditText.setText("")
            tracks.clear()
            recyclerTrackAdapter.notifyDataSetChanged()
            val inputMethodManager =
                //keyboard gone
                getSystemService(Context.INPUT_METHOD_SERVICE) as? InputMethodManager
            inputMethodManager?.hideSoftInputFromWindow(searchEditText.windowToken, 0)
        }

        searchHistoryClearButton.setOnClickListener {
            searchTrackHistoryHelper.clearHistory(this)
            historyRecyclerAdapter.notifyDataSetChanged()
            showResult()
        }


        searchEditText.setOnEditorActionListener { _, i, _ ->
            if (i == EditorInfo.IME_ACTION_DONE) {
                if (searchEditText.text.isNotEmpty()) {
                    lookForTrack(searchEditText.text.toString())
                }
                true
            }
            false
        }

        searchNowifiRefreshButton.setOnClickListener {
            lookForTrack(failedQuery)
        }

    }

    var failedQuery = ""
    private fun lookForTrack(text: String) {
        itunesService.findTrack(text).enqueue(object :
            Callback<TracksResponse> {

            override fun onResponse(
                call: Call<TracksResponse>,
                response: Response<TracksResponse>
            ) {
                if (response.code() == 200) {
                    tracks.clear()
                    if (response.body()?.results?.isNotEmpty() == true) {
                        tracks.addAll(response.body()?.results!!)
                        recyclerTrackAdapter.notifyDataSetChanged()
                        showResult()
                    }
                    if (tracks.isEmpty()) {
                        showNoResults()

                    }
                } else {
                    showNoWifi()
                    failedQuery = searchEditText.text.toString()
                }
            }

            override fun onFailure(call: Call<TracksResponse>, t: Throwable) {
                showNoWifi()
                failedQuery = searchEditText.text.toString()
            }
        })
    }

    private var textValue: String = EMPTY_TXT
    override fun onSaveInstanceState(outState: Bundle) {
        super.onSaveInstanceState(outState)
        outState.putString(CURRENT_TEXT, textValue)
    }

    companion object {
        private const val CURRENT_TEXT = "CURRENT_TEXT"
        private const val EMPTY_TXT = ""
    }


    private fun showResult() {
        searchWrongImage.visibility = GONE
        searchNowifiRefreshButton.visibility = GONE
        searchWrongText.visibility = GONE
        searchRecyclerView.visibility = VISIBLE

        searchHistoryRecyclerView.visibility = GONE
        searchHistoryText.visibility = GONE
        searchHistoryClearButton.visibility = GONE

    }

    private fun showNoWifi() {
        searchRecyclerView.visibility = GONE

        when (resources.configuration.uiMode and Configuration.UI_MODE_NIGHT_MASK) {
            Configuration.UI_MODE_NIGHT_YES -> searchWrongImage.setImageDrawable(getDrawable(R.drawable.track_ph_nowifi_dark))
            Configuration.UI_MODE_NIGHT_NO -> searchWrongImage.setImageDrawable(getDrawable(R.drawable.track_ph_nowifi_light))
        }
        this.searchWrongImage.visibility = VISIBLE

        searchWrongText.text = getString(R.string.search_nowifi_wrong_message)
        searchWrongText.visibility = VISIBLE

        searchNowifiRefreshButton.visibility = VISIBLE

        searchHistoryRecyclerView.visibility = GONE
        searchHistoryText.visibility = GONE
        searchHistoryClearButton.visibility = GONE
    }

    private fun showNoResults() {
        searchRecyclerView.visibility = GONE

        when (resources.configuration.uiMode and Configuration.UI_MODE_NIGHT_MASK) {
            Configuration.UI_MODE_NIGHT_YES -> searchWrongImage.setImageDrawable(getDrawable(R.drawable.track_ph_unknown_dark))
            Configuration.UI_MODE_NIGHT_NO -> searchWrongImage.setImageDrawable(getDrawable(R.drawable.track_ph_unknown_light))
        }
        this.searchWrongImage.visibility = VISIBLE

        searchWrongText.text = getString(R.string.search_noresult_message)
        searchWrongText.visibility = VISIBLE

        searchNowifiRefreshButton.visibility = GONE

        searchHistoryRecyclerView.visibility = GONE
        searchHistoryText.visibility = GONE
        searchHistoryClearButton.visibility = GONE
    }

    private fun showHistory() {
        searchRecyclerView.visibility = GONE
        searchWrongText.visibility = GONE
        searchNowifiRefreshButton.visibility = GONE
        searchWrongImage.visibility = GONE

        searchHistoryRecyclerView.visibility = VISIBLE
        searchHistoryText.visibility = VISIBLE
        searchHistoryClearButton.visibility = VISIBLE

    }

}