package com.reservation.domain

import org.springframework.data.annotation.Id
import javax.validation.constraints.NotNull

class Reservation {
    @Id
    var reservationId: String? = null

    @NotNull
    var restaurant: String? = null

    @NotNull
    var numberOfGuest: Int = 0
    var numberOfTable: Int = 0
    var active: Boolean = false

    override fun toString() =
        "Reservation = { reservationId: $reservationId, restaurant, $restaurant, numberOfGuest: $numberOfGuest, numberOfTable: $numberOfTable, active: $active }"
}