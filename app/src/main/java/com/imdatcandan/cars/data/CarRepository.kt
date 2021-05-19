package com.imdatcandan.cars.data

import com.imdatcandan.cars.model.CarApiModel
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

class CarRepository(
    private val carService: CarService,
    private val ioDispatcher: CoroutineDispatcher = Dispatchers.IO
) {
    // As this operation is manually retrieving the cars from the server
    // using a blocking HttpURLConnection, it needs to move the execution
    // to an IO dispatcher to make it main-safe
    suspend fun getCarList(): List<CarApiModel> = withContext(ioDispatcher) {
        return@withContext carService.getCarList()
    }
}
