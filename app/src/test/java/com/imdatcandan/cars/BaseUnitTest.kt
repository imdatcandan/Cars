package com.imdatcandan.cars

import androidx.annotation.CallSuper
import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.TestCoroutineScope
import kotlinx.coroutines.test.runBlockingTest
import org.junit.Before
import org.junit.Rule
import kotlin.coroutines.CoroutineContext
import kotlin.coroutines.EmptyCoroutineContext

@ExperimentalCoroutinesApi
abstract class BaseUnitTest<T : Any> {

    @get:Rule
    val coroutineTestRule = CoroutineTestRule()

    @get:Rule
    val instantTaskExecutorRule = InstantTaskExecutorRule()

    protected lateinit var tested: T

    @OptIn(ExperimentalCoroutinesApi::class)
    @Before
    @CallSuper
    open fun setUp() {
        tested = initSelf()
    }

    protected abstract fun initSelf(): T

    @OptIn(ExperimentalCoroutinesApi::class)
    fun testCoroutine(
        context: CoroutineContext = EmptyCoroutineContext,
        testBody: suspend TestCoroutineScope.() -> Unit
    ) =
        runBlockingTest(context, testBody)
}