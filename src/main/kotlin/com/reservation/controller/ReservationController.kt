package com.reservation.controller

import com.reservation.assembler.ReservationAssembler
import com.reservation.exception.ReservationException
import com.reservation.request.ReservationRequestDTO
import com.reservation.response.ReservationResponseDTO
import com.reservation.service.ReservationService
import com.reservation.service.RestaurantService
import org.slf4j.LoggerFactory
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.*

@RestController
@RequestMapping("/reservation")
class ReservationController(
    private val restaurantService: RestaurantService,
    private val reservationService: ReservationService,
    private val reservationAssembler: ReservationAssembler
) {

    private val logger = LoggerFactory.getLogger(ReservationController::class.java)

    @PostMapping("/reserve")
    fun reserve(@RequestBody dto: ReservationRequestDTO): ResponseEntity<ReservationResponseDTO> {
        logger.info("reserve: $dto")
        val restaurant = restaurantService.getRestaurantByKey(dto.restaurantName, dto.date)
        val reservation = reservationService.createReservation(dto, restaurant.numberOfTable, restaurant)
        val updatedRestaurant = restaurantService.decreaseTable(restaurant, reservation.numberOfTable)
        return ResponseEntity.ok(reservationAssembler.assembleDTO(reservation, updatedRestaurant.numberOfTable))
    }

    @DeleteMapping("/cancel/{restaurantName}/{date}/{id}")
    fun cancel(
        @PathVariable restaurantName: String,
        @PathVariable date: String,
        @PathVariable id: String
    ): ResponseEntity<ReservationResponseDTO> {
        logger.info("cancel: restaurant: $restaurantName, date: $date, id: $id")
        val reservation = reservationService.getReservationById(id)
        // validate restaurant in cancel request and reservation. Reject if they are not the same.
        if (restaurantName != reservation.restaurantName) throw ReservationException("Booking $id is not belong to $restaurantName")
        val restaurant = restaurantService.getRestaurantByKey(reservation.restaurantName, date)
        val cancelledReservation = reservationService.cancelReservation(reservation)
        val updatedRestaurant = restaurantService.increaseTable(restaurant, reservation.numberOfTable)
        return ResponseEntity.ok(
            reservationAssembler.assembleDTO(
                cancelledReservation,
                updatedRestaurant.numberOfTable
            )
        )
    }
}