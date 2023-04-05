package com.example.domains.contracts.repositories;

import java.sql.Timestamp;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.example.domains.core.repositories.contracts.RepositoryWithProjections;
import com.example.domains.entities.Actor;
import com.example.domains.entities.Film;

public interface FilmRepository extends JpaRepository<Film, Integer>, JpaSpecificationExecutor<Film>, RepositoryWithProjections {
	List<Film> findByLastUpdateGreaterThanEqualOrderByLastUpdate(Timestamp fecha);
	@Query("SELECT a FROM Film a WHERE a.filmId < :id")
	List<Film> findConJPQL(@Param("id") int filmId);
	@Query(name = "Film.findAll")
	List<Film> findConJPQL();
	@Query(value = "SELECT * from film WHERE film_id < ?1", nativeQuery = true)
	List<Film> findConSQL(int filmId);
}
