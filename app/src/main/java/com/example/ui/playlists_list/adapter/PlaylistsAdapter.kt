package com.example.ui.playlists_list.adapter

import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.extensions.inflate
import com.example.extensions.loadImage
import com.example.model.Items
import com.example.model.PlayList
import com.example.youtube.R
import kotlinx.android.synthetic.main.item_playlist.view.*

class PlaylistsAdapter(
    playList: PlayList,
    private val onClick: (m: Items, v: View) -> Unit
) : RecyclerView.Adapter<PlaylistsAdapter.ViewHolder>() {

    var arrayList: ArrayList<Items> = playList.items as ArrayList<Items>

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        return ViewHolder(
            parent.inflate(
                R.layout.item_playlist, false
            )
        )
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.onBind(arrayList[position], onClick)
    }

    override fun getItemCount(): Int = arrayList.size

    fun submitList(items: ArrayList<Items>) {
        val start = arrayList.size - 1
        arrayList.addAll(items)
        notifyItemRangeChanged(start, arrayList.size - 1)
    }


    inner class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {

        fun onBind(playList: Items, onClick: (m: Items, v: View) -> Unit) {

            itemView.textTitle.text = playList.snippet.title

            itemView.imageView.loadImage(playList.snippet.thumbnails?.default?.url)
            itemView.subTitle.text =
                String.format("${playList.contentDetails.itemCount} video series")
            itemView.setOnClickListener {
                onClick(arrayList[adapterPosition], itemView)
            }
        }
    }
}