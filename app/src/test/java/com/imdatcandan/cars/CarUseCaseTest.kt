package com.imdatcandan.cars

import com.imdatcandan.cars.data.CarRepository
import com.imdatcandan.cars.domain.CarDomainMapper
import com.imdatcandan.cars.domain.CarUseCase
import com.imdatcandan.cars.model.CarApiModel
import com.imdatcandan.cars.model.CarUiModel
import io.mockk.coEvery
import io.mockk.every
import io.mockk.mockk
import kotlinx.coroutines.ExperimentalCoroutinesApi
import org.junit.Assert.assertEquals
import org.junit.Test

@ExperimentalCoroutinesApi
class CarUseCaseTest : BaseUnitTest<CarUseCase>() {

    private val carRepository: CarRepository = mockk(relaxed = true)
    private val carDomainMapper: CarDomainMapper = mockk(relaxed = true)
    private val carApiModel: CarApiModel = mockk(relaxed = true)
    private val carUiModel: CarUiModel = mockk(relaxed = true)
    private val carApiModelList: List<CarApiModel> = listOf(carApiModel)
    private val carUiModelList: List<CarUiModel> = listOf(carUiModel)

    override fun initSelf() = CarUseCase(carDomainMapper, carRepository)

    @Test
    fun testGetCarList() = testCoroutine {
        coEvery { carRepository.getCarList() } returns carApiModelList
        every { carDomainMapper.mapToDomain(carApiModel) } returns carUiModel

        val result = tested.getCarList()

        assertEquals(result, carUiModelList)
    }

}