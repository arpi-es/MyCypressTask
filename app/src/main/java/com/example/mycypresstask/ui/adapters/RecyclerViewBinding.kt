package com.example.mycypresstask.ui.adapters

import android.widget.ImageView
import androidx.databinding.BindingAdapter
import com.example.mycypresstask.R
import com.squareup.picasso.Picasso

object RecyclerViewBinding {
    @BindingAdapter("imageUrl")
    @JvmStatic
    fun loadImage(view: ImageView, url: String?) {
        Picasso.get().load(url)
            .error(R.drawable.image_fail)
            .placeholder(R.drawable.default_placeholder)
            .into(view)
    }
}


