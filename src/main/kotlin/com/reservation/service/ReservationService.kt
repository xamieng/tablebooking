package com.reservation.service

import com.reservation.domain.Reservation
import com.reservation.request.ReservationRequestDTO
import com.reservation.exception.NotFoundException
import com.reservation.exception.ReservationException
import com.reservation.repository.ReservationRepository
import org.slf4j.LoggerFactory
import org.springframework.data.repository.findByIdOrNull
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional
import kotlin.math.ceil

@Service
@Transactional
class ReservationService(
    private val reservationRepository: ReservationRepository
) {

    private val logger = LoggerFactory.getLogger(ReservationService::class.java)

    @Transactional
    fun getReservationById(id: String): Reservation {
        logger.debug("getReservationById: $id")
        return reservationRepository.findByIdOrNull(id) ?: throw NotFoundException("Reservation $id not found.")
    }

    @Transactional
    fun createReservation(dto: ReservationRequestDTO, remainingTable: Int): Reservation {
        logger.debug("createReservation: $dto")
        val desireTable = ceil(dto.numberOfGuest / 4.0).toInt()
        if (remainingTable >= desireTable) {
            val reservation = Reservation()
            reservation.restaurant = dto.restaurant
            reservation.numberOfGuest = dto.numberOfGuest
            reservation.numberOfTable = desireTable
            reservation.active = true
            return reservationRepository.save(reservation)
        } else {
            throw ReservationException("Number of table not enough")
        }
    }

    @Transactional
    fun getReservationByRestaurant(name: String): List<Reservation> {
        logger.debug("getReservationByRestaurant: name: $name")
        return reservationRepository.findAllByRestaurant(name)
    }

    @Transactional
    fun cancelReservation(reservation: Reservation): Reservation {
        logger.debug("cancelReservation: $reservation")
        if (reservation.active) {
            reservation.active = false
            return reservationRepository.save(reservation)
        } else {
            throw ReservationException("Reservation ${reservation.reservationId} was cancelled already")
        }
    }
}