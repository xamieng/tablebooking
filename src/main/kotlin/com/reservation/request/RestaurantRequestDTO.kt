package com.reservation.request

import javax.validation.constraints.NotNull

class RestaurantRequestDTO {
    @NotNull var name: String? = null
    @NotNull var initialized: Boolean = false
    @NotNull var numberOfTable: Int = 0
    override fun toString() = "RestaurantDTO = { name = $name, isInitialized = $initialized, numberOfTable: $numberOfTable }"
}