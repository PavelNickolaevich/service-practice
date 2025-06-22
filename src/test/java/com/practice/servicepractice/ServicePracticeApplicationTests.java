package com.practice.servicepractice;

import com.github.javafaker.Faker;

import com.practice.servicepractice.config.ApiProperties;
import io.restassured.RestAssured;
import io.restassured.response.Response;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.Locale;
import java.util.Random;

class ServicePracticeApplicationTests {

    @Value("${app.api.base-uri}") // Инжектит значение из конфига
    private String apiBaseUri;

    @Autowired
    private ApiProperties apiProperties;

    private static final String BASE_URI = "http://service-practice:8080";


    @Test
    void simpleTest() {
        System.out.println("Hello world!");
        System.out.println(apiBaseUri);
        Assertions.assertEquals(4, 2 + 2);
    }

    @Test
    void checkResponseContainsCountryName() {
        Faker faker = new Faker(new Locale("ru"), new Random());

        String countryName = faker.country().name() + " - " + faker.country().countryCode2();
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
