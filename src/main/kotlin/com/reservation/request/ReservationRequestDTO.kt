package com.reservation.request

import javax.validation.constraints.NotNull

class ReservationRequestDTO {
    @NotNull
    var restaurantName: String? = null
    @NotNull
    var date: String? = null
    @NotNull
    var numberOfGuest: Int = 0
    override fun toString() =
        "ReservationRequestDTO = { restaurant, $restaurantName, date: $date, numberOfGuest: $numberOfGuest}"
}