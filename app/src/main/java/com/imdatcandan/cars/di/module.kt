package com.imdatcandan.cars.di


import com.imdatcandan.cars.BuildConfig
import com.imdatcandan.cars.data.CarRepository
import com.imdatcandan.cars.data.CarService
import com.imdatcandan.cars.domain.CarDomainMapper
import com.imdatcandan.cars.domain.CarUseCase
import com.imdatcandan.cars.viewmodel.CarViewModel
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import org.koin.android.viewmodel.dsl.viewModel
import org.koin.core.module.Module
import org.koin.dsl.module
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

val appModule = module {
    single { CarRepository(get()) }
    single { CarDomainMapper() }
    single { CarUseCase(get(), get()) }
    viewModel { CarViewModel(get()) }
}

val networkModule: Module = module {
    single { provideLoggingInterceptor() }
    single { provideOkHttpClient(get()) }
    single { provideRetrofit(get()) }
    single { provideCarService(get()) }
}

private fun provideLoggingInterceptor(): HttpLoggingInterceptor {
    return HttpLoggingInterceptor().apply {
        level = HttpLoggingInterceptor.Level.BODY
    }
}

private fun provideOkHttpClient(loggingInterceptor: HttpLoggingInterceptor): OkHttpClient {
    return OkHttpClient.Builder()
        .addInterceptor(loggingInterceptor)
        .build()
}

private fun provideCarService(retrofit: Retrofit): CarService =
    retrofit.create(CarService::class.java)

private fun provideRetrofit(okHttpClient: OkHttpClient): Retrofit {
    return Retrofit.Builder()
        .baseUrl(BuildConfig.BASE_API_URL)
        .client(okHttpClient)
        .addConverterFactory(GsonConverterFactory.create())
        .build()
}