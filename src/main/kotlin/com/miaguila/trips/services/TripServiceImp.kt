package com.miaguila.trips.services

import com.miaguila.trips.entities.Page
import com.miaguila.trips.entities.Trip
import com.miaguila.trips.entities.TripSearchParams
import com.miaguila.trips.entities.TripSearchResponse
import com.miaguila.trips.exception.TripNotfFoundExeption
import com.miaguila.trips.repositories.TripRepository
import org.springframework.stereotype.Service
import java.time.LocalDateTime

@Service
class TripServiceImp(
        val tripRepository: TripRepository
): Tripservice {

    override fun updateTrip(id: Long, trip: Trip) {
        trip.updatedAt.date= LocalDateTime.now()
        tripRepository.update(id, trip)
    }

    override fun createTrip(trip: Trip) {
        val now = LocalDateTime.now()
        trip.updatedAt.date = now
        trip.createdAt.date = now
        trip.car.plate = null
        trip.driver.firstName = null
        trip.driver.lastName = null
        trip.driverLocation = null
        trip.start.date = null
        tripRepository.save(trip)
    }

    override fun search(params: TripSearchParams): TripSearchResponse {
        val pages = tripRepository.getPages(params)
        val trips = tripRepository.search(params, pages)
        return TripSearchResponse(trips, Page.new(pages, params))
    }

    override fun getTrip(id: Long): Trip {
       val trip = tripRepository.getByID(id)
        return if (trip.isPresent){
            trip.get()
        } else {
            throw TripNotfFoundExeption("trip not found")
        }
    }

    override fun migrateTrip(trips: List<Trip>) {
        tripRepository.bulkSaveTrips(trips)
    }

}