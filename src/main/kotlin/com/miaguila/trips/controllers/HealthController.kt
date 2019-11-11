package com.miaguila.trips.controllers

import com.miaguila.trips.entities.TripMoment
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController

@RestController
@RequestMapping("health")
class HealthController {

    @GetMapping()
    fun healthCheck(): ResponseEntity<String> {
        return ResponseEntity("Up", HttpStatus.OK)
    }
}
