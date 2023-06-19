package com.reservation.assembler

import com.reservation.domain.Reservation
import com.reservation.request.ReservationRequestDTO
import com.reservation.response.ReservationResponseDTO
import org.springframework.stereotype.Component

@Component
class ReservationAssembler {
    fun assembleDTO(reservation: Reservation): ReservationResponseDTO {
        val dto = ReservationResponseDTO()
        dto.reservationId = reservation.reservationId
        dto.numberOfTable = reservation.numberOfTable
        dto.numberOfGuest = reservation.numberOfGuest
        dto.restaurant = reservation.restaurant
        dto.active = reservation.active
        return dto
    }
}