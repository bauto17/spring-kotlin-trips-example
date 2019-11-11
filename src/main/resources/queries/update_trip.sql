UPDATE trips
SET created_at = ?,
updated_at = ?,
price = ?,
check_code = ?,
status = ?,
car_plate = ?,
country = ?,
city = ?,
passenger_first_name = ?,
passenger_last_name = ?,
driver_first_name = ?,
driver_last_name = ?,
driver_location = cast(? as json),
trip_start = cast(? as json),
trip_end = cast(? as json)
WHERE id = ?;