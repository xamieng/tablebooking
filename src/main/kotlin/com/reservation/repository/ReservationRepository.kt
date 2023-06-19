package com.reservation.repository

import com.reservation.domain.Reservation
import org.springframework.data.mongodb.repository.MongoRepository
import org.springframework.stereotype.Repository

@Repository
interface ReservationRepository : MongoRepository<Reservation, String> {
    fun findAllByRestaurant(name: String): List<Reservation>
}