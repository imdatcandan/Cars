package com.imdatcandan.cars.view

import android.content.Context
import android.graphics.Bitmap
import android.view.View
import com.bumptech.glide.Glide
import com.bumptech.glide.load.DataSource
import com.bumptech.glide.load.engine.GlideException
import com.bumptech.glide.request.RequestListener
import com.bumptech.glide.request.target.Target
import com.google.android.gms.maps.model.BitmapDescriptorFactory
import com.google.android.gms.maps.model.Marker
import com.imdatcandan.cars.R

fun View.visible() {
    visibility = View.VISIBLE
}

fun View.gone() {
    visibility = View.GONE
}

fun View.showLoading(show: Boolean) {
    if (show) {
        visible()
    } else {
        gone()
    }
}

fun Marker.loadIcon(context: Context, url: String?) {
    Glide.with(context)
        .asBitmap()
        .load(url)
        .placeholder(R.drawable.ic_placeholder_car_icon)
        .error(R.drawable.ic_placeholder_car_icon) // to show a default icon in case of any errors
        .listener(object : RequestListener<Bitmap> {
            override fun onLoadFailed(
                e: GlideException?,
                model: Any?,
                target: Target<Bitmap>?,
                isFirstResource: Boolean
            ): Boolean {
                return false
            }

            override fun onResourceReady(
                resource: Bitmap?,
                model: Any?,
                target: Target<Bitmap>?,
                dataSource: DataSource?,
                isFirstResource: Boolean
            ): Boolean {
                return resource?.let {
                    BitmapDescriptorFactory.fromBitmap(it)
                }?.let {
                    setIcon(it); true
                } ?: false
            }
        }).submit()
}