package com.example.playlistmaker.settings.ui


import android.os.Bundle
import android.view.View
import android.widget.Button
import android.widget.ImageView

import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.SwitchCompat
import androidx.lifecycle.ViewModelProvider
import com.example.playlistmaker.App
import com.example.playlistmaker.R

class SettingActivity : AppCompatActivity() {

    private lateinit var viewModel: SettingViewModel
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_setting)

        val backArrowImage = findViewById<ImageView>(R.id.SettingsBackArrowImage)
        val switchDarkMode = findViewById<SwitchCompat>(R.id.SettingSwitchDarkTheme)
        val shareButton = findViewById<Button>(R.id.ShareButton)
        val supportButton = findViewById<Button>(R.id.SupportButton)
        val userCondButton = findViewById<Button>(R.id.UserCondButton)

        viewModel = ViewModelProvider(
            this,
            SettingViewModel.getViewModelFactory()
        )[SettingViewModel::class.java]

        backArrowImage.visibility = View.VISIBLE
        backArrowImage.setOnClickListener {
            this.finish()
        }

        var currentMode = viewModel.getThemeLD()
        switchDarkMode.isChecked = currentMode
        switchDarkMode.setOnCheckedChangeListener { _, checked ->
            viewModel.changeThemeLD(checked)
        }

        viewModel.getThemeLiveData().observe(this) { newMode ->
            if (newMode != currentMode) {
                (applicationContext as App).switchTheme(newMode)
                currentMode = newMode
            }

        }

        shareButton.setOnClickListener {
            viewModel.shareApp()
        }

        supportButton.setOnClickListener {
            viewModel.writeSupport()
        }
        userCondButton.setOnClickListener {
            viewModel.openTerms()
        }


    }
}

