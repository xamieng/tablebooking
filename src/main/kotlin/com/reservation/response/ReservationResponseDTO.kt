package com.reservation.response

class ReservationResponseDTO {
    var reservationId: String? = null
    var restaurantName: String? = null
    var date: String? = null
    var numberOfGuest: Int? = 0
    var numberOfTable: Int? = 0
    var active: Boolean = false
    var remainingTable: Int? = 0
}