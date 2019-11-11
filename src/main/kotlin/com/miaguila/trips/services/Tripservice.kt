package com.miaguila.trips.services

import com.miaguila.trips.entities.Trip
import com.miaguila.trips.entities.TripSearchParams
import com.miaguila.trips.entities.TripSearchResponse
import com.miaguila.trips.exception.SearchParamExeption

interface Tripservice {
    fun migrateTrip(trips: List<Trip>)
    fun getTrip(id: Long): Trip
    fun createTrip(trip: Trip)
    fun updateTrip(id: Long, trip: Trip)
    fun search(params: TripSearchParams): TripSearchResponse
}