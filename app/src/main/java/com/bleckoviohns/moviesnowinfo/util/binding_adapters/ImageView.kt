package com.bleckoviohns.moviesnowinfo.util.binding_adapters

import android.graphics.drawable.Drawable
import android.widget.ImageView
import androidx.databinding.BindingAdapter
import com.bumptech.glide.Glide

@BindingAdapter("imageUrl", "placeholder", requireAll = false)
fun ImageView.setImage(url: String?, placeHolder: Drawable?) {
    if (!url.isNullOrEmpty()) {
        Glide.with(context)
            .load(url)
            .placeholder(placeHolder)
            .into(this)
    } else {
        setImageDrawable(placeHolder)
    }
}