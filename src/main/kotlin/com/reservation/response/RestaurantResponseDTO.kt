package com.reservation.response

import javax.validation.constraints.NotNull

class RestaurantResponseDTO {
    @NotNull var name: String? = null
    @NotNull var initialized: Boolean = false
    @NotNull var numberOfTable: Int = 0
    override fun toString() = "RestaurantDTO = { name = $name, isInitialized = $initialized, numberOfTable: $numberOfTable }"
}