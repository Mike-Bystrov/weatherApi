--создание таблицы
CREATE TABLE weather_info (
                              id SERIAL PRIMARY KEY,
                              temperature DOUBLE PRECISION,
                              wind_speed DOUBLE PRECISION,
                              pressure INTEGER,
                              humidity INTEGER,
                              weather_condition VARCHAR(255),
                              location VARCHAR(255),
                              timestamp TIMESTAMP
);
