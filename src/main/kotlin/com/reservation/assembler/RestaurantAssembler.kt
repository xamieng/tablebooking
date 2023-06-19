package com.reservation.assembler

import com.reservation.domain.Restaurant
import com.reservation.request.RestaurantRequestDTO
import com.reservation.response.RestaurantResponseDTO
import org.springframework.stereotype.Component

@Component
class RestaurantAssembler {
    fun assembleDTO(restaurant: Restaurant): RestaurantResponseDTO {
        val dto = RestaurantResponseDTO()
        dto.name = restaurant.name
        dto.initialized = restaurant.initialized
        dto.numberOfTable = restaurant.numberOfTable
        return dto
    }
}