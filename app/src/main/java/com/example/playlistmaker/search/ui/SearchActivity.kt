package com.example.playlistmaker.search.ui

import android.content.Context
import android.content.res.Configuration
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.text.Editable
import android.text.TextWatcher
import android.view.View
import android.view.View.GONE
import android.view.View.INVISIBLE
import android.view.View.VISIBLE
import android.view.inputmethod.InputMethodManager
import android.widget.EditText
import android.widget.ImageView
import android.widget.ProgressBar
import android.widget.TextView
import androidx.appcompat.content.res.AppCompatResources
import androidx.appcompat.widget.AppCompatButton
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.constraintlayout.widget.Guideline
import androidx.core.view.updateLayoutParams
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.playlistmaker.R
import com.example.playlistmaker.search.domain.models.Track
import com.example.playlistmaker.search.ui.models.SearchScreenState
import org.koin.androidx.viewmodel.ext.android.viewModel


class SearchActivity : AppCompatActivity() {

    private val tracks = mutableListOf(Track())

    private lateinit var searchWrongImage: ImageView
    private lateinit var searchWrongText: TextView
    private lateinit var searchNowifiRefreshButton: AppCompatButton
    private lateinit var searchRecyclerView: RecyclerView
    private lateinit var searchEditText: EditText

    private lateinit var searchHistoryText: TextView
    private lateinit var searchHistoryClearButton: AppCompatButton

    private lateinit var searchProgressBar: ProgressBar

    private lateinit var recyclerTrackAdapter: SearchTrackAdapter
    private lateinit var recyclerBotGuideline: Guideline

    private val viewModel by viewModel<SearchActivityViewModel>()

    private val handler = Handler(Looper.getMainLooper())

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_search)

        val searchBackArrowImage = findViewById<ImageView>(R.id.PlayTrackBackArrowImage)
        searchEditText = findViewById(R.id.SearchEditText)
        val searchClearTextImage = findViewById<ImageView>(R.id.SearchClearTextImage)
        searchRecyclerView = findViewById(R.id.SearchRecyclerView)
        searchWrongImage = findViewById(R.id.SearchSmthWrongImage)
        searchWrongText = findViewById(R.id.SearchSmthWrongText)
        searchNowifiRefreshButton = findViewById(R.id.SearchNowifiRefreshButton)

        searchHistoryText = findViewById(R.id.SearchHistoryTextView)
        searchHistoryClearButton = findViewById(R.id.SearchHistoryClear)

        searchProgressBar = findViewById(R.id.SearchProgressBar)
        recyclerBotGuideline = findViewById(R.id.RecyclerBotGuideline)

        if (savedInstanceState != null) {
            textValue = savedInstanceState.getString(CURRENT_TEXT, EMPTY_TXT)
        }
        searchEditText.requestFocus()
        searchEditText.setText(textValue)
        tracks.clear()

        searchBackArrowImage.setOnClickListener {
            if (clickDebounce()) {
                this.finish()
            }
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
        viewModel.getSearchActivityStateLiveData().observe(this) {
            when (it) {
                is SearchScreenState.FoundContent -> showResult(it.foundTracks)
                is SearchScreenState.History -> showHistory(it.historyList)
                SearchScreenState.Loading -> showProgressBar()
                is SearchScreenState.NoConnection -> showNoWifi(it.lastRequest)
                SearchScreenState.NoResult -> showNoResults()
            }
        }

        val inputMethodManager =
            //keyboard on start
            getSystemService(Context.INPUT_METHOD_SERVICE) as? InputMethodManager
        inputMethodManager?.showSoftInput(searchEditText, 0)
        val searchTextWatcher = object : TextWatcher {
            override fun beforeTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) { //nothing
            }

            override fun onTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {
                if (p0.toString() == "") {
                    searchClearTextImage.visibility = INVISIBLE
                    showHistory(viewModel.getHistory())
                } else {
                    searchClearTextImage.visibility = VISIBLE
                    textValue = p0.toString()
                    searchDebounce()
                }
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
            showHistory(viewModel.getHistory())
            val inputMethodManager =
                //keyboard gone
                getSystemService(Context.INPUT_METHOD_SERVICE) as? InputMethodManager
            inputMethodManager?.hideSoftInputFromWindow(searchEditText.windowToken, 0)
        }

        searchHistoryClearButton.setOnClickListener {
            viewModel.clearHistory()
            showHistory(viewModel.getHistory())
        }


    }


    private fun lookForTrack() {
        if (searchEditText.text.isNotEmpty())
        viewModel.findTrack(searchEditText.text.toString())
    }
    private val newTaskRunnable = Runnable { lookForTrack() }

    private fun searchDebounce() {
        handler.removeCallbacks(newTaskRunnable)
        handler.postDelayed(newTaskRunnable, SEARCH_DEBOUNCE_DELAY)
    }

    private var textValue: String = EMPTY_TXT
    override fun onSaveInstanceState(outState: Bundle) {
        super.onSaveInstanceState(outState)
        outState.putString(CURRENT_TEXT, textValue)
    }

    companion object {
        private const val CURRENT_TEXT = "CURRENT_TEXT"
        private const val EMPTY_TXT = ""
        private const val SEARCH_DEBOUNCE_DELAY = 2000L
        const val CLICK_DEBOUNCE_DELAY = 1000L
    }

    private var isClickAllowed = true
    private fun clickDebounce(): Boolean {
        val current = isClickAllowed
        if (isClickAllowed) {
            isClickAllowed = false
            handler.postDelayed({ isClickAllowed = true }, CLICK_DEBOUNCE_DELAY)
        }
        return current
    }

    private fun showResult(foundTracks: List<Track>) {
        tracks.clear()
        tracks.addAll(foundTracks)
        recyclerTrackAdapter.notifyDataSetChanged()
        searchRecyclerView.visibility = VISIBLE

        searchWrongImage.visibility = GONE
        searchNowifiRefreshButton.visibility = GONE
        searchWrongText.visibility = GONE
        searchProgressBar.visibility = GONE

        searchHistoryText.visibility = GONE
        searchHistoryClearButton.visibility = GONE
        recyclerBotGuideline.visibility = GONE
        searchRecyclerView.updateLayoutParams<ConstraintLayout.LayoutParams> {
            bottomToTop = ConstraintLayout.LayoutParams.UNSET
        }


    }

    private fun showNoWifi(lastRequest:String?) {
        searchRecyclerView.visibility = GONE
        searchProgressBar.visibility = GONE

        when (resources.configuration.uiMode and Configuration.UI_MODE_NIGHT_MASK) {
            Configuration.UI_MODE_NIGHT_YES -> searchWrongImage.setImageDrawable(getDrawable(R.drawable.track_ph_nowifi_dark))
            Configuration.UI_MODE_NIGHT_NO -> searchWrongImage.setImageDrawable(getDrawable(R.drawable.track_ph_nowifi_light))
        }
        this.searchWrongImage.visibility = VISIBLE

        searchWrongText.text = getString(R.string.search_nowifi_wrong_message)
        searchWrongText.visibility = VISIBLE

        searchNowifiRefreshButton.visibility = VISIBLE

        searchHistoryText.visibility = GONE
        searchHistoryClearButton.visibility = GONE
        searchNowifiRefreshButton.setOnClickListener {
            if (clickDebounce()) {
                searchEditText.setText(lastRequest)
                viewModel.findTrack(lastRequest?:"test")
            }
        }
    }

    private fun showNoResults() {
        searchRecyclerView.visibility = GONE
        searchProgressBar.visibility = GONE

        when (resources.configuration.uiMode and Configuration.UI_MODE_NIGHT_MASK) {
            Configuration.UI_MODE_NIGHT_YES -> searchWrongImage.setImageDrawable(getDrawable(R.drawable.track_ph_unknown_dark))
            Configuration.UI_MODE_NIGHT_NO -> searchWrongImage.setImageDrawable(getDrawable(R.drawable.track_ph_unknown_light))
        }
        this.searchWrongImage.visibility = VISIBLE

        searchWrongText.text = getString(R.string.search_noresult_message)
        searchWrongText.visibility = VISIBLE

        searchNowifiRefreshButton.visibility = GONE

        searchHistoryText.visibility = GONE
        searchHistoryClearButton.visibility = GONE
    }

    private fun showHistory(historyList: List<Track>) {
        if (historyList.isEmpty()) {
            tracks.clear()
            recyclerTrackAdapter.notifyDataSetChanged()
            showResult(historyList)
        } else {
            tracks.clear()
            tracks.addAll(historyList)
            recyclerTrackAdapter.notifyDataSetChanged()
            searchWrongText.visibility = GONE
            searchNowifiRefreshButton.visibility = GONE
            searchWrongImage.visibility = GONE
            searchProgressBar.visibility = GONE

            searchHistoryText.visibility = VISIBLE
            searchHistoryClearButton.visibility = VISIBLE
            searchRecyclerView.visibility = VISIBLE
            recyclerBotGuideline.visibility = INVISIBLE
            searchRecyclerView.updateLayoutParams<ConstraintLayout.LayoutParams> {
                bottomToTop = recyclerBotGuideline.id
            }

        }
    }

    private fun showProgressBar() {
        searchProgressBar.visibility = VISIBLE

        searchRecyclerView.visibility = GONE

        searchWrongImage.visibility = GONE
        searchNowifiRefreshButton.visibility = GONE
        searchWrongText.visibility = GONE

        searchHistoryText.visibility = GONE
        searchHistoryClearButton.visibility = GONE
    }

}