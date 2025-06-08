package com.practice.servicepractice.data.entity;

import jakarta.persistence.*;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;

import java.util.Set;

@Entity
@Table(name = "country", uniqueConstraints = {@UniqueConstraint(columnNames = {"name"})})
@Setter
public class CountryEntity {
    @Id
    @Column(name = "country_id", nullable = false)
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long countryId;

    @Column(name = "name", nullable = false, length = 50)
    private String name;

    @OneToMany(mappedBy = "country")
    private Set<ActorEntity> actors;

    @OneToMany(mappedBy = "country")
    private Set<DirectorEntity> directors;

    @OneToMany(mappedBy = "countryId")
    private Set<MoviesCountriesEntity> moviesCountries;

    public Long getCountryId() {
        return countryId;
    }

    public String getName() {
        return name;
    }

    public Set<ActorEntity> getActors() {
        return actors;
    }

    public Set<DirectorEntity> getDirectors() {
        return directors;
    }

    public Set<MoviesCountriesEntity> getMoviesCountries() {
        return moviesCountries;
    }

    public void setCountryId(Long countryId) {
        this.countryId = countryId;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setActors(Set<ActorEntity> actors) {
        this.actors = actors;
    }

    public void setDirectors(Set<DirectorEntity> directors) {
        this.directors = directors;
    }

    public void setMoviesCountries(Set<MoviesCountriesEntity> moviesCountries) {
        this.moviesCountries = moviesCountries;
    }
}
