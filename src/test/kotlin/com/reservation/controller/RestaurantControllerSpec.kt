package com.reservation.controller

import com.reservation.assembler.RestaurantAssembler
import com.reservation.exception.BadRequestException
import com.reservation.exception.NotFoundException
import com.reservation.helper.TestHelper
import com.reservation.helper.TestHelper.any
import com.reservation.service.RestaurantService
import org.assertj.core.api.Assertions.assertThat
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import org.mockito.BDDMockito.given
import org.mockito.Mock
import org.mockito.Mockito.`when`
import org.mockito.junit.MockitoJUnitRunner
import org.springframework.http.HttpStatus

@RunWith(MockitoJUnitRunner::class)
class RestaurantControllerSpec {

    @Mock
    private lateinit var restaurantService: RestaurantService
    private var restaurantAssembler = RestaurantAssembler()
    private lateinit var restaurantController: RestaurantController

    @Before
    fun setup() {
        restaurantController = RestaurantController(restaurantService, restaurantAssembler)
    }

    @Test
    fun test_initialize_when_success() {
        `when`(restaurantService.initialize(any())).thenReturn(TestHelper.getMockRestaurant())

        val actual = restaurantController.initialize(TestHelper.getMockRestaurantRequestDTO())
        val actualBody = actual.body

        assertThat(actual.statusCode).isEqualTo(HttpStatus.OK)
        actualBody?.let {
            assertThat(it.restaurantName).isEqualTo("mock name")
            assertThat(it.date).isEqualTo("mock date")
            assertThat(it.initialized).isEqualTo(true)
            assertThat(it.numberOfTable).isEqualTo(5)
        }
    }

    @Test(expected = BadRequestException::class)
    fun test_initialize_when_table_already_initialize() {
        given(restaurantService.initialize(any())).willAnswer {
            throw BadRequestException("mock exception")
        }
        restaurantController.initialize(TestHelper.getMockRestaurantRequestDTO())
    }

    @Test
    fun test_getRestaurant_when_success() {
        `when`(restaurantService.getRestaurantByKey(any(), any())).thenReturn(TestHelper.getMockRestaurant())

        val actual = restaurantController.getRestaurant("mock name", "mock date")
        val actualBody = actual.body

        assertThat(actual.statusCode).isEqualTo(HttpStatus.OK)
        actualBody?.let {
            assertThat(it.restaurantName).isEqualTo("mock name")
            assertThat(it.date).isEqualTo("mock date")
            assertThat(it.initialized).isEqualTo(true)
            assertThat(it.numberOfTable).isEqualTo(5)
        }
    }

    @Test(expected = NotFoundException::class)
    fun test_getRestaurant_when_not_found() {
        given(restaurantService.getRestaurantByKey(any(), any())).willAnswer {
            throw NotFoundException("mock exception")
        }
        restaurantController.getRestaurant("mock name", "mock date")
    }
}