package com.practice.servicepractice.controller;

import com.practice.servicepractice.data.dto.ActorDto;
import com.practice.servicepractice.data.models.ApiResponseService;
import com.practice.servicepractice.service.ActorService;
import com.practice.servicepractice.service.ActorValidatorService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.time.Instant;
import java.util.Date;
import java.util.List;
import java.util.Map;

import static com.practice.servicepractice.topics.Topics.ACTOR_TOPIC;

@RestController
public class ActorController {

    private final ActorService actorService;
    private final KafkaTemplate<String, Object> kafkaTemplate;
    private final ActorValidatorService actorValidatorService;

    @Autowired
    public ActorController(ActorService actorService, KafkaTemplate<String, Object> kafkaTemplate, ActorValidatorService actorValidatorService) {
        this.actorService = actorService;
        this.kafkaTemplate = kafkaTemplate;
        this.actorValidatorService = actorValidatorService;
    }

    @GetMapping("/v1/api/getAllActors")
    @Operation(summary = "Получить всех актеров")
    @ApiResponse(responseCode = "200", description = "Список всех актеров")
    public List<ActorDto> getAllActors() {
        return actorService.getAllActors();

    }

    @PostMapping("/v1/api/addActor")
    @Operation(summary = "Добавить нового актера")
    @ApiResponse(responseCode = "202", description = "Запрос на добавление актера принят")
    @ApiResponse(responseCode = "400", description = "Некорректные данные актера")
    public ResponseEntity<ApiResponseService<String>> addActor(@Valid @RequestBody ActorDto actorDto) {

        actorValidatorService.validateActor(actorDto);

        ActorDto savedActor = actorService.addActor(actorDto);
        kafkaTemplate.send(ACTOR_TOPIC, "add", actorDto);

        return ResponseEntity.accepted()
                .body(new ApiResponseService<>(
                        "accepted",
                        "Запрос на добавление актера принят",
                        savedActor.firstName() + " " + savedActor.lastName()
                ));
    }
}
