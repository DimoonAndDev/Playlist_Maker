package com.example.playlistmaker

import com.example.playlistmaker.data.repository.PlayerGetTrackRepositoryImpl
import com.example.playlistmaker.data.repository.mediaplayer.DeactivatePlayerRepositoryImpl
import com.example.playlistmaker.data.repository.mediaplayer.PlayClickRepositoryImpl
import com.example.playlistmaker.data.repository.mediaplayer.PreparePlayerRepositoryImpl
import com.example.playlistmaker.domain.repository.PlayerGetTrackRepository
import com.example.playlistmaker.domain.repository.mediaplayer.DeactivatePlayerRepository
import com.example.playlistmaker.domain.repository.mediaplayer.PlayClickRepository
import com.example.playlistmaker.domain.repository.mediaplayer.PreparePlayerRepository
import com.example.playlistmaker.domain.usecases.GetPlayerTrackUseCase
import com.example.playlistmaker.domain.usecases.MediaPlayerInteractor

object Creator {
    fun provideGetPlayerTrackUseCase(): GetPlayerTrackUseCase {
        return GetPlayerTrackUseCase(providePlayerGetTrackRepository())
    }

    fun provideMediaPlayerInteractor(): MediaPlayerInteractor {
        return MediaPlayerInteractor(
            providePlayClickRepository(),
            providePrepareTrackRepository(), provideDeactivateTrackRepository()
        )
    }

    private fun providePlayerGetTrackRepository(): PlayerGetTrackRepository {
        return PlayerGetTrackRepositoryImpl()
    }

    private fun providePlayClickRepository(): PlayClickRepository {
        return PlayClickRepositoryImpl()
    }

    private fun providePrepareTrackRepository(): PreparePlayerRepository {
        return PreparePlayerRepositoryImpl()
    }

    private fun provideDeactivateTrackRepository(): DeactivatePlayerRepository {
        return DeactivatePlayerRepositoryImpl()
    }
}