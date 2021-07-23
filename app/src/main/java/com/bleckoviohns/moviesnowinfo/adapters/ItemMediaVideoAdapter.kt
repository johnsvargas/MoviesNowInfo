package com.bleckoviohns.moviesnowinfo.adapters

import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.bleckoviohns.moviesnowinfo.R
import com.bleckoviohns.moviesnowinfo.data.model.MediaType
import com.bleckoviohns.moviesnowinfo.data.model.MediaVideo
import com.bleckoviohns.moviesnowinfo.databinding.ItemCardMovieOrTvBinding
import com.bumptech.glide.Glide


class ItemMediaVideoAdapter : RecyclerView.Adapter<ItemMediaVideoAdapter.ItemMediaVideoHolder> (){
    private val listItems:MutableList<MediaVideo> = ArrayList()
    private var onClick: ((mediaVideo: MediaVideo,title: String) -> Unit)? = null

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ItemMediaVideoHolder {
        val layoutInflater = LayoutInflater.from(parent.context)
        val view = ItemCardMovieOrTvBinding.inflate(layoutInflater, parent, false)
        return ItemMediaVideoHolder(view)
    }

    override fun onBindViewHolder(holder: ItemMediaVideoHolder, position: Int) {
        holder.bind(listItems[position])
    }

    fun setData(data : MutableList<MediaVideo>) {
        val size = listItems.size
        listItems.addAll(data)
        val sizeNew = listItems.size
        notifyItemRangeChanged(size, sizeNew)
    }


    fun setOnClickListener(onClick: ((mediaVideo: MediaVideo,title: String) -> Unit)) {
        this.onClick = onClick
    }

    override fun getItemCount() = listItems.size

    inner class ItemMediaVideoHolder(itemView: ItemCardMovieOrTvBinding): RecyclerView.ViewHolder(itemView.root){
        private val titleTextView: TextView = itemView.tvItemTitleMovieOrTv
        private val imagePoster: ImageView = itemView.ivItemImageMovieOrTv
        fun bind(mediaVideo: MediaVideo){

                itemView.run {
                    val title = when(mediaVideo.mediaType){
                        MediaType.Movie.value -> mediaVideo.originalTitle
                        MediaType.Tv.value -> mediaVideo.name
                        else -> mediaVideo.name
                    }

                    Glide.with(itemView)
                        .load(mediaVideo.getPosterImage())
                        .placeholder(R.drawable.background_movies)
                        .into(imagePoster)
                    rootView.setOnClickListener {
                        onClick?.invoke(mediaVideo,title.toString())
                    }
                    titleTextView.text = title

                }

        }
    }
}