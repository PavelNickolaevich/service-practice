package com.practice.servicepractice.data.repository;

import com.practice.servicepractice.data.entity.ActorEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public  interface ActorRepository extends JpaRepository<ActorEntity, Long> {

    ActorEntity findByActorId(Long actorId);

}
