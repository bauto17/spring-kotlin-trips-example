package com.miaguila.trips.controllers

import com.miaguila.trips.entities.Trip
import com.miaguila.trips.entities.TripSearchParams
import com.miaguila.trips.entities.TripSearchResponse
import com.miaguila.trips.entities.TripsMigration
import com.miaguila.trips.services.Tripservice
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.PutMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RequestParam
import org.springframework.web.bind.annotation.RestController
import javax.servlet.http.HttpServletRequest

@RestController
@RequestMapping("trips")
class TripController(
        val tripservice: Tripservice
) {

    @GetMapping
    fun searchTrips(@RequestParam reqParam: Map<String, String>,
                     request: HttpServletRequest): ResponseEntity<TripSearchResponse> {
        return ResponseEntity(tripservice.search(TripSearchParams(reqParam, request)), HttpStatus.OK)
    }

    @GetMapping("/{id}")
    fun getTrip(@PathVariable(value = "id") id: Long): ResponseEntity<Trip> {
        return ResponseEntity(tripservice.getTrip(id), HttpStatus.OK)
    }

    @PostMapping
    fun createTrip(@RequestBody trip: Trip): ResponseEntity<String> {
        tripservice.createTrip(trip)
        return ResponseEntity("", HttpStatus.OK)
    }

    @PostMapping("/migrate")
    fun migrateTrips(@RequestBody trips: TripsMigration): ResponseEntity<String> {
        tripservice.migrateTrip(trips.trips)
        return ResponseEntity("", HttpStatus.OK)
    }

    @PutMapping("/{id}")
    fun updateTrip(@PathVariable(value = "id") id: Long, @RequestBody trip: Trip): ResponseEntity<String> {
        tripservice.updateTrip(id, trip)
        return ResponseEntity("", HttpStatus.OK)
    }
}
