package com.imdatcandan.cars.data

import com.imdatcandan.cars.model.CarApiModel
import retrofit2.http.GET

interface CarService {

    @GET("codingtask/cars")
    suspend fun getCarList(): List<CarApiModel>
}