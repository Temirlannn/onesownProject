package com.example.ui.playlist

import androidx.lifecycle.LiveData
import androidx.lifecycle.liveData
import com.example.core.network.result.Resource
import com.example.model.PlayList
import com.example.data.remote.RemoteDataSource
import kotlinx.coroutines.Dispatchers

class PlaylistRepository(private val dataSource: RemoteDataSource) {

    fun fetchSpecPlaylist(id: String): LiveData<Resource<PlayList>> = liveData(Dispatchers.IO) {
        emit(Resource.loading(null))
        val result = dataSource.fetchSpecPlaylist(id)
        emit(result)
    }

    fun fetchNextSpecPlaylist(id: String, nextPageToken: String): LiveData<Resource<PlayList>> =
        liveData(Dispatchers.IO) {
            emit(Resource.loading(null))
            val result = dataSource.fetchNextSpecPlaylist(id, nextPageToken)
            emit(result)
        }
}