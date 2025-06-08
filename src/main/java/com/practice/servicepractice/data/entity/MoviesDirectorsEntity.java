package com.practice.servicepractice.data.entity;

import jakarta.persistence.*;
import lombok.Data;

@Entity
@Table(name = "movies_directors")
@Data
public class MoviesDirectorsEntity {
    @Id
    @Column(name = "movie_director_id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long movieDirectorId;

    @ManyToOne
    @JoinColumn(name = "movie_id", nullable = false)
    private MovieEntity movieId;

    @ManyToOne
    @JoinColumn(name = "director_id", nullable = false)
    private DirectorEntity directorId;
}
