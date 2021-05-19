package com.imdatcandan.cars.view

import android.widget.ImageView
import androidx.databinding.BindingAdapter
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.imdatcandan.cars.R

@BindingAdapter("imageUrl")
fun loadImage(view: ImageView, url: String) =
    Glide.with(view.context).load(url).placeholder(R.drawable.ic_placeholder_car_icon)
        .fallback(R.drawable.ic_placeholder_car_icon).into(view)

@BindingAdapter(value = ["setAdapter"])
fun RecyclerView.bindRecyclerViewAdapter(adapter: RecyclerView.Adapter<*>) {
    this.run {
        this.setHasFixedSize(true)
        this.adapter = adapter
    }
}