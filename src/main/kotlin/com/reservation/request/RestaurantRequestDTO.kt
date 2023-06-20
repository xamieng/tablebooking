package com.reservation.request

import javax.validation.constraints.NotNull

class RestaurantRequestDTO {
    // accept restaurantName and date to support multiple restaurants and keep reservation history in DB.
    @NotNull var restaurantName: String? = null
    @NotNull var date: String? = null
    @NotNull var numberOfTable: Int = 0
    override fun toString() =
        "RestaurantRequestDTO = { restaurantName = $restaurantName, date:$date, numberOfTable: $numberOfTable }"
}