package com.reservation.exception

import org.springframework.http.HttpStatus
import org.springframework.web.bind.annotation.ResponseStatus

@ResponseStatus(code = HttpStatus.BAD_REQUEST, reason = "Not found exception")
class NotFoundException(message: String) : Exception(message)
