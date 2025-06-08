package com.practice.servicepractice.data.entity;

import jakarta.persistence.*;
import lombok.Data;

@Entity
@Table(name = "movies_genres")
@Data
public class MoviesGenresEntity {
    @Id
    @Column(name = "movie_genres_id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long movieGenresId;

    @ManyToOne
    @JoinColumn(name = "movie_id", nullable = false)
    private MovieEntity movieId;

    @ManyToOne
    @JoinColumn(name = "genre_id", nullable = false)
    private GenreEntity genreId;
}
