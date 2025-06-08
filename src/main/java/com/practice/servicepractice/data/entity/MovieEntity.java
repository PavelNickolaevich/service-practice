package com.practice.servicepractice.data.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.Set;

@Entity
@Table(name = "movies")
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class MovieEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "movie_id")
    private Long movieId;

    @Column(name = "title", nullable = false)
    private String title;

    @Lob
    @Column(name = "description", columnDefinition = "TEXT")
    private String description;

    @Column(name = "release_year")
    private Integer releaseYear;

    @Column(name = "duration")
    private Integer duration;

    @Column(name = "rating")
    private Integer rating;

    @OneToMany(mappedBy = "movieId", cascade = CascadeType.ALL)
    Set<MoviesActorsEntity> moviesActors;

    @OneToMany(mappedBy = "movieId", cascade = CascadeType.ALL)
    Set<MoviesCountriesEntity> moviesCountries;

    @OneToMany(mappedBy = "movieId", cascade = CascadeType.ALL)
    Set<MoviesDirectorsEntity> moviesDirectors;

    @OneToMany(mappedBy = "movieId", cascade = CascadeType.ALL)
    Set<MoviesGenresEntity> moviesGenres;

    // You can add additional methods if needed
    public String getFormattedDuration() {
        if (duration == null) return "N/A";
        int hours = duration / 60;
        int minutes = duration % 60;
        return String.format("%dh %02dm", hours, minutes);
    }

}
