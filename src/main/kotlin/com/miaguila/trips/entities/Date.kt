package com.miaguila.trips.entities

import com.fasterxml.jackson.annotation.JsonIgnore
import com.fasterxml.jackson.annotation.JsonProperty
import java.time.LocalDateTime
import java.time.format.DateTimeFormatter
import java.text.SimpleDateFormat
import java.text.DateFormat





open class Date{
    @field:JsonProperty("\$date") var dateString: String? = ""
        set(value) {
            if (value != null) {
                val formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd'T'HH:mm:ss.SSSZ")
                val formatDateTime = LocalDateTime.parse(value, formatter)
                date = formatDateTime
                field = value
            }
        }
    @JsonIgnore
    var date: LocalDateTime? = null

    companion object {
        fun from(dateString: java.sql.Timestamp): Date{
            val date = Date()
            val df = SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSSZ")
            date.dateString = df.format(dateString)
            return date
        }
    }
}