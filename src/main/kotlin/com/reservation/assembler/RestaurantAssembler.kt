package com.reservation.assembler

import com.reservation.domain.Restaurant
import com.reservation.response.RestaurantResponseDTO
import org.springframework.stereotype.Component

@Component
class RestaurantAssembler {
    fun assembleDTO(restaurant: Restaurant): RestaurantResponseDTO {
        val dto = RestaurantResponseDTO()
        dto.restaurantName = restaurant.key?.restaurantName
        dto.date = restaurant.key?.date
        dto.initialized = restaurant.initialized
        dto.numberOfTable = restaurant.numberOfTable
        return dto
    }
}