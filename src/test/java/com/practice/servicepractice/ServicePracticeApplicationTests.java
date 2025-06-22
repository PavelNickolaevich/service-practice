package com.practice.servicepractice;

import com.github.javafaker.Faker;

import com.practice.servicepractice.config.TestConfigReader;
import io.restassured.RestAssured;
import io.restassured.response.Response;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import java.util.Locale;
import java.util.Random;

class ServicePracticeApplicationTests {

    private static String baseUri;

    @BeforeAll
    public static void init() {
        String portFromCmd = System.getProperty("server.port");
        String hostFromCmd = System.getProperty("server.host");

        if (portFromCmd == null) {
            System.setProperty("server.port", "8080");
        }
        if (hostFromCmd == null) {
            System.setProperty("server.host", "localhost");
        }
        baseUri = TestConfigReader.getProperty("app.api.base-uri");
    }

    @Test
    void simpleTest() {
        System.out.println("Hello world!");
        System.out.println(baseUri);
        Assertions.assertEquals(4, 2 + 2);
    }


    @Test
    void checkResponseContainsCountryName() {

        System.setProperty("server.port", "9090");

        Faker faker = new Faker(new Locale("ru"), new Random());

        String countryName = faker.country().name() + " - " + faker.country().countryCode2();
        String payload = String.format("{\"name\":\"%s\"}", countryName);

        RestAssured
                .given()
                .baseUri(baseUri)
                .contentType("application/json")
                .body(payload)
                .when()
                .log().all()
                .basePath("/v1/api/saveCountry")
                .post()
                .then()
                .statusCode(202)
                .log().all();

        Response response = RestAssured.get(baseUri + "/v1/api/allCountries");

        org.assertj.core.api.Assertions.assertThat(response.body().asString()).contains(countryName);
    }
}
