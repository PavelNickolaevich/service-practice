package com.practice.servicepractice.service;

import com.practice.servicepractice.data.dto.CountryDto;
import com.practice.servicepractice.data.entity.CountryEntity;
import com.practice.servicepractice.data.repository.CountryRepository;
import jakarta.annotation.Nonnull;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;

@Component
public class CountryService {

    private static final Logger LOG = LoggerFactory.getLogger(ActorService.class);
    private final CountryRepository countryRepository;

    @Autowired
    public CountryService(CountryRepository countryRepository) {
        this.countryRepository = countryRepository;
    }

    @Transactional(readOnly = true)
    public @Nonnull
    List<CountryDto> getAllCountries() {
        return countryRepository.findAll()
                .stream()
                .map(CountryDto::fromEntity)
                .toList();
    }

    public @Nonnull
    CountryDto addCountry(@Nonnull CountryDto country) {
            LOG.info("Add new country {}", country);
            var newCountry = new CountryEntity();
            newCountry.setName(country.name());
            return CountryDto.fromEntity(countryRepository.save(newCountry));
    }

    public boolean countryExistsByName(String name) {
        return countryRepository.existsByName(name);
    }
}
