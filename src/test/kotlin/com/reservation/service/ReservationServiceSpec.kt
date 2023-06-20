package com.reservation.service

import com.reservation.exception.NotFoundException
import com.reservation.exception.ReservationException
import com.reservation.helper.TestHelper
import com.reservation.helper.TestHelper.any
import com.reservation.helper.TestHelper.getMockReservation
import com.reservation.repository.ReservationRepository
import org.assertj.core.api.Assertions
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import org.mockito.Mock
import org.mockito.Mockito.`when`
import org.mockito.junit.MockitoJUnitRunner
import org.springframework.data.repository.findByIdOrNull
import java.util.*

@RunWith(MockitoJUnitRunner::class)
class ReservationServiceSpec {

    @Mock
    private lateinit var reservationRepository: ReservationRepository
    private lateinit var reservationService: ReservationService

    @Before
    fun setup() {
        reservationService = ReservationService(reservationRepository)
    }

    @Test
    fun test_getReservationById_when_success() {
        `when`(reservationRepository.findById(any())).thenReturn(Optional.of(getMockReservation()))

        val actual = reservationService.getReservationById("mock id")

        Assertions.assertThat(actual.reservationId).isEqualTo("mock Id")
        Assertions.assertThat(actual.restaurantName).isEqualTo("mock name")
        Assertions.assertThat(actual.date).isEqualTo("mock date")
        Assertions.assertThat(actual.numberOfGuest).isEqualTo(5)
        Assertions.assertThat(actual.numberOfTable).isEqualTo(2)
        Assertions.assertThat(actual.active).isEqualTo(true)
    }

    @Test(expected = NotFoundException::class)
    fun test_getReservationById_when_not_found() {
        `when`(reservationRepository.findById(any())).thenReturn(Optional.ofNullable(null))
        reservationService.getReservationById("mock id")
    }

    @Test
    fun test_createReservation_when_success() {
        `when`(reservationRepository.save(any())).thenReturn(getMockReservation())

        val actual = reservationService.createReservation(
            TestHelper.getReservationRequestDTO(),
            5,
            TestHelper.getMockRestaurant()
        )

        Assertions.assertThat(actual.reservationId).isEqualTo("mock Id")
        Assertions.assertThat(actual.restaurantName).isEqualTo("mock name")
        Assertions.assertThat(actual.date).isEqualTo("mock date")
        Assertions.assertThat(actual.numberOfGuest).isEqualTo(5)
        Assertions.assertThat(actual.numberOfTable).isEqualTo(2)
        Assertions.assertThat(actual.active).isEqualTo(true)
    }

    @Test(expected = ReservationException::class)
    fun test_createReservation_when_table_not_enough() {
        reservationService.createReservation(TestHelper.getReservationRequestDTO(), 0, TestHelper.getMockRestaurant())
    }

    @Test
    fun test_cancelReservation_when_success() {
        val mockReservation = getMockReservation()
        mockReservation.active = false
        `when`(reservationRepository.save(any())).thenReturn(mockReservation)

        val actual = reservationService.cancelReservation(getMockReservation())

        Assertions.assertThat(actual.reservationId).isEqualTo("mock Id")
        Assertions.assertThat(actual.restaurantName).isEqualTo("mock name")
        Assertions.assertThat(actual.date).isEqualTo("mock date")
        Assertions.assertThat(actual.numberOfGuest).isEqualTo(5)
        Assertions.assertThat(actual.numberOfTable).isEqualTo(2)
        Assertions.assertThat(actual.active).isEqualTo(false)
    }

    @Test(expected = ReservationException::class)
    fun test_cancelReservation_when_reservation_already_cancelled() {
        val mockReservation = getMockReservation()
        mockReservation.active = false

        reservationService.cancelReservation(mockReservation)
    }
}