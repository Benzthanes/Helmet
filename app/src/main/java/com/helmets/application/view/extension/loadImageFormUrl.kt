package com.helmets.application.view.extension

import android.content.Context
import android.net.Uri
import android.widget.ImageView
import com.bumptech.glide.Glide
import com.bumptech.glide.request.RequestOptions
import com.github.twocoffeesoneteam.glidetovectoryou.GlideToVectorYou

fun ImageView.loadImageFormUrl(context: Context, url: String?, placeHolder: Int, error: Int) {
    if (url != null && url.endsWith("svg")) {
        GlideToVectorYou
            .init()
            .with(context)
            .setPlaceHolder(placeHolder, error)
            .load(Uri.parse(url), this);
    } else {
        Glide.with(this.context)
            .load(url.orEmpty())
            .apply(RequestOptions().error(error).placeholder(placeHolder))
            .into(this)
    }

}

fun ImageView.setImage(drawable: Int) {
    Glide.with(this.context)
        .load(drawable)
        .into(this)

}