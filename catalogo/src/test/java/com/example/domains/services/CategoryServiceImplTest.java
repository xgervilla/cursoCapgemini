package com.example.domains.services;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.math.BigDecimal;
import java.sql.Timestamp;
import java.util.Optional;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.annotation.ComponentScan;

import com.example.domains.contracts.repositories.CategoryRepository;
import com.example.domains.entities.Category;
import com.example.domains.entities.Film;
import com.example.domains.entities.Language;
import com.example.domains.entities.Film.Rating;
import com.example.exceptions.DuplicateKeyException;
import com.example.exceptions.InvalidDataException;
import com.example.exceptions.NotFoundException;

@SpringBootTest
@ComponentScan(basePackages = "com.example")
class CategoryServiceImplTest {
	
	@Autowired
	CategoryRepository dao;
	
	@Autowired
	CategoryServiceImpl srv;

	@BeforeEach
	void setUp() throws Exception {
	}

	@Test
	@DisplayName("getAll")
	void testGetAll() {
		assertThat(srv.getAll().size()).isEqualTo(16);
	}

	@Test
	@DisplayName("getOne")
	void testGetOne() {
		var item = srv.getOne(1);
		assertTrue(item.isPresent());
	}
	
	@Test
	@DisplayName("Get one but no valid ID")
	void testGetOneNotFound() {
		var item = srv.getOne(100);
		assertFalse(item.isPresent());
	}
	
	@Test
	void testAdd() throws DuplicateKeyException, InvalidDataException {
		
		var originalSize = srv.getAll().size();
		
		var category = new Category(0, "New film category");
		
		var result = srv.add(category);
		assertEquals(originalSize+1, srv.getAll().size());
		
		srv.deleteById(result.getCategoryId());
		
	}

	@Test
	@DisplayName("Add null category")
	void testAddNull() throws DuplicateKeyException, InvalidDataException {
		assertThrows(InvalidDataException.class, () -> srv.add(null));
	}
	
	@Test
	@DisplayName("Add invalid category")
	void testAddInvalid() throws DuplicateKeyException, InvalidDataException{
		assertThrows(InvalidDataException.class, () -> srv.add(new Category(0,"     ")));
	}
	
	@Test
	@DisplayName("Modify category")
	void testModify() throws NotFoundException, InvalidDataException {
		
		var category = new Category(0, "New category: original");
		var addedCategory = srv.add(category);
		addedCategory.setName("New category: modified");
		
		var result = srv.modify(category);
		assertEquals("New category: modified", result.getName());
		assertEquals(category.getCategoryId(), result.getCategoryId());
		
		srv.deleteById(result.getCategoryId());
	}
	
	@Test
	@DisplayName("Modify null category")
	void testModifyNull() throws NotFoundException, InvalidDataException {
		assertThrows(InvalidDataException.class, () -> srv.modify(null));
	}
	
	@Test
	@DisplayName("Modify invalid category")
	void testModifyInvalid() throws NotFoundException, InvalidDataException {
		assertThrows(InvalidDataException.class, () -> srv.modify(new Category(0,"     ")));
	}
	
	@Test
	@DisplayName("Modify category not found")
	void testModifyNotFound() throws NotFoundException, InvalidDataException {
		assertThrows(NotFoundException.class, () -> srv.modify(new Category(0,"NotFound")));
	}

	//cuando se hace un delete de null salta una excepción, en caso contrario se ejecuta deleteById
	@Test
	@DisplayName("Delete null value")
	void testDelete() {
		assertThrows(InvalidDataException.class, () -> srv.delete(null));
	}

	@Test
	@DisplayName("Delete by id")
	void testDeleteById() throws InvalidDataException, NotFoundException{
		
		var category = new Category(0,"Category to delete");
		
		var addedCategoryId = srv.add(category).getCategoryId();
		
		var originalSize = srv.getAll().size();
		
		srv.deleteById(addedCategoryId);
		
		assertEquals(originalSize-1, srv.getAll().size());
	}
	
	@Test
	@DisplayName("Delete by id not exists")
	void testDeleteByIdNotExists() throws InvalidDataException, NotFoundException{
		
		var category = new Category(0,"Category to delete");
		
		var addedCategoryId = srv.add(category).getCategoryId();
		
		var originalSize = srv.getAll().size();
		
		srv.deleteById(addedCategoryId+1);
		
		assertEquals(originalSize, srv.getAll().size());
		
		srv.deleteById(addedCategoryId);
	}
	
	@Test
	@DisplayName("Novedades of category")
	void testNovedadesCategory() {
		var timestamp = Timestamp.valueOf("2007-01-01 00:00:00");
		var result = srv.novedades(timestamp);
		
		assertAll("Novedades of category",
				() -> assertEquals(5, result.size()));
	}
}