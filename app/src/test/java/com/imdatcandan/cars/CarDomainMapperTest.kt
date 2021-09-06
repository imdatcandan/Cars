package com.imdatcandan.cars

import com.imdatcandan.cars.domain.CarDomainMapper
import com.imdatcandan.cars.model.CarApiModel
import com.imdatcandan.cars.model.CarUiModel
import io.mockk.mockk
import kotlinx.coroutines.ExperimentalCoroutinesApi
import org.junit.Assert.assertEquals
import org.junit.Test

@ExperimentalCoroutinesApi
class CarDomainMapperTest : BaseUnitTest<CarDomainMapper>() {

    override fun initSelf() = CarDomainMapper()

    @Test
    fun mapCarApiModelToCarUiModel() {
        val result = tested.mapToDomain(carApiModel)

        assertEquals(result, carUiModel)
    }

    @Test
    fun mapEmptyCarApiModelToEmptyCarUiModel() {
        val result = tested.mapToDomain(emptyCarApiModel)

        assertEquals(result, emptyCarUiModel)
    }

    companion object {
        private val emptyCarApiModel = mockk<CarApiModel>(relaxed = true)
        private val emptyCarUiModel = CarUiModel(
            carImageUrl = "",
            color = "",
            fuelLevel = 0.0,
            fuelType = "",
            group = "",
            id = "",
            innerCleanliness = "",
            latitude = 0.0,
            licensePlate = "",
            longitude = 0.0,
            make = "",
            modelIdentifier = "",
            modelName = "",
            name = "",
            series = "",
            transmission = ""
        )

        private val carApiModel: CarApiModel = CarApiModel(
            id = "WMWSW31030T222518",
            modelIdentifier = "mini",
            modelName = "MINI",
            name = "Vanessa",
            make = "BMW",
            group = "MINI",
            color = "midnight_black",
            series = "MINI",
            fuelType = null,
            fuelLevel = null,
            transmission = null,
            licensePlate = "M-VO0259",
            latitude = 48.134557,
            longitude = 11.576921,
            innerCleanliness = "REGULAR",
            carImageUrl = "https://cdn.sixt.io/codingtask/images/mini.png"
        )

        private val carUiModel: CarUiModel = CarUiModel(
            id = "WMWSW31030T222518",
            modelIdentifier = "mini",
            modelName = "MINI",
            name = "Vanessa",
            make = "BMW",
            group = "MINI",
            color = "midnight_black",
            series = "MINI",
            fuelType = "",
            fuelLevel = 0.0,
            transmission = "",
            licensePlate = "M-VO0259",
            latitude = 48.134557,
            longitude = 11.576921,
            innerCleanliness = "REGULAR",
            carImageUrl = "https://cdn.sixt.io/codingtask/images/mini.png"
        )
    }
}