package com.miaguila.trips.exception

import com.fasterxml.jackson.annotation.JsonInclude
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.ControllerAdvice
import org.springframework.web.bind.annotation.ExceptionHandler
import java.util.*

/**
 * provides a midleware to transform controller's exceptions
 */
@ControllerAdvice
class ExceptionHandler {

    @ExceptionHandler(SearchParamExeption::class)
    private fun handleResponse(ex: SearchParamExeption): ResponseEntity<TripsError> {
        val error = TripsError(HttpStatus.BAD_REQUEST, ex.message?:"")

        return ResponseEntity(error, HttpStatus.BAD_REQUEST)
    }

    @ExceptionHandler(TripNotfFoundExeption::class)
    private fun tripNotFound(ex: TripNotfFoundExeption): ResponseEntity<TripsError> {
        val error = TripsError(HttpStatus.BAD_REQUEST, ex.message?:"")

        return ResponseEntity(error, HttpStatus.BAD_REQUEST)
    }

    private inner class TripsError(status: HttpStatus, msg: String) {
        val status: Int
        val error: String

        init {
            this.error = msg
            this.status = status.value()
        }
    }
}
