package com.example.ui.playlists_list

import androidx.lifecycle.LiveData
import androidx.lifecycle.liveData
import com.example.core.network.result.Resource
import com.example.model.PlayList
import com.example.data.remote.RemoteDataSource
import kotlinx.coroutines.Dispatchers

class ListPlaylistsRepository(private val dataSource: RemoteDataSource) {

    fun fetchYoutubeApiPlaylists(): LiveData<Resource<PlayList>> =
        liveData(Dispatchers.IO) {
            emit(Resource.loading(null))
            emit(dataSource.fetchAllPlaylist())
        }

    fun fetchNextPagePlaylists(nextPageToken: String): LiveData<Resource<PlayList>> =
        liveData(Dispatchers.IO) {
            emit(Resource.loading(null))
            emit(dataSource.fetchNextPlaylists(nextPageToken))
        }
}