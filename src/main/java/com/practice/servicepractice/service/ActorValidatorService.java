package com.practice.servicepractice.service;

import com.practice.servicepractice.data.dto.ActorDto;
import com.practice.servicepractice.exceptions.ValidationActorException;
import org.springframework.stereotype.Service;

import java.util.Date;

@Service
public class ActorValidatorService {

    public void validateActor(ActorDto actorDto) {
        if (actorDto.firstName() == null || actorDto.firstName().isBlank()) {
            throw new ValidationActorException("First name is required");
        }
        if (actorDto.lastName() == null || actorDto.lastName().isBlank()) {
            throw new ValidationActorException("Last name is required");
        }
        if (actorDto.birthOfDate() != null && actorDto.birthOfDate().after(new Date())) {
            throw new ValidationActorException("Birth date cannot be in the future");
        }
    }
}
