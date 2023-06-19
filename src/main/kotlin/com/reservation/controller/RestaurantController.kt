package com.reservation.controller

import com.reservation.assembler.RestaurantAssembler
import com.reservation.request.RestaurantRequestDTO
import com.reservation.response.RestaurantResponseDTO
import com.reservation.service.RestaurantService
import org.slf4j.LoggerFactory
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.*
import javax.validation.Valid

@RestController
@RequestMapping("/restaurant")
class RestaurantController(
    private val restaurantService: RestaurantService,
    private val restaurantAssembler: RestaurantAssembler
) {

    private val logger = LoggerFactory.getLogger(RestaurantController::class.java)

    @PostMapping("/initialize")
    fun initialize(@Valid @RequestBody dto: RestaurantRequestDTO): ResponseEntity<RestaurantResponseDTO> {
        logger.info("Initializing tables: $dto")
        val updatedRestaurant = restaurantService.initialize(dto)
        return ResponseEntity.ok(restaurantAssembler.assembleDTO(updatedRestaurant))
    }

    @GetMapping("/{name}/{date}")
    fun getRestaurant(@PathVariable name: String, @PathVariable date: String): ResponseEntity<RestaurantResponseDTO> {
        logger.info("getRestaurant: name: $name, date: $date")
        val restaurant = restaurantService.getRestaurantByKey(name, date)
        return ResponseEntity.ok(restaurantAssembler.assembleDTO(restaurant))
    }
}