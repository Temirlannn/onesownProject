package com.example.di

import com.example.ui.main.MainViewModel
import com.example.ui.playlist.SpecPlaylistViewModel
import com.example.ui.playlists_list.PlaylistsViewModel
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.core.module.Module
import org.koin.dsl.module

val viewModules: Module = module {
    viewModel { MainViewModel() }
    viewModel { SpecPlaylistViewModel(get()) }
    viewModel { PlaylistsViewModel(get()) }
}