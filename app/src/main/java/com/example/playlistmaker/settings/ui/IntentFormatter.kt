package com.example.playlistmaker.settings.ui

import android.content.Intent
import com.example.playlistmaker.settings.domain.models.IntentModel

object IntentFormatter {
    fun format(intentModel: IntentModel):Intent{
        return intentModel.intent
    }
}