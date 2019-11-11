package com.miaguila.trips.entities

import com.fasterxml.jackson.annotation.JsonProperty

open class Person(
        @field:JsonProperty("first_name") var firstName: String?,
        @field:JsonProperty("last_name") var lastName: String?
)

class Passenger(firstName: String?, lastName: String?): Person(firstName, lastName)

class Driver(firstName: String?, lastName: String?): Person(firstName, lastName)