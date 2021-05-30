package com.example.selltech

import android.content.Context
import android.widget.ImageView
import com.bumptech.glide.Glide
import java.io.IOException

class GlideLoader() {

    fun loadProductPicture(context:Context,image: Any, imageView: ImageView) {
        try {
            Glide.with(context)
                .load(image)
                .centerCrop()
                .placeholder(R.drawable.ic_us)
                .into(imageView)


        } catch (e: IOException) {

            e.printStackTrace()
        }

    }
}