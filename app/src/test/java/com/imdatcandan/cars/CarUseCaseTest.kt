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
import kotlinx.coroutines.test.runBlockingTest
import org.junit.Assert.assertEquals
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import org.junit.runners.JUnit4


@ExperimentalCoroutinesApi
@RunWith(JUnit4::class)
class CarUseCaseTest {

    private lateinit var useCase: CarUseCase

    private val carRepository: CarRepository = mockk(relaxed = true)
    private val carDomainMapper: CarDomainMapper = mockk(relaxed = true)
    private val carApiModel: CarApiModel = mockk(relaxed = true)
    private val carUiModel: CarUiModel = mockk(relaxed = true)
    private val carApiModelList: List<CarApiModel> = listOf(carApiModel)
    private val carUiModelList: List<CarUiModel> = listOf(carUiModel)

    @Before
    fun setup() {
        useCase = CarUseCase(carDomainMapper, carRepository)
    }

    @Test
    fun testGetCarList() = runBlockingTest {
        coEvery { carRepository.getCarList() } returns carApiModelList
        every { carDomainMapper.mapToDomain(carApiModel) } returns carUiModel

        val result = useCase.getCarList()

        assertEquals(result, carUiModelList)

    }

}