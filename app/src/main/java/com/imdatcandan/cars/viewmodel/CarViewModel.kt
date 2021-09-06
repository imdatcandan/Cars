package com.imdatcandan.cars.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.imdatcandan.cars.domain.CarUseCase
import com.imdatcandan.cars.view.ViewState
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch

class CarViewModel(private val carUseCase: CarUseCase) : ViewModel() {

    private val _uiStateFlow: MutableStateFlow<ViewState> =
        MutableStateFlow(ViewState.Loading(false))
    val uiStateFlow: StateFlow<ViewState> = _uiStateFlow

    init {
        getCarList()
    }

    fun getCarList() {
        viewModelScope.launch {
            try {
                _uiStateFlow.value = ViewState.Loading(true)
                val carList = carUseCase.getCarList()
                _uiStateFlow.value = ViewState.Success(carList)
            } catch (exception: Exception) {
                _uiStateFlow.value = ViewState.Error(exception)
            } finally {
                _uiStateFlow.value = ViewState.Loading(false)
            }
        }
    }
}