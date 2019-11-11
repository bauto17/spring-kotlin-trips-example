package com.miaguila.trips

import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.runApplication

@SpringBootApplication
class TripsApplication

fun main(args: Array<String>) {
	runApplication<TripsApplication>(*args)
}
