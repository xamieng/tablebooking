package com.reservation.request

import javax.validation.constraints.NotNull

class ReservationRequestDTO {
    var reservationId: String? = null
    @NotNull
    var restaurant: String? = null
    @NotNull
    var numberOfGuest: Int = 0
    var numberOfTable: Int = 0
    var active: Boolean = false
    override fun toString() =
        "ReservationDTO = { reservationId: $reservationId, restaurant, $restaurant, numberOfGuest: $numberOfGuest, numberOfTable: $numberOfTable, active: $active }"
}