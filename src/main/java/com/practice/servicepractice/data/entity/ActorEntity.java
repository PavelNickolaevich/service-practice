package com.practice.servicepractice.data.entity;

import jakarta.persistence.*;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

import java.util.Date;
import java.util.Set;

@Entity
@Table(name = "actors")
@Setter
public class ActorEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "actor_id", nullable = false)
    private Long actorId;

    @Column(name = "first_name", nullable = false, length = 50)
    private String firstName;

    @Column(name = "last_name", nullable = false, length = 50)
    private String lastName;

    @Column(name = "patronymic", length = 50)
    private String patronymic;

    @Column(name = "birth_of_date")
    @Temporal(TemporalType.DATE)
    private Date birthOfDate;

    @ManyToOne
    @JoinColumn(name = "country_id")
    private CountryEntity country;

    @OneToMany(mappedBy = "actorId", cascade = CascadeType.ALL)
    private Set<MoviesActorsEntity> moviesActors;

    public ActorEntity(String firstName, String lastName) {
        this.firstName = firstName;
        this.lastName = lastName;
    }

    public ActorEntity(String firstName, String lastName, String patronymic, Date birthOfDate, CountryEntity country) {
        this.firstName = firstName;
        this.lastName = lastName;
        this.patronymic = patronymic;
        this.birthOfDate = birthOfDate;
        this.country = country;
    }

    public Long getActorId() {
        return actorId;
    }

    public String getFirstName() {
        return firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public String getPatronymic() {
        return patronymic;
    }

    public Date getBirthOfDate() {
        return birthOfDate;
    }

    public CountryEntity getCountry() {
        return country;
    }

    public Set<MoviesActorsEntity> getMoviesActors() {
        return moviesActors;
    }

    public void setActorId(Long actorId) {
        this.actorId = actorId;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public void setPatronymic(String patronymic) {
        this.patronymic = patronymic;
    }

    public void setBirthOfDate(Date birthOfDate) {
        this.birthOfDate = birthOfDate;
    }

    public void setCountry(CountryEntity country) {
        this.country = country;
    }

    public void setMoviesActors(Set<MoviesActorsEntity> moviesActors) {
        this.moviesActors = moviesActors;
    }
}
