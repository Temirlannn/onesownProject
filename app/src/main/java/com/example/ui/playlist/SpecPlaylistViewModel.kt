package com.example.ui.playlist

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.example.core.base.BaseViewModel
import com.example.core.network.result.Resource
import com.example.model.PlayList

class SpecPlaylistViewModel(private val repository: PlaylistRepository) : BaseViewModel() {


    var loading = MutableLiveData<Boolean>()
    private var _page = MutableLiveData<Resource<PlayList>>()
    private var _nextPage = MutableLiveData<Resource<PlayList>>()

    fun fetchSpecPlaylist(id: String) {
        _page = repository.fetchSpecPlaylist(id) as MutableLiveData<Resource<PlayList>>
    }

    fun fetchNextSpecPlaylist(id: String, nextPageToken: String) {
        _nextPage = repository.fetchNextSpecPlaylist(
            id,
            nextPageToken
        ) as MutableLiveData<Resource<PlayList>>
    }

    val page: LiveData<Resource<PlayList>>
        get() = _page

    val nexPage: LiveData<Resource<PlayList>>
        get() = _nextPage

}