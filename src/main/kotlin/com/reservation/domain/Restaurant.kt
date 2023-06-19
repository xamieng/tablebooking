package com.reservation.domain

import org.springframework.data.annotation.Id

class Restaurant {
    @Id
    var name: String? = null
    var initialized: Boolean = false
    var numberOfTable: Int = 0
    override fun toString() = "Restaurant = { name = $name, isInitialized = $initialized, numberOfTable: $numberOfTable }"
}