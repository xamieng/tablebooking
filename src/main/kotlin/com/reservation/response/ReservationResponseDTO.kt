package com.reservation.response

import javax.validation.constraints.NotNull

class ReservationResponseDTO {
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