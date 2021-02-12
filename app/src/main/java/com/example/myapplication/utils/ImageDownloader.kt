package com.example.myapplication.utils

import android.widget.ImageView
import com.bumptech.glide.Glide
import com.bumptech.glide.load.engine.DiskCacheStrategy
import com.bumptech.glide.request.RequestOptions
import com.example.myapplication.R

object ImageDownloader {
    fun download(imageView:ImageView, imagePath:String?){
        if (imagePath == null) return

        Glide.with(imageView.context)
//            .load(BASE_URL_IMAGE +  imagePath)
            .load( imagePath)
            .apply(
                RequestOptions().placeholder(R.mipmap.ic_launcher)
                    .diskCacheStrategy(DiskCacheStrategy.ALL))

            .into(imageView)
    }
}