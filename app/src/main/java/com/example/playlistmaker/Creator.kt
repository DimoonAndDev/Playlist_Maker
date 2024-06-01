package com.example.playlistmaker

import com.example.playlistmaker.data.repository.mediaplayer.PauseTrackRepositoryImpl
import com.example.playlistmaker.data.repository.mediaplayer.PlayTrackRepositoryImpl
import com.example.playlistmaker.data.repository.PlayerGetTrackRepositoryImpl
import com.example.playlistmaker.data.repository.mediaplayer.DeactivatePlayerRepositoryImpl
import com.example.playlistmaker.data.repository.mediaplayer.PreparePlayerRepositoryImpl
import com.example.playlistmaker.domain.repository.mediaplayer.PauseTrackRepository
import com.example.playlistmaker.domain.repository.mediaplayer.PlayTrackRepository
import com.example.playlistmaker.domain.repository.PlayerGetTrackRepository
import com.example.playlistmaker.domain.repository.mediaplayer.DeactivatePlayerRepository
import com.example.playlistmaker.domain.repository.mediaplayer.PreparePlayerRepository
import com.example.playlistmaker.domain.usecases.GetPlayerTrackUseCase
import com.example.playlistmaker.domain.usecases.MediaPlayerInteractor

object Creator {
    fun provideGetPlayerTrackUseCase(): GetPlayerTrackUseCase {
        return GetPlayerTrackUseCase(providePlayerGetTrackRepository())
    }

    fun provideMediaPlayerInteractor(): MediaPlayerInteractor {
        return MediaPlayerInteractor(
            providePauseTrackRepository(), providePlayTrackRepository(),
            providePrepareTrackRepository(), provideDeactivateTrackRepository()
        )
    }

    private fun providePlayerGetTrackRepository(): PlayerGetTrackRepository {
        return PlayerGetTrackRepositoryImpl()
    }

    private fun providePauseTrackRepository(): PauseTrackRepository {
        return PauseTrackRepositoryImpl()
    }

    private fun providePlayTrackRepository(): PlayTrackRepository {
        return PlayTrackRepositoryImpl()
    }

    private fun providePrepareTrackRepository(): PreparePlayerRepository {
        return PreparePlayerRepositoryImpl()
    }

    private fun provideDeactivateTrackRepository(): DeactivatePlayerRepository {
        return DeactivatePlayerRepositoryImpl()
    }
}