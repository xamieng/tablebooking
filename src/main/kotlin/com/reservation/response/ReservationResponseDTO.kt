package com.reservation.response

class ReservationResponseDTO {
    var reservationId: String? = null
    var restaurantName: String? = null
    var date: String? = null
    var numberOfGuest: Int? = 0
    var numberOfTable: Int? = 0
    var active: Boolean = false
    var remainingTable: Int? = 0
    override fun toString() =
        "ReservationResponseDTO = { reservationId: $reservationId, restaurantName: $restaurantName, date: $date, numberOfGuest: $numberOfGuest, numberOfTable: $numberOfTable, active: $active, remainingTable:$remainingTable }"
}