-- Проверка и создание базы данных
CREATE DATABASE cinema_service;

CREATE TABLE country (
    country_id BIGSERIAL PRIMARY KEY,
    name VARCHAR(50) NOT NULL
);



CREATE TABLE actors (
                        actor_id BIGSERIAL PRIMARY KEY,
                        first_name VARCHAR(50) NOT NULL,
                        last_name VARCHAR(50) NOT NULL,
                        patronymic VARCHAR(50),
                        birth_of_date DATE,
                        country_id BIGINT,

                        CONSTRAINT fk_actor_country
                            FOREIGN KEY (country_id)
                                REFERENCES country(country_id)
                                ON DELETE SET NULL
);
