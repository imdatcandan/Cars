package com.imdatcandan.cars.domain

import com.imdatcandan.cars.model.CarApiModel
import com.imdatcandan.cars.model.CarUiModel

class CarDomainMapper : DomainMapper<CarApiModel, CarUiModel> {
    override fun mapToDomain(data: CarApiModel): CarUiModel {
        return CarUiModel(
            carImageUrl = data.carImageUrl ?: "",
            color = data.color ?: "",
            fuelLevel = data.fuelLevel ?: 0.0,
            fuelType = data.fuelType ?: "",
            group = data.group ?: "",
            id = data.id ?: "",
            innerCleanliness = data.innerCleanliness ?: "",
            latitude = data.latitude ?: 0.0,
            licensePlate = data.licensePlate ?: "",
            longitude = data.longitude ?: 0.0,
            make = data.make ?: "",
            modelIdentifier = data.modelIdentifier ?: "",
            modelName = data.modelName ?: "",
            name = data.name ?: "",
            series = data.series ?: "",
            transmission = data.transmission ?: ""
        )
    }
}