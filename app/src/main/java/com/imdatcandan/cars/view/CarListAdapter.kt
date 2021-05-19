package com.imdatcandan.cars.view

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.imdatcandan.cars.databinding.AdapterCarListBinding
import com.imdatcandan.cars.model.CarUiModel

class CarListAdapter : ListAdapter<CarUiModel, CarListAdapter.CarViewHolder>(Companion) {

    class CarViewHolder(val binding: AdapterCarListBinding) : RecyclerView.ViewHolder(binding.root)

    companion object : DiffUtil.ItemCallback<CarUiModel>() {
        override fun areItemsTheSame(oldItem: CarUiModel, newItem: CarUiModel): Boolean =
            oldItem === newItem

        override fun areContentsTheSame(oldItem: CarUiModel, newItem: CarUiModel): Boolean =
            oldItem.id == newItem.id
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CarViewHolder {
        val layoutInflater = LayoutInflater.from(parent.context)
        val binding = AdapterCarListBinding.inflate(layoutInflater)
        return CarViewHolder(binding)
    }

    override fun onBindViewHolder(holder: CarViewHolder, position: Int) {
        val currentCar = getItem(position)
        holder.binding.car = currentCar
        holder.binding.executePendingBindings()
    }
}