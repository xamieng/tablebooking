package com.reservation.request

import javax.validation.constraints.NotNull

class RestaurantRequestDTO {
    @NotNull var restaurantName: String? = null
    @NotNull var date: String? = null
    @NotNull var initialized: Boolean = false
    @NotNull var numberOfTable: Int = 0
    override fun toString() =
        "RestaurantRequestDTO = { restaurantName = $restaurantName, date:$date, isInitialized = $initialized, numberOfTable: $numberOfTable }"
}