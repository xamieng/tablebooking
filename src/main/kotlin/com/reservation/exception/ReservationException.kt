package com.reservation.exception

import org.springframework.http.HttpStatus
import org.springframework.web.bind.annotation.ResponseStatus

@ResponseStatus(code = HttpStatus.INTERNAL_SERVER_ERROR, reason = "Reservation exception")
class ReservationException(message: String) : Exception(message)
