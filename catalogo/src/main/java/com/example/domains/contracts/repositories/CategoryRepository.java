package com.example.domains.contracts.repositories;

import java.sql.Timestamp;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

import com.example.domains.core.repositories.contracts.RepositoryWithProjections;
import com.example.domains.entities.Actor;
import com.example.domains.entities.Category;

public interface CategoryRepository extends JpaRepository<Category, Integer>, JpaSpecificationExecutor<Category>, RepositoryWithProjections{
	List<Category> findByLastUpdateGreaterThanEqualOrderByLastUpdate(Timestamp fecha);
}
