package com.reservation.service

import com.reservation.domain.Restaurant
import com.reservation.domain.RestaurantKey
import com.reservation.exception.BadRequestException
import com.reservation.exception.NotFoundException
import com.reservation.exception.ReservationException
import com.reservation.repository.RestaurantRepository
import com.reservation.request.RestaurantRequestDTO
import org.slf4j.LoggerFactory
import org.springframework.cache.annotation.Cacheable
import org.springframework.data.repository.findByIdOrNull
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional

@Service
@Transactional
class RestaurantService(
    private val restaurantRepository: RestaurantRepository
) {

    private val logger = LoggerFactory.getLogger(RestaurantService::class.java)

    @Transactional
    @Cacheable(cacheNames = ["restaurant"], key = "{#name, #date}")
    fun getRestaurantByKey(name: String?, date: String?): Restaurant {
        logger.debug("getRestaurantByName: $name")
        val restaurantKey = RestaurantKey()
        restaurantKey.restaurantName = name
        restaurantKey.date = date
        return restaurantRepository.findByIdOrNull(restaurantKey)
            ?: throw NotFoundException("Restaurant $name not found.")
    }

    @Transactional
    fun decreaseTable(restaurant: Restaurant, table: Int?): Restaurant {
        logger.debug("decreaseTable: restaurant: $restaurant, table: $table")
        val result = restaurant.numberOfTable?.let { it - (table ?: 0) }
        if ((result ?: 0) > 0) {
            restaurant.numberOfTable = result
            return restaurantRepository.save(restaurant)
        } else {
            throw ReservationException("Internal error: table is not enough")
        }
    }

    @Transactional
    fun increaseTable(restaurant: Restaurant, table: Int?): Restaurant {
        logger.debug("increaseTable: restaurant: $restaurant, table: $table")
        restaurant.numberOfTable = restaurant.numberOfTable?.let { it + (table ?: 0) }
        return restaurantRepository.save(restaurant)

    }

    @Transactional
    fun createRestaurant(dto: RestaurantRequestDTO): Restaurant {
        logger.debug("createRestaurant: $dto")
        val restaurant = Restaurant()
        val key = RestaurantKey()
        key.restaurantName = dto.restaurantName
        key.date = dto.date
        restaurant.key = key
        restaurant.initialized = true
        restaurant.numberOfTable = dto.numberOfTable
        return restaurantRepository.save(restaurant)
    }

    @Transactional
    fun initialize(dto: RestaurantRequestDTO): Restaurant {
        logger.debug("initialize: dto: $dto")
        val restaurantKey = RestaurantKey()
        restaurantKey.restaurantName = dto.restaurantName
        restaurantKey.date = dto.date
        val restaurant = restaurantRepository.findByIdOrNull(restaurantKey)

        return restaurant?.let {
            throw BadRequestException("Error: The table of restaurant ${restaurant.key} has been initialized already.")
        } ?: createRestaurant(dto)
    }
}