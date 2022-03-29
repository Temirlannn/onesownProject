package com.example.data.remote

import com.example.`object`.Constants
import com.example.core.network.BaseDataSource
import org.koin.dsl.module

val remoteDataSourceModule = module {
    factory { RemoteDataSource(get()) }
}
class RemoteDataSource(private val apiService: YoutubeAPI) : BaseDataSource() {

    suspend fun fetchAllPlaylist() = getResult {
        apiService.fetchAllPlaylists( Constants.PART,Constants.CHANNEL_ID, Constants.MAXRESULT,Constants.API_KEY)
    }
    suspend fun fetchNextPlaylists(nextPageToken: String) = getResult {
        apiService.fetchNextPlaylists(Constants.PART, Constants.CHANNEL_ID, nextPageToken, Constants.API_KEY)
    }

    suspend fun fetchSpecPlaylist(playlistId: String) = getResult {
        apiService.fetchSpecPlaylist(Constants.PART, Constants.MAXRESULT, playlistId, Constants.API_KEY)
    }

    suspend fun fetchNextSpecPlaylist(playlistId: String, nextPageToken: String) = getResult {
        apiService.fetchNextSpecPlaylist(Constants.PART, nextPageToken, Constants.MAXRESULT, playlistId, Constants.API_KEY)
    }
}