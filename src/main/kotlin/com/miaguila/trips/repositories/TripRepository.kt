package com.miaguila.trips.repositories

import com.miaguila.trips.entities.Trip
import com.miaguila.trips.entities.TripSearchParams
import java.util.*

interface TripRepository {
    fun bulkSaveTrips(trips: List<Trip>)
    fun search(searchParams: TripSearchParams, pages: Map<Int, Long>): List<Trip>
    fun getPages(params: TripSearchParams): Map<Int,Long>
    fun getByID(id: Long): Optional<Trip>
    fun save(trip: Trip)
    fun update(id: Long, trip: Trip)
}