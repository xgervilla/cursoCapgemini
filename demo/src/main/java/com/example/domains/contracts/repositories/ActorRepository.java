package com.example.domains.contracts.repositories;

import java.util.List;
import org.springframework.data.domain.Sort;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.example.domains.entities.Actor;

public interface ActorRepository extends JpaRepository<Actor, Integer> {
	List<Actor> findTop5ByFirstNameStartingWithOrderByLastNameDesc(String prefijo);
	
	List<Actor> findTop5ByFirstNameStartingWith(String prefijo, Sort order);
	
	@Query("SELECT a FROM Actor a WHERE a.actorId < ?1")
	List<Actor> findConJPQL(int actorId);
	
	@Query(value = "SELECT * FROM actor a WHERE a.actor_id < :id", nativeQuery = true)
	List<Actor> findConNativeSQL(@Param("id") int actorId);
	
	
}
