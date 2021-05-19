package com.imdatcandan.cars.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.imdatcandan.cars.domain.CarUseCase
import com.imdatcandan.cars.view.ViewState
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class CarViewModel(private val carUseCase: CarUseCase) : ViewModel() {

    private val _stateLiveData: MutableLiveData<ViewState> =
        MutableLiveData(ViewState.Loading(true))
    val stateLiveData: LiveData<ViewState> = _stateLiveData

    init {
        getCarList()
    }

    fun getCarList() {
        viewModelScope.launch(Dispatchers.IO) {
            try {
                val carList = carUseCase.getCarList()
                emitNewState(ViewState.Success(carList))
            } catch (exception: Exception) {
                emitNewState(ViewState.Error(exception))
            } finally {
                emitNewState(ViewState.Loading(false))
            }
        }
    }

    /*
     setValue() method must be called from the main thread.
     But if you need set a value from a background thread, postValue() should be used.
     Instead of using 3 different liveData, I prefer to use stateLiveData for ViewState
     that's why I set the value on main thread otherwise my last state would be set
     */
    private fun emitNewState(newState: ViewState) {
        viewModelScope.launch(Dispatchers.Main) {
            _stateLiveData.value = newState
        }
    }
}