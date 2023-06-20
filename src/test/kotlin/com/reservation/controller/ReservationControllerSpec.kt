package com.reservation.controller

import com.reservation.assembler.ReservationAssembler
import com.reservation.exception.NotFoundException
import com.reservation.exception.ReservationException
import com.reservation.helper.TestHelper
import com.reservation.helper.TestHelper.any
import com.reservation.service.ReservationService
import com.reservation.service.RestaurantService
import org.assertj.core.api.Assertions
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import org.mockito.BDDMockito
import org.mockito.Mock
import org.mockito.Mockito.`when`
import org.mockito.junit.MockitoJUnitRunner
import org.springframework.http.HttpStatus

@RunWith(MockitoJUnitRunner::class)
class ReservationControllerSpec {

    @Mock
    private lateinit var reservationService: ReservationService

    @Mock
    private lateinit var restaurantService: RestaurantService
    private var reservationAssembler = ReservationAssembler()
    private lateinit var reservationController: ReservationController

    @Before
    fun setup() {
        reservationController = ReservationController(restaurantService, reservationService, reservationAssembler)
    }

    @Test
    fun test_reserve_when_success() {
        `when`(restaurantService.getRestaurantByKey(any(), any())).thenReturn(TestHelper.getMockRestaurant())
        `when`(reservationService.createReservation(any(), any(), any())).thenReturn(TestHelper.getMockReservation())
        `when`(restaurantService.decreaseTable(any(), any())).thenReturn(TestHelper.getMockRestaurant())

        val actual = reservationController.reserve(TestHelper.getReservationRequestDTO())
        val actualBody = actual.body

        Assertions.assertThat(actual.statusCode).isEqualTo(HttpStatus.OK)
        actualBody?.let {
            Assertions.assertThat(it.reservationId).isEqualTo("mock Id")
            Assertions.assertThat(it.restaurantName).isEqualTo("mock name")
            Assertions.assertThat(it.date).isEqualTo("mock date")
            Assertions.assertThat(it.numberOfGuest).isEqualTo(5)
            Assertions.assertThat(it.numberOfTable).isEqualTo(2)
            Assertions.assertThat(it.active).isEqualTo(true)
            Assertions.assertThat(it.remainingTable).isEqualTo(5)
        }
    }

    @Test(expected = NotFoundException::class)
    fun test_reserve_when_fail() {
        BDDMockito.given(restaurantService.getRestaurantByKey(any(), any())).willAnswer {
            throw NotFoundException("mock exception")
        }
        reservationController.reserve(TestHelper.getReservationRequestDTO())
    }

    @Test
    fun test_cancel_when_success() {
        val mockRestaurant = TestHelper.getMockRestaurant()
        val mockReservation = TestHelper.getMockReservation()

        `when`(reservationService.getReservationById(any())).thenReturn(mockReservation)
        `when`(restaurantService.getRestaurantByKey(any(), any())).thenReturn(mockRestaurant)
        `when`(reservationService.cancelReservation(any())).thenReturn(mockReservation)
        `when`(restaurantService.increaseTable(any(), any())).thenReturn(mockRestaurant)

        val actual = reservationController.cancel("mock name", "mock date", "mock id")
        val actualBody = actual.body

        Assertions.assertThat(actual.statusCode).isEqualTo(HttpStatus.OK)
        actualBody?.let {
            Assertions.assertThat(it.reservationId).isEqualTo("mock Id")
            Assertions.assertThat(it.restaurantName).isEqualTo("mock name")
            Assertions.assertThat(it.date).isEqualTo("mock date")
            Assertions.assertThat(it.numberOfGuest).isEqualTo(5)
            Assertions.assertThat(it.numberOfTable).isEqualTo(2)
            Assertions.assertThat(it.active).isEqualTo(true)
            Assertions.assertThat(it.remainingTable).isEqualTo(5)
        }
    }

    @Test(expected = ReservationException::class)
    fun test_cancel_when_restaurant_mismatch() {
        val mockReservation = TestHelper.getMockReservation()

        `when`(reservationService.getReservationById(any())).thenReturn(mockReservation)

        reservationController.cancel("mock name2", "mock date", "mock id")
    }
}