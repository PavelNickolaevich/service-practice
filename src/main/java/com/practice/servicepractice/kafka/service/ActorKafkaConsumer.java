package com.practice.servicepractice.kafka.service;

import com.practice.servicepractice.data.dto.ActorDto;
import com.practice.servicepractice.data.dto.CountryDto;
import com.practice.servicepractice.service.ActorService;
import com.practice.servicepractice.service.CountryService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Service;

@Service
public class ActorKafkaConsumer {

    private static final Logger LOG = LoggerFactory.getLogger(ActorKafkaConsumer.class);
    private final ActorService actorService;

    @Autowired
    public ActorKafkaConsumer(ActorService actorService) {
        this.actorService = actorService;
    }

    @KafkaListener(topics = "actor-events", groupId = "movie-service-group")
    public void processActorRequest(ActorDto actorDto) {
        try {
            actorService.addActor(actorDto);
            LOG.info("Aктер сохранен через Kafka: {}", actorDto.firstName() + " " + actorDto.lastName());
        } catch (Exception e) {
            LOG.error("Ошибка при обработке страны из Kafka: {}", e.getMessage());
        }
    }
}
