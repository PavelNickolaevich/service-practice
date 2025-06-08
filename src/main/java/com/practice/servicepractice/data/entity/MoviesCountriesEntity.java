package com.practice.servicepractice.data.entity;

import jakarta.persistence.*;
import lombok.Data;

@Entity
@Table(name = "movies_countries")
@Data
public class MoviesCountriesEntity {
    @Id
    @Column(name = "movie_country_id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long movieCountryId;

    @ManyToOne
    @JoinColumn(name = "movie_id", nullable = false)
    private MovieEntity movieId;

    @ManyToOne
    @JoinColumn(name = "country_id", nullable = false)
    private CountryEntity countryId;
}
