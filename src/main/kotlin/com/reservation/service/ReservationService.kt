package com.reservation.service

import com.reservation.domain.Reservation
import com.reservation.domain.Restaurant
import com.reservation.request.ReservationRequestDTO
import com.reservation.exception.NotFoundException
import com.reservation.exception.ReservationException
import com.reservation.repository.ReservationRepository
import org.slf4j.LoggerFactory
import org.springframework.cache.annotation.CacheEvict
import org.springframework.cache.annotation.Cacheable
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
    @Cacheable(cacheNames = ["reservation"], key = "#id")
    fun getReservationById(id: String): Reservation {
        logger.debug("getReservationById: $id")
        return reservationRepository.findByIdOrNull(id) ?: throw NotFoundException("Reservation $id not found.")
    }

    @Transactional
    fun createReservation(dto: ReservationRequestDTO, remainingTable: Int?, restaurant: Restaurant): Reservation {
        logger.debug("createReservation: dto:$dto, remainingTable:$remainingTable")
        if (restaurant.initialized != true) throw ReservationException("Restaurant ${restaurant.key} haven't been initialize yet.")

        val desireTable = ceil(dto.numberOfGuest / 4.0).toInt()
        if ((remainingTable ?: 0) >= desireTable) {
            val reservation = Reservation()
            reservation.restaurantName = dto.restaurantName
            reservation.numberOfGuest = dto.numberOfGuest
            reservation.numberOfTable = desireTable
            reservation.date = restaurant.key?.date
            reservation.active = true
            return reservationRepository.save(reservation)
        } else {
            throw ReservationException("Number of table not enough")
        }
    }

    @Transactional
    @CacheEvict(cacheNames = ["reservation"], key = "#reservation.reservationId")
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