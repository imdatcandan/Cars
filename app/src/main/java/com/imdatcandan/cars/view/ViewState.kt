package com.imdatcandan.cars.view

import com.imdatcandan.cars.model.CarUiModel

sealed class ViewState {
    data class Success(val carList: List<CarUiModel>) : ViewState()
    data class Error(val exception: Throwable) : ViewState()
    data class Loading(val isLoading: Boolean) : ViewState()
}