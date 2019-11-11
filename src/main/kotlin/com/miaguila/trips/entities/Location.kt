package com.miaguila.trips.entities

data class Location(
        val type: String = "Point",
        val coordinates: List<Double> = emptyList()
)