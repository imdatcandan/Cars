package com.imdatcandan.cars.domain

import com.imdatcandan.cars.data.CarRepository
import com.imdatcandan.cars.model.CarUiModel

class CarUseCase(
    private val carDomainMapper: CarDomainMapper,
    private val carRepository: CarRepository
) {

    suspend fun getCarList(): List<CarUiModel> {
        val carListApiModel = carRepository.getCarList()

        return carListApiModel.map {
            carDomainMapper.mapToDomain(it)
        }
    }
}
