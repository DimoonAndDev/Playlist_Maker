package com.example.playlistmaker.search.ui

import android.content.Context.INPUT_METHOD_SERVICE
import android.content.res.Configuration
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.inputmethod.InputMethodManager
import androidx.appcompat.content.res.AppCompatResources
import androidx.constraintlayout.widget.ConstraintLayout

import androidx.core.view.updateLayoutParams
import androidx.fragment.app.Fragment
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController

import androidx.recyclerview.widget.LinearLayoutManager
import com.example.playlistmaker.R

import com.example.playlistmaker.databinding.FragmentSearchBinding
import com.example.playlistmaker.media.player.ui.PlayerTrackFragment
import com.example.playlistmaker.search.domain.models.Track
import com.example.playlistmaker.search.ui.models.SearchScreenState
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import org.koin.androidx.viewmodel.ext.android.viewModel

class SearchFragment : Fragment() {
    lateinit var binding: FragmentSearchBinding
    private val tracks = mutableListOf(Track())
    private val viewModel by viewModel<SearchFragmentViewModel>()
    var textValue: String = EMPTY_TXT
    private lateinit var recyclerTrackAdapter: SearchTrackAdapter


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?
    ): View {
        // Inflate the layout for this fragment
        binding = FragmentSearchBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.SearchEditText.requestFocus()
        binding.SearchEditText.setText(textValue)
        tracks.clear()
        if (savedInstanceState != null) {
            textValue = savedInstanceState.getString(
                CURRENT_TEXT, EMPTY_TXT
            )
        }

        binding.SearchClearTextImage.setImageDrawable(
            AppCompatResources.getDrawable(
                requireContext(), R.drawable.clearsearchbutton
            )
        )

        binding.SearchRecyclerView.layoutManager =
            LinearLayoutManager(requireContext(), LinearLayoutManager.VERTICAL, false)
        recyclerTrackAdapter = SearchTrackAdapter(tracks)
        binding.SearchRecyclerView.adapter = recyclerTrackAdapter

        recyclerTrackAdapter.setOnClickListener(object : SearchTrackAdapter.OnClickListener {
            override fun onClick(position: Int, track: Track) {
                if (clickDebounce()) {
                    viewModel.saveTrackInHistory(track)
                    val savedTrack = viewModel.getGsonString(track)
                    findNavController().navigate(
                        R.id.action_searchFragment_to_playerTrackFragment,
                        PlayerTrackFragment.createArgs(savedTrack, SEARCH_ID)
                    )
                }
            }

        })
        viewModel.getSearchActivityStateLiveData().observe(viewLifecycleOwner) {
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
            requireContext().getSystemService(INPUT_METHOD_SERVICE) as? InputMethodManager
        inputMethodManager?.showSoftInput(binding.SearchEditText, 0)

        val searchTextWatcher = object : TextWatcher {
            override fun beforeTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) { //nothing
            }

            override fun onTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {
                if (p0.toString() == "") {
                    binding.SearchClearTextImage.visibility = View.INVISIBLE
                    showHistory(viewModel.getHistory())
                } else {
                    binding.SearchClearTextImage.visibility = View.VISIBLE
                    if (p0.toString() != textValue) {
                        textValue = p0.toString()
                        viewModel.searchDebounce(textValue)
                    }
                }
                if (savedInstanceState != null) {
                    onSaveInstanceState(savedInstanceState)
                }
            }

            override fun afterTextChanged(p0: Editable?) {//nothing
            }
        }
        binding.SearchEditText.addTextChangedListener(searchTextWatcher)

        binding.SearchClearTextImage.setOnClickListener {
            binding.SearchEditText.setText("")
            showHistory(viewModel.getHistory())
            val inputMethodManager =
                //keyboard gone
                requireContext().getSystemService(INPUT_METHOD_SERVICE) as? InputMethodManager
            inputMethodManager?.hideSoftInputFromWindow(binding.SearchEditText.windowToken, 0)
        }
        binding.SearchHistoryClear.setOnClickListener {
            viewModel.clearHistory()
            showHistory(viewModel.getHistory())
        }
    }


    override fun onSaveInstanceState(outState: Bundle) {
        super.onSaveInstanceState(outState)
        outState.putString(CURRENT_TEXT, textValue)
    }

    private var isClickAllowed = true
    private fun clickDebounce(): Boolean {
        val current = isClickAllowed
        if (isClickAllowed) {
            isClickAllowed = false
            viewLifecycleOwner.lifecycleScope.launch {
                delay(CLICK_DEBOUNCE_DELAY)
                isClickAllowed = true
            }
        }
        return current
    }

    override fun onStop() {
        super.onStop()
        isClickAllowed = true
    }
    companion object {
        private const val CURRENT_TEXT = "CURRENT_TEXT"
        private const val EMPTY_TXT = ""
        private const val SEARCH_ID = 0

        const val CLICK_DEBOUNCE_DELAY = 1000L


    }

    private fun showResult(foundTracks: List<Track>) {
        tracks.clear()
        tracks.addAll(foundTracks)
        recyclerTrackAdapter.notifyDataSetChanged()
        binding.SearchRecyclerView.visibility = View.VISIBLE

        binding.SearchSmthWrongImage.visibility = View.GONE
        binding.SearchNowifiRefreshButton.visibility = View.GONE
        binding.SearchSmthWrongText.visibility = View.GONE
        binding.SearchProgressBar.visibility = View.GONE

        binding.SearchHistoryTextView.visibility = View.GONE
        binding.SearchHistoryClear.visibility = View.GONE
        binding.RecyclerBotGuideline.visibility = View.GONE
        binding.SearchRecyclerView.updateLayoutParams<ConstraintLayout.LayoutParams> {
            bottomToTop = ConstraintLayout.LayoutParams.UNSET
        }


    }

    private fun showNoWifi(lastRequest: String?) {
        binding.SearchRecyclerView.visibility = View.GONE
        binding.SearchProgressBar.visibility = View.GONE

        when (resources.configuration.uiMode and Configuration.UI_MODE_NIGHT_MASK) {
            Configuration.UI_MODE_NIGHT_YES -> binding.SearchSmthWrongImage.setImageResource(R.drawable.track_ph_nowifi_dark)
            Configuration.UI_MODE_NIGHT_NO -> binding.SearchSmthWrongImage.setImageResource(R.drawable.track_ph_nowifi_light)
        }
        binding.SearchSmthWrongImage.visibility = View.VISIBLE

        binding.SearchSmthWrongText.text = getString(R.string.search_nowifi_wrong_message)
        binding.SearchSmthWrongText.visibility = View.VISIBLE

        binding.SearchNowifiRefreshButton.visibility = View.VISIBLE

        binding.SearchHistoryTextView.visibility = View.GONE
        binding.SearchHistoryClear.visibility = View.GONE
        binding.SearchNowifiRefreshButton.setOnClickListener {
            if (clickDebounce()) {
                binding.SearchEditText.setText(lastRequest)
                viewModel.searchDebounce(lastRequest ?: "test")
            }
        }
    }

    private fun showNoResults() {
        binding.SearchRecyclerView.visibility = View.GONE
        binding.SearchProgressBar.visibility = View.GONE

        when (resources.configuration.uiMode and Configuration.UI_MODE_NIGHT_MASK) {
            Configuration.UI_MODE_NIGHT_YES -> binding.SearchSmthWrongImage.setImageResource(R.drawable.track_ph_unknown_dark)
            Configuration.UI_MODE_NIGHT_NO -> binding.SearchSmthWrongImage.setImageResource(R.drawable.track_ph_unknown_light)
        }
        binding.SearchSmthWrongImage.visibility = View.VISIBLE

        binding.SearchSmthWrongText.text = getString(R.string.search_noresult_message)
        binding.SearchSmthWrongText.visibility = View.VISIBLE

        binding.SearchNowifiRefreshButton.visibility = View.GONE

        binding.SearchHistoryTextView.visibility = View.GONE
        binding.SearchHistoryClear.visibility = View.GONE
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
            binding.SearchSmthWrongText.visibility = View.GONE
            binding.SearchNowifiRefreshButton.visibility = View.GONE
            binding.SearchSmthWrongImage.visibility = View.GONE
            binding.SearchProgressBar.visibility = View.GONE

            binding.SearchHistoryTextView.visibility = View.VISIBLE
            binding.SearchHistoryClear.visibility = View.VISIBLE
            binding.SearchRecyclerView.visibility = View.VISIBLE
            binding.RecyclerBotGuideline.visibility = View.INVISIBLE
            binding.SearchRecyclerView.updateLayoutParams<ConstraintLayout.LayoutParams> {
                bottomToTop = binding.RecyclerBotGuideline.id
            }

        }
    }

    private fun showProgressBar() {
        binding.SearchProgressBar.visibility = View.VISIBLE

        binding.SearchRecyclerView.visibility = View.GONE

        binding.SearchSmthWrongImage.visibility = View.GONE
        binding.SearchNowifiRefreshButton.visibility = View.GONE
        binding.SearchSmthWrongText.visibility = View.GONE

        binding.SearchHistoryTextView.visibility = View.GONE
        binding.SearchHistoryClear.visibility = View.GONE
    }
}