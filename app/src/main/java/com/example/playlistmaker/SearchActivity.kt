package com.example.playlistmaker

import android.content.Context
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.view.View
import android.view.inputmethod.InputMethodManager
import android.widget.EditText
import android.widget.ImageView
import androidx.appcompat.content.res.AppCompatResources
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.playlistmaker.searchrecycler.SearchTrackAdapter

class SearchActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_search)

        if (savedInstanceState != null) {
            textValue = savedInstanceState.getString(CURRENT_TEXT, EMPTY_TXT)
        }

        val searchBackArrowImage = findViewById<ImageView>(R.id.SearchBackArrowImage)
        val searchEditText = findViewById<EditText>(R.id.SearchEditText)
        val searchClearTextImage = findViewById<ImageView>(R.id.SearchClearTextImage)
        val searchRecyclerView = findViewById<RecyclerView>(R.id.SearchRecyclerView)


        searchBackArrowImage.setOnClickListener {
            this.finish()
        }

        searchEditText.requestFocus()
        searchEditText.setText(textValue)
        val inputMethodManager =
            getSystemService(Context.INPUT_METHOD_SERVICE) as? InputMethodManager
        inputMethodManager?.showSoftInput(searchEditText, 0)
        val searchTextWatcher = object : TextWatcher {
            override fun beforeTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) { //nothing
            }

            override fun onTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {
                if (p0.isNullOrEmpty()) {
                    searchClearTextImage.visibility = View.INVISIBLE
                } else searchClearTextImage.visibility = View.VISIBLE
                textValue = p0.toString()
                if (savedInstanceState != null) {
                    onSaveInstanceState(savedInstanceState)
                }
            }

            override fun afterTextChanged(p0: Editable?) {//nothing
            }
        }
        searchEditText.addTextChangedListener(searchTextWatcher)

        searchClearTextImage.setImageDrawable(
            AppCompatResources.getDrawable(
                this,
                R.drawable.clearsearchbutton
            )
        )
        searchClearTextImage.setOnClickListener {
            searchEditText.setText("")
            val inputMethodManager =
                getSystemService(Context.INPUT_METHOD_SERVICE) as? InputMethodManager
            inputMethodManager?.hideSoftInputFromWindow(searchEditText.windowToken, 0)
        }


        searchRecyclerView.layoutManager =
            LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false)
        val recyclerTrackAdapter = SearchTrackAdapter(tracks)
        searchRecyclerView.adapter = recyclerTrackAdapter
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

    private val tracks = arrayOf(
        Track(
            "Smells Like Teen SpiritSpiritSpiritSpiritSpiritSpiritSpiritSpiritSpirit",
            "Nirvana Nirvana Nirvana Nirvana Nirvana Nirvana Nirvana Nirvana Nirvana Nirvana Nirvana Nirvana ",
            "5:01",
            "https://is5-ssl.mzstatic.com/image/thumb/Music115/v4/7b/58/c2/7b58c21a-2b51-2bb2-e59a-9bb9b96ad8c3/00602567924166.rgb.jpg/100x100bb.jpg"
        ),
        Track(
            "Billie Jean",
            "Michael Jackson",
            "4:35",
            "https://is5-ssl.mzstatic.com/image/thumb/Music125/v4/3d/9d/38/3d9d3811-71f0-3a0e-1ada-3004e56ff852/827969428726.jpg/100x100bb.jpg"
        ),
        Track(
            "Stayin' Alive",
            "Bee Gees",
            "4:10",
            "https://is4-ssl.mzstatic.com/image/thumb/Music115/v4/1f/80/1f/1f801fc1-8c0f-ea3e-d3e5-387c6619619e/16UMGIM86640.rgb.jpg/100x100bb.jpg"
        ),
        Track(
            "Whole Lotta Love",
            "Led Zeppelin",
            "5:33",
            "https://is2-ssl.mzstatic.com/image/thumb/Music62/v4/7e/17/e3/7e17e33f-2efa-2a36-e916-7f808576cf6b/mzm.fyigqcbs.jpg/100x100bb.jpg"
        ),
        Track(
            "Sweet Child O'Mine",
            "Guns N' Roses",
            "5:03",
            "https://is5-ssl.mzstatic.com/image/thumb/Music125/v4/a0/4d/c4/a04dc484-03cc-02aa-fa82-5334fcb4bc16/18UMGIM24878.rgb.jpg/100x100bb.jpg"
        )
    )
}