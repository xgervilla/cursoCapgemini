package com.example.domains.services;

import static org.junit.jupiter.api.Assertions.*;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.ComponentScan;

import com.example.domains.contracts.repositories.CategoryRepository;
import com.example.domains.entities.Category;
import com.example.exceptions.DuplicateKeyException;
import com.example.exceptions.InvalidDataException;
import com.example.exceptions.NotFoundException;

@DataJpaTest
@ComponentScan(basePackages = "com.example")
class CategoryServiceImplMockTest {
		
	@MockBean
	CategoryRepository dao;
	
	@Autowired
	CategoryServiceImpl srv;

	@BeforeEach
	void setUp() throws Exception {
	}

	void testGetAll() {
		List<Category> categoryList = new ArrayList<>(Arrays.asList(
				new Category(1, "Romance"),
				new Category(2, "Drama"),
				new Category(3, "Comedy")
				));
		when(dao.findAll()).thenReturn(categoryList);
		assertEquals(3, dao.findAll().size());
		assertEquals(3, dao.findAll().get(2).getCategoryId());
	}

	@Test
	@DisplayName("getOne")
	void testGetOne() {
		List<Category> categoryList = new ArrayList<>(Arrays.asList(
				new Category(1, "Drama"),
				new Category(2, "Horror"),
				new Category(3, "Indie")
				));
		when(dao.findById(2)).thenReturn(Optional.of(categoryList.get(1)));
		var item = srv.getOne(2);
		assertTrue(item.isPresent());
		assertEquals("Horror", item.get().getName());
		}

	@Test
	@DisplayName("Get one but no data available")
	void testGetOneEmptyList() {
		when(dao.findById(1)).thenReturn(Optional.empty());
		var item = srv.getOne(1);
		assertFalse(item.isPresent());
	}
	
	@Test
	void testAdd() throws DuplicateKeyException, InvalidDataException {
		var category = new Category(4, "Animation");
		when(dao.save(category)).thenReturn(category);
		var result = srv.add(category);
		assertEquals(category, result);
	}

	@Test
	@DisplayName("Add null language")
	void testAddNull() throws DuplicateKeyException, InvalidDataException {
		assertThrows(InvalidDataException.class, () -> srv.add(null));
	}
	
	@Test
	@DisplayName("Add invalid language")
	void testAddInvalid() throws DuplicateKeyException, InvalidDataException{
		assertThrows(InvalidDataException.class, () -> srv.add(new Category(0,"     ")));
	}
	
	@Test
	@DisplayName("Modify language")
	void testModify() throws NotFoundException, InvalidDataException {
		var category = new Category(5, "German");
		when(dao.existsById(5)).thenReturn(true);
		when(dao.findById(5)).thenReturn(Optional.of(category));
		when(dao.save(any(Category.class))).thenReturn(category);
		
		var result = srv.modify(category);
		
		verify(dao, times(1)).existsById(5);
		assertEquals(category, result);
	}
	
	@Test
	@DisplayName("Modify null language")
	void testModifyNull() throws NotFoundException, InvalidDataException {
		assertThrows(InvalidDataException.class, () -> srv.modify(null));
	}
	
	@Test
	@DisplayName("Modify invalid language")
	void testModifyInvalid() throws NotFoundException, InvalidDataException {
		assertThrows(InvalidDataException.class, () -> srv.modify(new Category(0,"     ")));
	}
	
	@Test
	@DisplayName("Modify language not found")
	void testModifyNotFound() throws NotFoundException, InvalidDataException {
		
		when(dao.existsById(0)).thenReturn(false);
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
		srv.deleteById(0);
		verify(dao, times(1)).deleteById(0);
	}

//	@Test
//	void testNovedades() {
//		fail("Not yet implemented");
//	}

}