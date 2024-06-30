package com.example.playlistmaker.settings.domain.repository

import android.content.Intent

interface OutOptionsRepository {
    fun openTerms():Intent
    fun shareApp():Intent
    fun writeSupport():Intent
}