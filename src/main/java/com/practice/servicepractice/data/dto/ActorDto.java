package com.practice.servicepractice.data.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.practice.servicepractice.data.entity.ActorEntity;
import jakarta.annotation.Nonnull;

import java.time.ZoneId;
import java.util.Date;

public record ActorDto(
        @JsonProperty("firstName")
        String firstName,
        @JsonProperty("lastName")
        String lastName,
        @JsonProperty("patronymic")
        String patronymic,
        @JsonProperty("birthOfDate")
        Date birthOfDate,
        @JsonProperty("countryId")
        Long countryId
) {
    public static @Nonnull
    ActorDto fromEntity(@Nonnull ActorEntity entity) {
        return new ActorDto(
                entity.getFirstName(),
                entity.getLastName(),
                entity.getPatronymic(),
                entity.getBirthOfDate(),
                entity.getCountry() != null
                        ? entity.getCountry().getCountryId()
                        : null
        );
    }
}