package com.imdatcandan.cars.domain

interface DomainMapper<D, E> {
    fun mapToDomain(data: D): E
}