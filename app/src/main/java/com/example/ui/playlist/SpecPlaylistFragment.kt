package com.example.ui.playlist

import android.view.View
import androidx.core.widget.NestedScrollView
import androidx.navigation.Navigation
import androidx.navigation.fragment.navArgs
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.core.base.BaseFragment
import com.example.core.network.result.Status
import com.example.extensions.showMessage
import com.example.extensions.visible
import com.example.model.Items
import com.example.model.PlayList
import com.example.ui.main.MainViewModel
import com.example.ui.playlist.adapter.SpecPlaylistAdapter
import com.example.youtube.R
import kotlinx.android.synthetic.main.content_scrolling.*
import kotlinx.android.synthetic.main.spec_playlist_fragmen.*
import org.koin.android.ext.android.inject

class SpecPlaylistFragment : BaseFragment(
    R.layout.spec_playlist_fragmen
) {
    
    private val viewModel: SpecPlaylistViewModel by inject()

    private var nextPageToken: String? = null

    private var specPlaylistAdapter: SpecPlaylistAdapter? = null

    private val args: SpecPlaylistFragmentArgs by navArgs()

    override fun setupUI() {
        title_playlist.text = args.item?.snippet?.title
        initData()
        btn_exit.setOnClickListener { Navigation.findNavController(btn_exit).navigateUp() }
    }

    private fun initData() {
        viewModel.loading.observe(this, { progress_barr_video.visible = it })
        args.item?.id?.let { key ->
            viewModel.fetchSpecPlaylist(key)

            viewModel.page.observe(this) { info ->
                when (info.status) {

                    Status.LOADING -> {
                        viewModel.loading.postValue(true)
                    }
                    Status.SUCCESS -> {
                        info.data?.let {
                            if (it.items.isNotEmpty()) {
                                initDesc(info.data)
                                specPlaylistAdapter =
                                    SpecPlaylistAdapter(info.data) { m, v -> onClickPlaylist(m, v) }
                                initRecycler()
                            }
                        }
                        viewModel.loading.postValue(false)
                        this.nextPageToken = info.data?.nextPageToken
                    }
                    Status.ERROR -> {
                        viewModel.loading.postValue(true)
                        context?.showMessage(info.message)
                    }
                }
            }
        }
    }

    private fun initRecycler() {
        recycler_video.layoutManager = LinearLayoutManager(context)
        recycler_video.adapter = specPlaylistAdapter
    }

    private fun initDesc(data: PlayList) {
        count_videos.text =
            String.format("${data.pageInfo.totalResults} ${getString(R.string.video_series)}")
        desc_playlist.text = data.items[0].snippet.description
    }

    private fun initScrollView() {
        scroll_view_video.setOnScrollChangeListener(NestedScrollView.OnScrollChangeListener { v, _, scrollY, _, _ ->
            if (nextPageToken != null && scrollY == v.getChildAt(0).measuredHeight - v.measuredHeight) {
                getNextPage()
            }
        })
    }

    private fun getNextPage() {
        args.item?.id?.let {
            nextPageToken?.let { it1 ->
                viewModel.fetchNextSpecPlaylist(it, it1)
                viewModel.nexPage.observe(this) { info ->
                    when (info.status) {
                        Status.LOADING -> {
                            viewModel.loading.postValue(true)
                        }
                        Status.SUCCESS -> {
                            info.data?.let {
                                this.nextPageToken = info.data.nextPageToken
                                specPlaylistAdapter?.submitList(info.data.items as ArrayList<Items>)
                            }
                            viewModel.loading.postValue(false)
                        }
                        Status.ERROR -> {
                            viewModel.loading.postValue(true)
                            context?.showMessage(info.message)
                        }
                    }
                }
            }
        }
    }

    private fun onClickPlaylist(m: String, v: View) {
        context?.showMessage(m)
    }

    override fun showConnectedState() {
        initData()
    }

    override fun setupObservers() {
        initScrollView()
    }
}