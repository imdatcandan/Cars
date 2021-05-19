package com.imdatcandan.cars.data

import com.imdatcandan.cars.model.CarApiModel

class CarRepository(private val carService: CarService) {

    suspend fun getCarList(): List<CarApiModel> = carService.getCarList()
}
