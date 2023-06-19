package com.reservation.repository

import com.reservation.domain.Restaurant
import org.springframework.data.mongodb.repository.MongoRepository
import org.springframework.stereotype.Repository

@Repository
interface RestaurantRepository : MongoRepository<Restaurant, String>