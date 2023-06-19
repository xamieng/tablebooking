package com.reservation.controller

import com.reservation.assembler.ReservationAssembler
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
        val restaurant = restaurantService.getRestaurantByName(dto.restaurant)
        val reservation = reservationService.createReservation(dto, restaurant.numberOfTable)
        restaurantService.decreaseTable(restaurant, reservation.numberOfTable)
        return ResponseEntity.ok(reservationAssembler.assembleDTO(reservation))
    }

    @GetMapping("/{restaurantName}")
    fun getReservations(@PathVariable restaurantName: String): ResponseEntity<List<ReservationResponseDTO>> {
        logger.info("getReservation: Restaurant: $restaurantName")
        val reservations = reservationService.getReservationByRestaurant(restaurantName)
        return ResponseEntity.ok(reservations.map { reservationAssembler.assembleDTO(it) })
    }

    @DeleteMapping("/cancel/{restaurantName}/{id}")
    fun cancel(@PathVariable restaurantName: String, @PathVariable id: String): ResponseEntity<ReservationResponseDTO> {
        logger.info("cancel: restaurant: $restaurantName, id: $id")
        val restaurant = restaurantService.getRestaurantByName(restaurantName)
        val reservation = reservationService.getReservationById(id)
        restaurantService.increaseTable(restaurant, reservation.numberOfTable)
        val cancelledReservation = reservationService.cancelReservation(reservation)
        return ResponseEntity.ok(reservationAssembler.assembleDTO(cancelledReservation))
    }
}