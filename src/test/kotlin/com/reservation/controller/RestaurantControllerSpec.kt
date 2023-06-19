package com.reservation.controller

import com.reservation.assembler.RestaurantAssembler
import com.reservation.domain.Restaurant
import com.reservation.domain.RestaurantKey
import com.reservation.exception.BadRequestException
import com.reservation.exception.NotFoundException
import com.reservation.request.RestaurantRequestDTO
import com.reservation.service.RestaurantService
import org.assertj.core.api.Assertions.assertThat
import org.junit.Before
import org.junit.Test
import org.mockito.Mockito.`when`
import org.junit.runner.RunWith
import org.mockito.Mock
import org.mockito.junit.MockitoJUnitRunner
import org.springframework.http.HttpStatus
import org.mockito.BDDMockito.given

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
    fun testInitialize_when_success() {
        `when`(restaurantService.initialize(any())).thenReturn(getMockRestaurant())

        val actual = restaurantController.initialize(getMockRestaurantRequestDTO())
        val actualBody = actual.body

        assertThat(actual.statusCode).isEqualTo(HttpStatus.OK)
        actualBody?.let {
            assertThat(it.restaurantName).isEqualTo("Mock name")
            assertThat(it.date).isEqualTo("Mock date")
            assertThat(it.initialized).isEqualTo(true)
            assertThat(it.numberOfTable).isEqualTo(5)
        }
    }

    @Test(expected = BadRequestException::class)
    fun testInitialize_when_table_already_initialize() {
        given(restaurantService.initialize(any())).willAnswer {
            throw BadRequestException("Mock exception")
        }
        restaurantController.initialize(getMockRestaurantRequestDTO())
    }

    @Test
    fun testGetRestaurant_when_success() {
        `when`(restaurantService.getRestaurantByKey(any(), any())).thenReturn(getMockRestaurant())

        val actual = restaurantController.getRestaurant("mock name", "mock date")
        val actualBody = actual.body

        assertThat(actual.statusCode).isEqualTo(HttpStatus.OK)
        actualBody?.let {
            assertThat(it.restaurantName).isEqualTo("Mock name")
            assertThat(it.date).isEqualTo("Mock date")
            assertThat(it.initialized).isEqualTo(true)
            assertThat(it.numberOfTable).isEqualTo(5)
        }
    }

    @Test(expected = NotFoundException::class)
    fun testGetRestaurant_when_not_found() {
        given(restaurantService.getRestaurantByKey(any(), any())).willAnswer {
            throw NotFoundException("Mock exception")
        }
        restaurantController.getRestaurant("mock name", "mock date")
    }

    private fun getMockRestaurant(): Restaurant {
        val restaurant = Restaurant()
        val key = RestaurantKey()
        key.restaurantName = "Mock name"
        key.date = "Mock date"

        restaurant.key = key
        restaurant.initialized = true
        restaurant.numberOfTable = 5

        return restaurant
    }

    private fun getMockRestaurantRequestDTO(): RestaurantRequestDTO {
        val dto = RestaurantRequestDTO()
        dto.restaurantName = "Mock name"
        dto.date = "Mock date"
        dto.initialized = true
        dto.numberOfTable = 5
        return dto
    }

    private fun <T> any(): T {
        return org.mockito.ArgumentMatchers.any()
    }
}