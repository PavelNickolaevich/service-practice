package com.practice.servicepractice.controller;

import com.practice.servicepractice.data.dto.ActorDto;
import com.practice.servicepractice.service.ActorService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

import static com.practice.servicepractice.topics.Topics.ACTOR_TOPIC;

@RestController
public class ActorController {

    private final ActorService actorService;
    private final KafkaTemplate<String, Object> kafkaTemplate;

    @Autowired
    public ActorController(ActorService actorService, KafkaTemplate<String, Object> kafkaTemplate) {
        this.actorService = actorService;
        this.kafkaTemplate = kafkaTemplate;
    }

    @GetMapping("/v1/api/getAllActors")
    public List<ActorDto> getAllActors() {
        return actorService.getAllActors();
    }

    @PostMapping("/v1/api/addActor")
    public void addActor(@RequestBody ActorDto actorDto) {
        kafkaTemplate.send(ACTOR_TOPIC, "add", actorDto);
    }

}
