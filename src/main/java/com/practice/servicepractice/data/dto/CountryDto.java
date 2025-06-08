package com.practice.servicepractice.data.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.practice.servicepractice.data.entity.CountryEntity;
import io.swagger.v3.oas.annotations.media.Schema;

@Schema(description = "Представление страны")
public record CountryDto(

        @Schema(description = "Название страны", example = "Россия")
        @JsonProperty("name")
        String name
) {
    public static CountryDto fromEntity(CountryEntity entity) {
        return new CountryDto(entity.getName());
    }
}
