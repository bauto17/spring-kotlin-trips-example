INSERT INTO trips (created_at, updated_at, price, check_code, status, car_plate, country, city, passenger_first_name, passenger_last_name, driver_first_name, driver_last_name, driver_location, trip_start, trip_end)
VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, cast(? as json), cast(? as json), cast(? as json) );