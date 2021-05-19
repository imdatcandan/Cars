package com.imdatcandan.cars

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.lifecycle.Observer
import com.imdatcandan.cars.domain.CarUseCase
import com.imdatcandan.cars.model.CarUiModel
import com.imdatcandan.cars.view.ViewState
import com.imdatcandan.cars.viewmodel.CarViewModel
import io.mockk.coEvery
import io.mockk.mockk
import io.mockk.spyk
import io.mockk.verifyOrder
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.runBlockingTest
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith
import org.junit.runners.JUnit4

@ExperimentalCoroutinesApi
@RunWith(JUnit4::class)
class CarViewModelTest {

    @ExperimentalCoroutinesApi
    @get:Rule
    val coroutineTestRule = CoroutineTestRule()

    @get:Rule
    val instantTaskExecutorRule = InstantTaskExecutorRule()

    private lateinit var viewModel: CarViewModel
    private lateinit var mockedObserver: Observer<ViewState>

    private val useCase: CarUseCase = mockk(relaxed = true)
    private val carList: List<CarUiModel> = mockk(relaxed = true)

    @Before
    fun setup() {
        viewModel = CarViewModel(useCase)
        mockedObserver = createViewStateObserver()
        viewModel.stateLiveData.observeForever(mockedObserver)
    }

    @Test
    fun testSuccessViewState() = runBlockingTest {
        coEvery { useCase.getCarList() } returns carList

        viewModel.getCarList()

        verifyOrder {
            mockedObserver.onChanged(ViewState.Loading(true))
            mockedObserver.onChanged(ViewState.Success(carList))
            mockedObserver.onChanged(ViewState.Loading(false))
        }
    }

    @Test
    fun testErrorViewState() = runBlockingTest {
        coEvery { useCase.getCarList() } throws ERROR

        viewModel.getCarList()

        verifyOrder {
            mockedObserver.onChanged(ViewState.Loading(true))
            mockedObserver.onChanged(ViewState.Error(ERROR))
            mockedObserver.onChanged(ViewState.Loading(false))
        }
    }

    private companion object {
        private val ERROR = Exception("dummy error")
    }

    private fun createViewStateObserver(): Observer<ViewState> = spyk(Observer { })

}