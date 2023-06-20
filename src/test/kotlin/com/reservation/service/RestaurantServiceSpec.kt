package com.reservation.service

import com.reservation.exception.NotFoundException
import com.reservation.exception.ReservationException
import com.reservation.helper.TestHelper.any
import com.reservation.helper.TestHelper.getMockRestaurant
import com.reservation.repository.RestaurantRepository
import org.assertj.core.api.Assertions
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import org.mockito.Mock
import org.mockito.Mockito.`when`
import org.mockito.junit.MockitoJUnitRunner
import java.util.*

@RunWith(MockitoJUnitRunner::class)
class RestaurantServiceSpec {

    @Mock
    private lateinit var restaurantRepository: RestaurantRepository
    private lateinit var restaurantService: RestaurantService

    @Before
    fun setup() {
        restaurantService = RestaurantService(restaurantRepository)
    }

    @Test
    fun test_getRestaurantByKey_when_success() {
        `when`(restaurantRepository.findById(any())).thenReturn(Optional.of(getMockRestaurant()))

        val actual = restaurantService.getRestaurantByKey("mock id", "mock date")

        Assertions.assertThat(actual.key?.restaurantName).isEqualTo("mock name")
        Assertions.assertThat(actual.key?.date).isEqualTo("mock date")
        Assertions.assertThat(actual.numberOfTable).isEqualTo(5)
        Assertions.assertThat(actual.version).isEqualTo(0)
    }

    @Test(expected = NotFoundException::class)
    fun test_getRestaurantByKey_when_not_found() {
        `when`(restaurantRepository.findById(any())).thenReturn(Optional.ofNullable(null))

        restaurantService.getRestaurantByKey("mock id", "mock date")
    }

    @Test
    fun test_increaseTable_when_success() {
        `when`(restaurantRepository.save(any())).thenReturn(getMockRestaurant())

        val actual = restaurantService.increaseTable(getMockRestaurant(), 2)

        Assertions.assertThat(actual.key?.restaurantName).isEqualTo("mock name")
        Assertions.assertThat(actual.key?.date).isEqualTo("mock date")
        Assertions.assertThat(actual.numberOfTable).isEqualTo(5)
        Assertions.assertThat(actual.version).isEqualTo(0)
    }

    @Test
    fun test_decreaseTable_when_success() {
        `when`(restaurantRepository.save(any())).thenReturn(getMockRestaurant())

        val actual = restaurantService.decreaseTable(getMockRestaurant(), 2)

        Assertions.assertThat(actual.key?.restaurantName).isEqualTo("mock name")
        Assertions.assertThat(actual.key?.date).isEqualTo("mock date")
        Assertions.assertThat(actual.numberOfTable).isEqualTo(5)
        Assertions.assertThat(actual.version).isEqualTo(0)
    }

    @Test(expected = ReservationException::class)
    fun test_decreaseTable_when_table_not_enough() {
        val actual = restaurantService.decreaseTable(getMockRestaurant(), 10)

        Assertions.assertThat(actual.key?.restaurantName).isEqualTo("mock name")
        Assertions.assertThat(actual.key?.date).isEqualTo("mock date")
        Assertions.assertThat(actual.numberOfTable).isEqualTo(5)
        Assertions.assertThat(actual.version).isEqualTo(0)
    }

}