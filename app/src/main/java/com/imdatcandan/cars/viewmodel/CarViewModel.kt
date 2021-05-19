package com.imdatcandan.cars.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.imdatcandan.cars.domain.CarUseCase
import com.imdatcandan.cars.view.ViewState
import kotlinx.coroutines.launch

class CarViewModel(private val carUseCase: CarUseCase) : ViewModel() {

    private val _stateLiveData: MutableLiveData<ViewState> = MutableLiveData()
    val stateLiveData: LiveData<ViewState> = _stateLiveData

    init {
        getCarList()
    }

    fun getCarList() {
        viewModelScope.launch {
            try {
                _stateLiveData.value = ViewState.Loading(true)
                val carList = carUseCase.getCarList()
                _stateLiveData.value = ViewState.Success(carList)
            } catch (exception: Exception) {
                _stateLiveData.value = ViewState.Error(exception)
            } finally {
                _stateLiveData.value = ViewState.Loading(false)
            }
        }
    }
}