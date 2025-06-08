package com.practice.servicepractice.data.entity;

import jakarta.persistence.*;
import lombok.Data;

@Entity
@Table(name = "movies_actors")
@Data
public class MoviesActorsEntity {
    @Id
    @Column(name = "movie_actor_id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long movieActorId;

    @ManyToOne
    @JoinColumn(name = "movie_id", nullable = false)
    private MovieEntity movieId;

    @ManyToOne
    @JoinColumn(name = "actor_id", nullable = false)
    private ActorEntity actorId;
}
