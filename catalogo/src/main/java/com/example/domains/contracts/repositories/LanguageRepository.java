package com.example.domains.contracts.repositories;

import java.sql.Timestamp;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

import com.example.domains.core.repositories.contracts.RepositoryWithProjections;
import com.example.domains.entities.Actor;
import com.example.domains.entities.Language;

public interface LanguageRepository extends JpaRepository<Language, Integer>, JpaSpecificationExecutor<Language>, RepositoryWithProjections{
	List<Language> findByLastUpdateGreaterThanEqualOrderByLastUpdate(Timestamp fecha);
}
