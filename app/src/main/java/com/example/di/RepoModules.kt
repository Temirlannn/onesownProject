package com.example.di

import com.example.ui.playlists_list.ListPlaylistsRepository
import com.example.ui.playlist.PlaylistRepository
import org.koin.core.module.Module
import org.koin.dsl.module

val repoModules: Module = module {
    single { ListPlaylistsRepository(get()) }
    single { PlaylistRepository(get()) }
}