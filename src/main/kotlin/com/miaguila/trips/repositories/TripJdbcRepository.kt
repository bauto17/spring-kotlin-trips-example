package com.miaguila.trips.repositories

import com.fasterxml.jackson.databind.ObjectMapper
import com.miaguila.trips.entities.Trip
import com.miaguila.trips.entities.TripSearchParams
import com.miaguila.trips.utils.CustomFileManager
import org.springframework.jdbc.core.ResultSetExtractor
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate
import org.springframework.stereotype.Repository
import java.sql.Timestamp
import java.sql.Types
import java.util.*
import javax.sql.DataSource

@Repository

class TripJdbcRepository(val fileManager: CustomFileManager,
                         ds: DataSource,
                         val template: NamedParameterJdbcTemplate): TripRepository{

    override fun update(id: Long, trip: Trip) {
        template.jdbcOperations.update(
                getQueryFromFile("update_trip")
        ) { ps ->
            ps.setTimestamp(1, Timestamp.valueOf(trip.createdAt.date))
            ps.setTimestamp(2, Timestamp.valueOf(trip.updatedAt.date))
            ps.setLong(3, trip.price)
            ps.setInt(4, trip.checkCode)
            ps.setString(5, trip.status.name)
            ps.setString(6, trip.car.plate)
            ps.setString(7, trip.country.name)
            ps.setString(8, trip.city.name)
            ps.setString(9, trip.passenger.firstName)
            ps.setString(10, trip.passenger.lastName)
            ps.setString(11, trip.driver.firstName)
            ps.setString(12, trip.driver.lastName)
            ps.setObject(13, mapper.writeValueAsString(trip.driverLocation), Types.OTHER)
            ps.setObject(14, mapper.writeValueAsString(trip.start), Types.OTHER)
            ps.setObject(15, mapper.writeValueAsString(trip.end), Types.OTHER)
            ps.setLong(16, id)
        }
    }

    override fun save(trip: Trip) {
        template.jdbcOperations.update(
                getQueryFromFile("batch_insert_trips")
        ) { ps ->
            ps.setTimestamp(1, Timestamp.valueOf(trip.createdAt.date))
            ps.setTimestamp(2, Timestamp.valueOf(trip.updatedAt.date))
            ps.setLong(3, trip.price)
            ps.setInt(4, trip.checkCode)
            ps.setString(5, trip.status.name)
            ps.setString(6, trip.car.plate)
            ps.setString(7, trip.country.name)
            ps.setString(8, trip.city.name)
            ps.setString(9, trip.passenger.firstName)
            ps.setString(10, trip.passenger.lastName)
            ps.setString(11, trip.driver.firstName)
            ps.setString(12, trip.driver.lastName)
            ps.setObject(13, mapper.writeValueAsString(trip.driverLocation), Types.OTHER)
            ps.setObject(14, mapper.writeValueAsString(trip.start), Types.OTHER)
            ps.setObject(15, mapper.writeValueAsString(trip.end), Types.OTHER)
        }
    }

    override fun getByID(id: Long): Optional<Trip> {
        val trips = template.query(
                getQueryFromFile("get_trip_by_id"),
                hashMapOf("id" to id),
                ResultSetExtractor { resultSet ->
                    arrayListOf<Trip>().apply {
                        while (resultSet.next()) {
                            this.add(Trip.fromResultSet(resultSet))
                        }
                    }
                }
        ) ?: arrayListOf<Trip>()

        return trips.stream().findFirst()
    }

    override fun getPages(params: TripSearchParams): Map<Int, Long> {
        val query = getQueryFromFile("get_pages").replace("PARAMS", params.getQuery())
        val lastPage = params.index.getOrDefault(params.page, 0L)
        val pages = template.query(
                query,
                hashMapOf(
                        "driver_id" to params.driverId,
                        "passenger_id" to params.passengerId,
                        "city" to params.city,
                        "country" to params.country,
                        "last_page" to lastPage
                ),
                ResultSetExtractor { resultSet ->
                    hashMapOf<Int, Long>().apply {
                        this.put(1,0L)
                        while (resultSet.next()) {
                            this.put(params.page+resultSet.getInt("page_number"),resultSet.getLong("id"))
                        }
                        this.put(params.page, lastPage)
                    }
                }
        ) ?: emptyMap<Int, Long>()

        return pages
    }

    override fun search(searchParams: TripSearchParams, pages: Map<Int, Long>): List<Trip> {
        val trips = template.query(
                getQueryFromFile("search_trips").replace("PARAMS", searchParams.getQuery()),
                hashMapOf(
                        "base" to pages.getOrDefault(searchParams.page, 0),
                        "page_size" to 100,
                        "driver_id" to searchParams.driverId,
                        "passenger_id" to searchParams.passengerId,
                        "city" to searchParams.city,
                        "country" to searchParams.country
                ),
                ResultSetExtractor { resultSet ->
                    arrayListOf<Trip>().apply {
                        while (resultSet.next()) {
                            this.add(Trip.fromResultSet(resultSet))
                        }
                    }
                }
        ) ?: emptyList<Trip>()

        //logger.info("return ${payloads.size} brand payloads")

        return trips
    }

    override fun bulkSaveTrips(trips: List<Trip>) {
        template.jdbcOperations.batchUpdate(
                getQueryFromFile("batch_insert_trips"),
                trips,
                trips.size
        ) { ps, trip ->
            ps.setTimestamp(1, Timestamp.valueOf(trip.createdAt.date))
            ps.setTimestamp(2, Timestamp.valueOf(trip.updatedAt.date))
            ps.setLong(3, trip.price)
            ps.setInt(4, trip.checkCode)
            ps.setString(5, trip.status.name)
            ps.setString(6, trip.car.plate)
            ps.setString(7, trip.country.name)
            ps.setString(8, trip.city.name)
            ps.setString(9, trip.passenger.firstName)
            ps.setString(10, trip.passenger.lastName)
            ps.setString(11, trip.driver.firstName)
            ps.setString(12, trip.driver.lastName)
            ps.setObject(13, mapper.writeValueAsString(trip.driverLocation), Types.OTHER)
            ps.setObject(14, mapper.writeValueAsString(trip.start), Types.OTHER)
            ps.setObject(15, mapper.writeValueAsString(trip.end), Types.OTHER)
        }
    }

    fun getQueryFromFile(fileName: String) = fileManager.resolveSQL("classpath:queries/$fileName.sql")

    companion object {
        val mapper = ObjectMapper()
    }
}