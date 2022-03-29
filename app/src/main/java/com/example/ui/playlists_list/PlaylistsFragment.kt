package com.example.ui.playlists_list

import android.view.View
import androidx.core.widget.NestedScrollView
import androidx.navigation.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.core.base.BaseFragment
import com.example.core.network.result.Status
import com.example.extensions.showMessage
import com.example.extensions.visible
import com.example.model.Items
import com.example.ui.playlists_list.adapter.PlaylistsAdapter
import com.example.youtube.R
import kotlinx.android.synthetic.main.playlists_fragment.*
import org.koin.android.ext.android.inject

open class PlaylistsFragment :
    BaseFragment(R.layout.playlists_fragment) {


    private var nextPageToken: String? = null

    override fun setupObservers() {
        initScrollView()
    }

    private val viewModel: PlaylistsViewModel by inject()

    private var playlistAdapter: PlaylistsAdapter? = null

    private fun initData() {
        viewModel.loading.observe(this, { progress_barr.visible = it })

        viewModel.fetchYoutubeApiPlaylists()
        viewModel.page.observe(this, { resource ->
            when (resource.status) {

                Status.LOADING -> {
                    viewModel.loading.postValue(true)
                }
                Status.SUCCESS -> {
                    viewModel.loading.postValue(false)
                    playlistAdapter = resource.data?.let {
                        PlaylistsAdapter(it) { m, v ->
                            onClickPlaylist(
                                m,
                                v
                            )
                        }
                    }
                    initRecycler()
                    this.nextPageToken = resource.data?.nextPageToken
                }
                Status.ERROR -> {
                    viewModel.loading.postValue(true)
                    context?.showMessage(resource.message)
                }
            }
        })

    }

    private fun initRecycler() {
        recycler_vieww.layoutManager = LinearLayoutManager(context)
        recycler_vieww.adapter = playlistAdapter
    }

    private fun initScrollView() {
        scroll_view.setOnScrollChangeListener(NestedScrollView.OnScrollChangeListener { v, _, scrollY, _, _ ->
            if (nextPageToken != null && scrollY == v.getChildAt(0).measuredHeight - v.measuredHeight) {
                getNextPage()
            }
        })
    }

    private fun getNextPage() {
        nextPageToken?.let { viewModel.fetchNextPage(it) }
        viewModel.nextpage.observe(this, {
            when (it.status) {
                Status.LOADING -> {
                    viewModel.loading.postValue(true)
                }
                Status.SUCCESS -> {
                    viewModel.loading.postValue(false)
                    playlistAdapter?.submitList(it.data?.items as ArrayList<Items>)
                    this.nextPageToken = it.data?.nextPageToken
                }
                Status.ERROR -> {
                    viewModel.loading.postValue(true)
                    context?.showMessage(it.message)
                }
            }
        })
    }

    override fun showConnectedState() {
        initData()
    }

    private fun onClickPlaylist(id: Items, v: View) {
        val action = PlaylistsFragmentDirections.actionPlaylistsFragmentToSpecPlaylistFragment(id)
        v.findNavController().navigate(action)
    }
}