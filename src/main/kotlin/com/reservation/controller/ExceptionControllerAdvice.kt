package com.reservation.controller

import com.reservation.response.ErrorDTO
import com.reservation.exception.BadRequestException
import com.reservation.exception.NotFoundException
import com.reservation.exception.ReservationException
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.MethodArgumentNotValidException
import org.springframework.web.bind.annotation.ControllerAdvice
import org.springframework.web.bind.annotation.ExceptionHandler

@ControllerAdvice
class ExceptionControllerAdvice {
    @ExceptionHandler
    fun handleBadRequestException(ex: BadRequestException): ResponseEntity<ErrorDTO> {
        val errorMessage = ErrorDTO(
            HttpStatus.BAD_REQUEST.value(),
            ex.message
        )
        return ResponseEntity(errorMessage, HttpStatus.BAD_REQUEST)
    }

    @ExceptionHandler
    fun handleMethodArgumentNotValidException(ex: MethodArgumentNotValidException): ResponseEntity<ErrorDTO> {
        val errorMessage = ErrorDTO(
            HttpStatus.BAD_REQUEST.value(),
            ex.message
        )
        return ResponseEntity(errorMessage, HttpStatus.BAD_REQUEST)
    }

    @ExceptionHandler
    fun handleNotFoundException(ex: NotFoundException): ResponseEntity<ErrorDTO> {
        val errorMessage = ErrorDTO(
            HttpStatus.NOT_FOUND.value(),
            ex.message
        )
        return ResponseEntity(errorMessage, HttpStatus.BAD_REQUEST)
    }

    @ExceptionHandler
    fun handleReservationException(ex: ReservationException): ResponseEntity<ErrorDTO> {
        val errorMessage = ErrorDTO(
            HttpStatus.INTERNAL_SERVER_ERROR.value(),
            ex.message
        )
        return ResponseEntity(errorMessage, HttpStatus.BAD_REQUEST)
    }
}