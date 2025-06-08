package com.practice.servicepractice.service;

import com.practice.servicepractice.data.dto.ActorDto;
import com.practice.servicepractice.data.entity.ActorEntity;
import com.practice.servicepractice.data.entity.CountryEntity;
import com.practice.servicepractice.data.repository.ActorRepository;
import com.practice.servicepractice.data.repository.CountryRepository;
import jakarta.annotation.Nonnull;

import jakarta.persistence.EntityNotFoundException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.util.Date;
import java.util.List;
import java.util.Objects;

@Service
public class ActorService {

    private static final Logger LOG = LoggerFactory.getLogger(ActorService.class);
    private final ActorRepository actorRepository;
    private final CountryRepository countryRepository;

    @Autowired
    public ActorService(ActorRepository actorRepository, CountryRepository countryRepository) {
        this.actorRepository = actorRepository;
        this.countryRepository = countryRepository;
    }

    @Transactional(readOnly = true)
    public @Nonnull
    List<ActorDto> getAllActors() {
        return actorRepository.findAll()
                .stream()
                .map(ActorDto::fromEntity)
                .toList();
    }


    @Transactional
    public @Nonnull ActorDto addActor(@Nonnull ActorDto actorDto) {
        Objects.requireNonNull(actorDto, "Actor DTO cannot be null");
        LOG.info("Adding new actor: {}", actorDto);

        validateActor(actorDto);

        CountryEntity country = null;
        if (actorDto.countryId() != null) {
            country = countryRepository.findById(Math.toIntExact(actorDto.countryId()))
                    .orElseThrow(() -> new EntityNotFoundException(
                            "Country not found with id: " + actorDto.countryId()));
        }

        ActorEntity newActor = new ActorEntity(
                actorDto.firstName(),
                actorDto.lastName(),
                actorDto.patronymic(),
                actorDto.birthOfDate(),
                country
        );

        ActorEntity savedActor = actorRepository.save(newActor);
        LOG.info("Successfully added actor with ID: {}", savedActor.getActorId());

        return ActorDto.fromEntity(savedActor);
    }

    private void validateActor(ActorDto actorDto) {
        if (actorDto.firstName() == null || actorDto.firstName().isBlank()) {
            throw new IllegalArgumentException("First name is required");
        }
        if (actorDto.lastName() == null || actorDto.lastName().isBlank()) {
            throw new IllegalArgumentException("Last name is required");
        }
        if (actorDto.birthOfDate() != null && actorDto.birthOfDate().after(new Date())) {
            throw new IllegalArgumentException("Birth date cannot be in the future");
        }
    }
}
