package com.imdatcandan.cars

import app.cash.turbine.test
import com.imdatcandan.cars.domain.CarUseCase
import com.imdatcandan.cars.model.CarUiModel
import com.imdatcandan.cars.view.ViewState
import com.imdatcandan.cars.viewmodel.CarViewModel
import io.mockk.coEvery
import io.mockk.mockk
import junit.framework.Assert.assertEquals
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.runBlocking
import org.junit.Test
import kotlin.time.ExperimentalTime

@ExperimentalCoroutinesApi
class CarViewModelTest : BaseUnitTest<CarViewModel>() {

    private val carUseCase: CarUseCase = mockk(relaxed = true)
    private val carList: List<CarUiModel> = mockk(relaxed = true)

    override fun initSelf() = CarViewModel(carUseCase)

    @ExperimentalTime
    @Test
    fun testSuccessViewState() = runBlocking {
        coEvery { carUseCase.getCarList() } returns carList

        tested.uiStateFlow.test {

            tested.getCarList()
            assertEquals(ViewState.Loading(false), awaitItem())
            assertEquals(ViewState.Loading(true), awaitItem())
            assertEquals(ViewState.Success(carList), awaitItem())
            assertEquals(ViewState.Loading(false), awaitItem())
        }
    }

    @ExperimentalTime
    @Test
    fun testErrorViewState() = testCoroutine {
        coEvery { carUseCase.getCarList() } throws ERROR

        tested.uiStateFlow.test {

            tested.getCarList()
            assertEquals(ViewState.Loading(false), awaitItem())
            assertEquals(ViewState.Loading(true), awaitItem())
            assertEquals(ViewState.Error(ERROR), awaitItem())
            assertEquals(ViewState.Loading(false), awaitItem())
        }
    }

    private companion object {
        private val ERROR = Exception("dummy error")
    }

}