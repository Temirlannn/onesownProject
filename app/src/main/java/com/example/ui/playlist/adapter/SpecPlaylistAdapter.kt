package com.example.ui.playlist.adapter

import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.extensions.inflate
import com.example.extensions.loadImage
import com.example.model.Items
import com.example.model.PlayList
import com.example.youtube.R
import kotlinx.android.synthetic.main.item_playlist.view.*

class SpecPlaylistAdapter( playList: PlayList,private val onClick: (m: String, v: View) -> Unit) :
    RecyclerView.Adapter<SpecPlaylistAdapter.ViewHolder>() {

    val items: ArrayList<Items> = playList.items as ArrayList<Items>

    inner class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        fun onBind(item: Items, onClick: (m: String, v: View) -> Unit) {

            itemView.textTitle.text = item.snippet.title

            itemView.imageView.loadImage(item.snippet.thumbnails?.default?.url)
            itemView.subTitle.text =
                item.contentDetails.videoPublishedAt?.let { String.format(it) }
            itemView.setOnClickListener {
                onClick(items[adapterPosition].id, itemView)
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        return ViewHolder(parent.inflate(R.layout.playlist_item, false))
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.onBind(items[position], onClick)
    }

    fun submitList(temp: ArrayList<Items>) {
        val start = items.size - 1
        items.addAll(temp)
        notifyItemRangeChanged(start, items.size - 1)
    }

    override fun getItemCount(): Int {
        return items.size
    }
}