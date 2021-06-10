package com.imdatcandan.cars

import com.imdatcandan.cars.data.CarRepository
import com.imdatcandan.cars.data.CarService
import com.imdatcandan.cars.model.CarApiModel
import io.mockk.coEvery
import io.mockk.mockk
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.runBlocking
import org.junit.Assert.assertEquals
import org.junit.Test

@ExperimentalCoroutinesApi
class CarRepositoryTest : BaseUnitTest<CarRepository>() {

    private val carService: CarService = mockk(relaxed = true)
    private val carApiModel: CarApiModel = mockk(relaxed = true)
    private val carApiModelList: List<CarApiModel> = listOf(carApiModel)

    override fun initSelf() = CarRepository(carService)

    @Test
    fun testGetCarList() = runBlocking {
        coEvery { carService.getCarList() } returns carApiModelList

        val result = tested.getCarList()

        assertEquals(result, carApiModelList)
    }
}