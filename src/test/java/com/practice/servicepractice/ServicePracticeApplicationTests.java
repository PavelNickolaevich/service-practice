package com.practice.servicepractice;

import com.github.javafaker.Faker;

import io.restassured.RestAssured;
import io.restassured.response.Response;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.util.Locale;
import java.util.Random;


class ServicePracticeApplicationTests {

    private static final String BASE_URI = "http://localhost:8080";

    @Test
    void simpleTest() {
        Assertions.assertEquals(4, 2 + 2);
    }

    @Test
    void checkResponseContainsCountryName() {
        Faker faker = new Faker(new Locale("ru"), new Random());

        String countryName = faker.country().name();
        String payload = String.format("{\"name\":\"%s\"}", countryName);

        RestAssured
                .given()
                .baseUri(BASE_URI)
                .contentType("application/json")
                .body(payload)
                .when()
                .log().all()
                .basePath("/v1/api/saveCountry")
                .post()
                .then()
                .statusCode(202)
                .log().all();

        Response response = RestAssured.get(BASE_URI + "/v1/api/allCountries");

        org.assertj.core.api.Assertions.assertThat(response.body().asString()).contains(countryName);
    }


}
