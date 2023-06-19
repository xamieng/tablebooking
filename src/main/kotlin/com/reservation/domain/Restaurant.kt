package com.reservation.domain

import org.springframework.data.annotation.Id

class Restaurant {
    @Id
    var key: RestaurantKey? = null
    var initialized: Boolean? = false
    var numberOfTable: Int? = 0
    override fun toString() = "Restaurant = { key = $key, isInitialized = $initialized, numberOfTable: $numberOfTable }"
}

class RestaurantKey {
    var date: String? = null
    var restaurantName: String? = null
    override fun toString() = "RestaurantKey = { date = $date, restaurantName = $restaurantName}"
}