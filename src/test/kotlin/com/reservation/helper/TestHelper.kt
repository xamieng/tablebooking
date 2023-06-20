package com.reservation.helper

import com.reservation.domain.Reservation
import com.reservation.domain.Restaurant
import com.reservation.domain.RestaurantKey
import com.reservation.request.ReservationRequestDTO
import com.reservation.request.RestaurantRequestDTO

object TestHelper {
    fun <T> any(): T {
        return org.mockito.ArgumentMatchers.any()
    }


    fun getMockRestaurant(): Restaurant {
        val restaurant = Restaurant()
        val key = RestaurantKey()
        key.restaurantName = "mock name"
        key.date = "mock date"

        restaurant.key = key
        restaurant.initialized = true
        restaurant.numberOfTable = 5

        return restaurant
    }

    fun getMockRestaurantRequestDTO(): RestaurantRequestDTO {
        val dto = RestaurantRequestDTO()
        dto.restaurantName = "mock name"
        dto.date = "mock date"
        dto.initialized = true
        dto.numberOfTable = 5
        return dto
    }

    fun getMockReservation(): Reservation {
        val reservation = Reservation()
        reservation.reservationId = "mock Id"
        reservation.restaurantName = "mock name"
        reservation.date = "mock date"
        reservation.numberOfGuest = 5
        reservation.numberOfTable = 2
        reservation.active = true
        return reservation
    }

    fun getReservationRequestDTO(): ReservationRequestDTO {
        val dto = ReservationRequestDTO()
        dto.restaurantName = "mock name"
        dto.date = "mock date"
        dto.numberOfGuest = 5
        return dto
    }

}