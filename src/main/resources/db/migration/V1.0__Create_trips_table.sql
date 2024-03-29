create TABLE trips(
    id SERIAL PRIMARY KEY NOT NULL,
    created_at TIMESTAMP NOT NULL,
    updated_at TIMESTAMP NOT NULL,
    price NUMERIC NOT NULL,
    check_code INTEGER NOT NULL,
    status TEXT NOT NULL,
    car_plate TEXT,
    country TEXT NOT NULL,
    city TEXT NOT NULL,
    passenger_id INTEGER,
    passenger_first_name TEXT,
    passenger_last_name TEXT,
    driver_id INTEGER,
    driver_first_name TEXT,
    driver_last_name TEXT,
    driver_location JSON,
    trip_start JSON NOT NULL,
    trip_end JSON NOT NULL
);