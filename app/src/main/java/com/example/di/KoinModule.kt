package com.example.di

import com.example.core.network.networkModule
import com.example.data.remote.remoteDataSourceModule

val koinModules= listOf(
    networkModule,
    remoteDataSourceModule,
    viewModules,
    repoModules,
)