package com.imdatcandan.cars.view

import android.graphics.Bitmap
import android.graphics.Canvas
import android.graphics.drawable.Drawable
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.coroutineScope
import com.bumptech.glide.Glide
import com.bumptech.glide.request.target.SimpleTarget
import com.bumptech.glide.request.transition.Transition
import com.google.android.gms.maps.CameraUpdateFactory
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.OnMapReadyCallback
import com.google.android.gms.maps.SupportMapFragment
import com.google.android.gms.maps.model.BitmapDescriptorFactory
import com.google.android.gms.maps.model.LatLng
import com.google.android.gms.maps.model.MarkerOptions
import com.imdatcandan.cars.R
import com.imdatcandan.cars.databinding.ActivityCarsBinding
import com.imdatcandan.cars.model.CarUiModel
import com.imdatcandan.cars.viewmodel.CarViewModel
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch
import org.koin.androidx.viewmodel.ext.android.viewModel


class CarsActivity : AppCompatActivity(), OnMapReadyCallback {

    private val viewModel: CarViewModel by viewModel()
    private lateinit var carMap: GoogleMap
    private lateinit var binding: ActivityCarsBinding
    private val carListAdapter = CarListAdapter()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this, R.layout.activity_cars)
        binding.viewModel = viewModel
        binding.lifecycleOwner = this
        binding.adapter = carListAdapter

        val mapFragment = supportFragmentManager.findFragmentById(R.id.map) as SupportMapFragment
        mapFragment.getMapAsync(this)

        viewModel.getCarList()

        lifecycle.coroutineScope.launch {
            viewModel.uiStateFlow.collect {
                when (it) {
                    is ViewState.Success -> setCarListOnMap(it.carList)
                    is ViewState.Error -> showErrorDialog(it.exception.message) { _, _ ->
                        viewModel.getCarList()
                    }
                    is ViewState.Loading -> binding.progressBar.showLoading(it.isLoading)
                }
            }
        }
    }

    private fun setCarListOnMap(carList: List<CarUiModel>) {
        carListAdapter.submitList(carList)
        carList.map { car ->
            setIconAsMarker(car)
        }
    }

    override fun onMapReady(googleMap: GoogleMap) {
        carMap = googleMap
    }

    private fun setIconAsMarker(carUiModel: CarUiModel) {
        Glide.with(this).asBitmap()
            .load(carUiModel.carImageUrl)
            .placeholder(R.drawable.ic_placeholder_car_icon)
            .fallback(R.drawable.ic_placeholder_car_icon)
            .error(R.drawable.ic_placeholder_car_icon)
            .into(object : SimpleTarget<Bitmap?>(ICON_SIZE, ICON_SIZE) {
                override fun onResourceReady(
                    bitmap: Bitmap,
                    transition: Transition<in Bitmap?>?
                ) {
                    setIconOnMap(carUiModel, bitmap)
                }

                override fun onLoadFailed(errorDrawable: Drawable?) {
                    errorDrawable?.let {
                        val fallbackIcon = convertDrawableToBitmap(it)
                        setIconOnMap(carUiModel, fallbackIcon)
                    }
                }
            })
    }

    private fun convertDrawableToBitmap(drawable: Drawable): Bitmap {
        val bitmap = Bitmap.createBitmap(
            ICON_SIZE,
            ICON_SIZE,
            Bitmap.Config.ARGB_8888
        )
        val canvas = Canvas(bitmap)
        drawable.setBounds(BOUND_SIZE, BOUND_SIZE, canvas.width, canvas.height)
        drawable.draw(canvas)

        return bitmap
    }

    private fun setIconOnMap(carUiModel: CarUiModel, icon: Bitmap) {
        val carPosition = LatLng(carUiModel.latitude, carUiModel.longitude)

        val markerOptions = MarkerOptions()
            .position(carPosition)
            .title(carUiModel.name)
            .icon(BitmapDescriptorFactory.fromBitmap(icon))

        carMap.addMarker(markerOptions)
        carMap.animateCamera(CameraUpdateFactory.newLatLngZoom(carPosition, ZOOM_LEVEL))
    }

    private companion object {
        private const val ZOOM_LEVEL = 12f
        private const val ICON_SIZE = 120
        private const val BOUND_SIZE = 0
    }
}