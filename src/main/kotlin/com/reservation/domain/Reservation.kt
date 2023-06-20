package com.reservation.domain

import org.springframework.data.annotation.Id

class Reservation {
    @Id
    var reservationId: String? = null
    var restaurantName: String? = null
    var date: String? = null
    var numberOfGuest: Int = 0
    var numberOfTable: Int = 0
    var active: Boolean = false

    override fun toString() =
        "Reservation = { reservationId: $reservationId, restaurantName, $restaurantName, date: $date, numberOfGuest: $numberOfGuest, numberOfTable: $numberOfTable, active: $active }"
}