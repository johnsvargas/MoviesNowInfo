package com.bleckoviohns.moviesnowinfo.util.commonviews

import android.util.Log
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView


abstract class RecyclerViewOnScroll: RecyclerView.OnScrollListener() {
    override fun onScrolled(recyclerView: RecyclerView, dx: Int, dy: Int) {
        super.onScrolled(recyclerView, dx, dy)
        if (dy > 0) {
            val gridLayoutManager = recyclerView.layoutManager as GridLayoutManager?
            gridLayoutManager?.let{
                val visibleItemCount = it.childCount
                val totalItemCount = it.itemCount
                val firstVisibleItems = it.findFirstVisibleItemPosition()
                if (!isLoading() && !isLastPage() && (visibleItemCount + firstVisibleItems >= totalItemCount)) {
                    loadMoreItems()
                }
            }

        }
    }
    abstract fun isLastPage(): Boolean

    abstract fun isLoading(): Boolean

    abstract fun loadMoreItems()
}