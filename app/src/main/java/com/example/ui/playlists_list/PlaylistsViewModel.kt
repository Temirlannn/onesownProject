package com.example.ui.playlists_list

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.example.core.base.BaseViewModel
import com.example.core.network.result.Resource
import com.example.model.PlayList

class PlaylistsViewModel(private val repository: ListPlaylistsRepository) : BaseViewModel() {

    var loading = MutableLiveData<Boolean>()
    private var _nextPage = MutableLiveData<Resource<PlayList>>()
    private var _page = MutableLiveData<Resource<PlayList>>()

    fun fetchYoutubeApiPlaylists() {
        _page = repository.fetchYoutubeApiPlaylists() as MutableLiveData<Resource<PlayList>>
    }

    fun fetchNextPage(nextPageToken: String) {
        _nextPage = repository.fetchNextPagePlaylists(nextPageToken) as MutableLiveData<Resource<PlayList>>
    }

    val nextpage: LiveData<Resource<PlayList>>
        get() = _nextPage

    val page: LiveData<Resource<PlayList>>
        get() = _page
}