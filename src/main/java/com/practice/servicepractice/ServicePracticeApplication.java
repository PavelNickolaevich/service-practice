package com.practice.servicepractice;

import com.practice.servicepractice.config.ApiProperties;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

@SpringBootApplication
@EnableConfigurationProperties(ApiProperties.class)
public class ServicePracticeApplication {

    public static void main(String[] args) {
        SpringApplication.run(ServicePracticeApplication.class, args);
    }

}
