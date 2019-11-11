package com.miaguila.trips.entities

import com.fasterxml.jackson.annotation.JsonProperty
import com.fasterxml.jackson.databind.ObjectMapper
import java.sql.ResultSet

data class Trip(val id: Long?,
                val start: TripMoment,
                val end: TripMoment,
                val country: Country,
                val city: City,
                val passenger: Passenger,
                val driver: Driver,
                val car: Car,
                val status: State,
                val checkCode: Int,
                val createdAt: Date,
                val updatedAt: Date,
                val price: Long,
                @field:JsonProperty("driver_location") var driverLocation: Location?){

    companion object {
        fun fromResultSet(rs: ResultSet): Trip{
            return Trip(
                    rs.getLong("id"),
                    map.readValue(rs.getString("trip_start"), TripMoment::class.java),
                    map.readValue(rs.getString("trip_end"), TripMoment::class.java),
                    Country(rs.getString("country")),
                    City(rs.getString("city")),
                    Passenger(rs.getString("passenger_first_name"),rs.getString("passenger_last_name")),
                    Driver(rs.getString("driver_first_name"),rs.getString("driver_last_name")),
                    Car(rs.getString("car_plate")),
                    State.valueOf(rs.getString("status")),
                    rs.getInt("check_code"),
                    Date.from(rs.getTimestamp("created_at")),
                    Date.from(rs.getTimestamp("updated_at")),
                    rs.getLong("price"),
                    map.readValue(rs.getString("driver_location"), Location::class.java))
        }

        val map = ObjectMapper()
    }
}

data class TripMoment(
        var date: Date? = null,
        @field:JsonProperty("pickup_address") val pickupAddress: String = "",
        @field:JsonProperty("pickup_location") val pickupLocation: Location = Location())
