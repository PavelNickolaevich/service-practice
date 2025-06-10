package com.practice.servicepractice.controller;

import com.practice.servicepractice.data.dto.CountryDto;
import com.practice.servicepractice.data.models.ApiResponseService;
import com.practice.servicepractice.service.CountryService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.headers.Header;
import io.swagger.v3.oas.annotations.media.ArraySchema;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

import static com.practice.servicepractice.topics.Topics.COUNTRY_TOPIC;

@RestController
@Tag(name = "Country API", description = "Управление странами")
public class CountryController {

    private final CountryService countryService;
    private final KafkaTemplate<String, Object> kafkaTemplate;
   // private static final String COUNTRY_TOPIC = "country-events";

    @Autowired
    public CountryController(CountryService countryService, KafkaTemplate<String, Object> kafkaTemplate) {
        this.countryService = countryService;
        this.kafkaTemplate = kafkaTemplate;
    }

    @GetMapping("/v1/api/allCountries")
    @Operation(
            summary = "Получить список всех стран",
            description = "Возвращает полный список всех стран, зарегистрированных в системе",
            tags = {"Страны"}
    )
    @ApiResponses({
            @ApiResponse(
                    responseCode = "200",
                    description = "Успешное получение списка стран",
                    content = @Content(
                            mediaType = "application/json",
                            array = @ArraySchema(schema = @Schema(implementation = CountryDto.class))
                    ),
                    headers = @Header(
                            name = "X-Total-Count",
                            description = "Общее количество стран",
                            schema = @Schema(type = "integer")
                    )
            ),
            @ApiResponse(
                    responseCode = "401",
                    description = "Требуется аутентификация",
                    content = @Content(schema = @Schema(hidden = true))
            ),
            @ApiResponse(
                    responseCode = "403",
                    description = "Доступ запрещен",
                    content = @Content(schema = @Schema(hidden = true))
            ),
            @ApiResponse(
                    responseCode = "500",
                    description = "Внутренняя ошибка сервера",
                    content = @Content(schema = @Schema(hidden = true))
            )
    })
    @ApiResponse(responseCode = "200", description = "Список всех стран")
    public List<CountryDto> getAllCountries()  {
        return countryService.getAllCountries();
    }

    @PostMapping("/v1/api/saveCountry")
    @Operation(summary = "Создать новую страну")
    @ApiResponses({
            @ApiResponse(responseCode = "202", description = "Запрос принят в обработку"),
            @ApiResponse(responseCode = "409", description = "Страна уже существует")
    })
    public ResponseEntity<ApiResponseService<String>> saveCountry(@RequestBody CountryDto country) {
        if (countryService.countryExistsByName(country.name())) {
            return ResponseEntity.status(HttpStatus.CONFLICT)
                    .body(new ApiResponseService<>(
                            "error",
                            "Страна с названием '" + country.name() + "' уже существует",
                            null
                    ));
        }

        kafkaTemplate.send(COUNTRY_TOPIC, "create", country);

        return ResponseEntity.accepted()
                .body(new ApiResponseService<>(
                        "accepted",
                        "Запрос на сохранение страны принят",
                        country.name()
                ));
    }
}
