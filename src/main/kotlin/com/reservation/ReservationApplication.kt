package com.reservation

import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.runApplication
import org.springframework.cache.annotation.EnableCaching


@SpringBootApplication
@EnableCaching
class ReservationApplication

fun main(args: Array<String>) {
	runApplication<ReservationApplication>(*args)
}
