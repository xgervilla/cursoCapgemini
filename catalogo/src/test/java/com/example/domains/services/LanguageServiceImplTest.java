package com.example.domains.services;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.annotation.ComponentScan;

import com.example.domains.contracts.repositories.LanguageRepository;
import com.example.domains.entities.Language;
import com.example.exceptions.DuplicateKeyException;
import com.example.exceptions.InvalidDataException;
import com.example.exceptions.NotFoundException;

@SpringBootTest
@ComponentScan(basePackages = "com.example")
class LanguageServiceImplTest {
	
	@Autowired
	LanguageRepository dao;
	
	@Autowired
	LanguageServiceImpl srv;

	@BeforeEach
	void setUp() throws Exception {
	}

	@Test
	@DisplayName("getAll")
	void testGetAll() {
		assertThat(srv.getAll().size()).isEqualTo(6);
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
		var item = srv.getOne(8);
		assertFalse(item.isPresent());
	}
	
	@Test
	void testAdd() throws DuplicateKeyException, InvalidDataException {
		
		var originalSize = srv.getAll().size();
		
		var category = new Language(0, "New language");
		
		var result = srv.add(category);
		assertEquals(originalSize+1, srv.getAll().size());
		
		srv.deleteById(result.getLanguageId());
		
	}

	@Test
	@DisplayName("Add null language")
	void testAddNull() throws DuplicateKeyException, InvalidDataException {
		assertThrows(InvalidDataException.class, () -> srv.add(null));
	}
	
	@Test
	@DisplayName("Add invalid language")
	void testAddInvalid() throws DuplicateKeyException, InvalidDataException{
		assertThrows(InvalidDataException.class, () -> srv.add(new Language(0,"     ")));
	}
	
	@Test
	@DisplayName("Modify language")
	void testModify() throws NotFoundException, InvalidDataException {
		
		var category = new Language(0, "Portuguese");
		var addedLanguage = srv.add(category);
		addedLanguage.setName("Portuguese modified");
		
		var result = srv.modify(category);
		assertEquals("Portuguese modified", result.getName());
		assertEquals(category.getLanguageId(), result.getLanguageId());
		
		srv.deleteById(result.getLanguageId());
	}
	
	@Test
	@DisplayName("Modify null language")
	void testModifyNull() throws NotFoundException, InvalidDataException {
		assertThrows(InvalidDataException.class, () -> srv.modify(null));
	}
	
	@Test
	@DisplayName("Modify invalid language")
	void testModifyInvalid() throws NotFoundException, InvalidDataException {
		assertThrows(InvalidDataException.class, () -> srv.modify(new Language(0,"     ")));
	}
	
	@Test
	@DisplayName("Modify language not found")
	void testModifyNotFound() throws NotFoundException, InvalidDataException {
		assertThrows(NotFoundException.class, () -> srv.modify(new Language(0,"NotFound")));
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
		
		var language = new Language(0,"Language to delete");
		
		var addedLanguageId = srv.add(language).getLanguageId();
		
		var originalSize = srv.getAll().size();
		
		srv.deleteById(addedLanguageId);
		
		assertEquals(originalSize-1, srv.getAll().size());
	}
	
	@Test
	@DisplayName("Delete by id not exists")
	void testDeleteByIdNotExists() throws InvalidDataException, NotFoundException{
		
		var language = new Language(0,"Language to delete");
		
		var addedLanguageId = srv.add(language).getLanguageId();
		
		var originalSize = srv.getAll().size();
		
		srv.deleteById(addedLanguageId+1);
		
		assertEquals(originalSize, srv.getAll().size());
		
		srv.deleteById(addedLanguageId);
	}
}