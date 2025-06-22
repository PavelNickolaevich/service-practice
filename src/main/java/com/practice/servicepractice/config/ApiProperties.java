package com.practice.servicepractice.config;

import lombok.Data;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;
import org.springframework.stereotype.Component;

@Component
@ConfigurationProperties(prefix = "app")
public class ApiProperties {
    private Api api = new Api();

    @Value("${app.api.base-uri}") // Инжектит значение из конфига
    private String apiBaseUri;


    public static class Api {
        private String baseUri;

        // Геттеры и сеттеры
        public String getBaseUri() {
            return baseUri;
        }

        public void setBaseUri(String baseUri) {
            this.baseUri = baseUri;
        }
    }

    public Api getApi() {
        return api;
    }

    public void setApi(Api api) {
        this.api = api;
    }
}