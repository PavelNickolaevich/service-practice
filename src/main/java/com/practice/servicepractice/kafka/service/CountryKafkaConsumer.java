package com.practice.servicepractice.kafka.service;

import com.practice.servicepractice.data.dto.CountryDto;
import com.practice.servicepractice.service.CountryService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Service;

@Service
public class CountryKafkaConsumer {

    private static final Logger LOG = LoggerFactory.getLogger(CountryKafkaConsumer.class);
    private final CountryService countryService;

    @Autowired
    public CountryKafkaConsumer(CountryService countryService) {
        this.countryService = countryService;
    }

    @KafkaListener(topics = "country-events", groupId = "movie-service-group")
    public void processCountryRequest(CountryDto countryDto) {
        try {
            countryService.addCountry(countryDto);
            LOG.info("Страна сохранена через Kafka: {}", countryDto.name());
        } catch (Exception e) {
            LOG.error("Ошибка при обработке страны из Kafka: {}", e.getMessage());
        }
    }
}
