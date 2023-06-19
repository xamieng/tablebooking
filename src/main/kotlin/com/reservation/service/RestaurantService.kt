package com.reservation.service

import com.reservation.domain.Restaurant
import com.reservation.request.RestaurantRequestDTO
import com.reservation.exception.BadRequestException
import com.reservation.exception.NotFoundException
import com.reservation.exception.ReservationException
import com.reservation.repository.RestaurantRepository
import org.slf4j.LoggerFactory
import org.springframework.cache.annotation.CacheEvict
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
    @Cacheable(cacheNames = ["restaurant"], key = "#name")
    fun getRestaurantByName(name: String?): Restaurant {
        logger.debug("getRestaurantByName: $name")
        return restaurantRepository.findByIdOrNull(name) ?: throw NotFoundException("Restaurant $name not found.")
    }

    @Transactional
    fun getRestaurants(): List<Restaurant> {
        logger.debug("getRestaurants")
        return restaurantRepository.findAll()
    }

    @Transactional
    fun decreaseTable(restaurant: Restaurant, table: Int?): Restaurant {
        logger.debug("decreaseTable: restaurant: $restaurant, table: $table")
        val result = restaurant.numberOfTable - (table ?: 0)
        if (result > 0) {
            restaurant.numberOfTable = result
            return restaurantRepository.save(restaurant)
        } else {
            throw ReservationException("Internal error: table is not enough")
        }
    }

    @Transactional
    fun increaseTable(restaurant: Restaurant, table: Int?): Restaurant {
        logger.debug("increaseTable: restaurant: $restaurant, table: $table")
        restaurant.numberOfTable = restaurant.numberOfTable + (table ?: 0)
        return restaurantRepository.save(restaurant)

    }

    @Transactional
    fun createRestaurant(dto: RestaurantRequestDTO): Restaurant {
        logger.debug("createRestaurant: $dto")
        val restaurant = Restaurant()
        restaurant.name = dto.name
        restaurant.initialized = dto.initialized
        restaurant.numberOfTable = dto.numberOfTable
        return restaurantRepository.save(restaurant)
    }

    @Transactional
    @CacheEvict(cacheNames = ["restaurant"], key = "#dto.name")
    fun initialize(dto: RestaurantRequestDTO): Restaurant {
        logger.debug("initialize: dto: $dto")
        val restaurant = dto.name?.let { restaurantRepository.findByIdOrNull(it) }
        return restaurant?.let {
            if (!it.initialized) {
                it.initialized = dto.initialized
                restaurantRepository.save(it)
            } else {
                throw BadRequestException("Error: The table of restaurant ${restaurant.name} has been initialized already.")
            }
        } ?: createRestaurant(dto)
    }
}