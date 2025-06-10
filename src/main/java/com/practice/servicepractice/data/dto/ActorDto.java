package com.practice.servicepractice.data.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.practice.servicepractice.data.entity.ActorEntity;
import jakarta.annotation.Nonnull;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotEmpty;

import java.time.ZoneId;
import java.util.Date;

public record ActorDto(
        @JsonProperty("actorId")
        Long actorId,
        @JsonProperty("firstName")
        @NotBlank
        @NotEmpty
        String firstName,
        @NotBlank
        @NotEmpty
        @JsonProperty("lastName")
        String lastName,
        @JsonProperty("patronymic")
        String patronymic,
        @NotBlank
        @NotEmpty
        @JsonProperty("birthOfDate")
        Date birthOfDate,
        @NotBlank
        @NotEmpty
        @JsonProperty("countryId")
        Long countryId
) {
    public static @Nonnull
    ActorDto fromEntity(@Nonnull ActorEntity entity) {
        return new ActorDto(
                entity.getActorId(),
                entity.getFirstName(),
                entity.getLastName(),
                entity.getPatronymic(),
                entity.getBirthOfDate(),
                entity.getCountry().getCountryId()
        );
    }
}