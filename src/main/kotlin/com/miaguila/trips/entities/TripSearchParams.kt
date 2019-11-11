package com.miaguila.trips.entities

import com.miaguila.trips.exception.SearchParamExeption
import javax.servlet.http.HttpServletRequest

class TripSearchParams(params: Map<String, String>, request: HttpServletRequest) {

    var index: Map<Int, Long>
    val page: Int
    val country: String
    val city: String
    val passengerId: Long
    val driverId: Long
    val isValidPage: Boolean
    val url: String

    init {
        try{
            val page = (params["page"] ?: "0").toInt()
            this.index = readIndex(params["index"] ?: "")
            this.country = params["country"] ?: ""
            this.city = params["city"] ?: ""
            this.passengerId = (params["passenger_id"] ?: "0").toLong()
            this.driverId = (params["driver_id"] ?: "0").toLong()
            this.isValidPage = page > 1 && index.containsKey(page)
            this.page = if(isValidPage){
                page
            } else {
                1
            }
            this.url = request.requestURL.toString()
        } catch (e: Exception){
            throw SearchParamExeption("incorrect search params")
        }
    }

    private fun readIndex(s: String): Map<Int,Long> {
        return try {
            if(!"".equals(s)){
                s.split(",")
                        .map { it.split("_") }
                        .associate { it[0].toInt() to it[1].toLong() }
            } else {
                emptyMap()
            }
        } catch (e: Exception) {
            emptyMap()
        }
    }

    fun getQuery(): String {
        var validations = ""
        if("" != this.country){
            validations+=" AND country = :country "
        }
        if("" != this.city){
            validations+=" AND city = :city "
        }
        if(0L != this.passengerId){
            validations+=" AND passenger_id = :passenger_id "
        }
        if(0L != this.driverId){
            validations+=" AND driver_id = :driver_id "
        }
        return validations
    }

    fun getGetParams(): String {
        var query = ""
        if("" != this.country){
            query+="&country=${this.country}"
        }
        if("" != this.city){
            query+="&city=${this.city}"
        }
        if(0L != this.passengerId){
            query+="&passenger_id=${passengerId}"
        }
        if(0L != this.driverId){
            query+="&driver_id=${this.driverId}"
        }
        return query
    }
}

class TripSearchResponse(val trips: List<Trip>, val pages: List<Page>)