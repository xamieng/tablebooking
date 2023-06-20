package com.reservation.assembler

import com.reservation.domain.Reservation
import com.reservation.response.ReservationResponseDTO
import org.springframework.stereotype.Component

@Component
class ReservationAssembler {
    fun assembleDTO(reservation: Reservation, remainingTable: Int?): ReservationResponseDTO {
        val dto = ReservationResponseDTO()
        dto.reservationId = reservation.reservationId
        dto.numberOfTable = reservation.numberOfTable
        dto.numberOfGuest = reservation.numberOfGuest
        dto.restaurantName = reservation.restaurantName
        dto.active = reservation.active
        dto.date = reservation.date
        dto.remainingTable = remainingTable
        return dto
    }
}