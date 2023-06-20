package com.reservation.response

import javax.validation.constraints.NotNull

class RestaurantResponseDTO {
    var restaurantName: String? = null
    var date: String? = null
    var initialized: Boolean? = null
    var numberOfTable: Int? = null
}