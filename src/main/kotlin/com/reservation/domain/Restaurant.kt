package com.reservation.domain

import org.springframework.data.annotation.Id
import org.springframework.data.annotation.Version

class Restaurant {
    @Id
    var key: RestaurantKey? = null
    var initialized: Boolean? = false
    var numberOfTable: Int? = 0
    @Version var version: Long = 0 // Optimistic locking

    override fun toString() = "Restaurant = { key = $key, isInitialized = $initialized, numberOfTable: $numberOfTable }"
}

class RestaurantKey {
    var date: String? = null
    var restaurantName: String? = null
    override fun toString() = "RestaurantKey = { date = $date, restaurantName = $restaurantName}"
}