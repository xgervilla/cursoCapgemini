package com.example.domains.services;

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

import com.example.domains.contracts.repositories.LanguageRepository;
import com.example.domains.entities.Film;
import com.example.domains.entities.Language;
import com.example.domains.entities.Film.Rating;
import com.example.exceptions.DuplicateKeyException;
import com.example.exceptions.InvalidDataException;
import com.example.exceptions.NotFoundException;

@DataJpaTest
@ComponentScan(basePackages = "com.example")
class LanguageServiceImplTest {
	
	@MockBean
	LanguageRepository dao;
	
	@Autowired
	LanguageServiceImpl srv;

	@BeforeEach
	void setUp() throws Exception {
	}

	void testGetAll() {
		List<Language> languageList = new ArrayList<>(Arrays.asList(
				new Language(1, "English"),
				new Language(2, "Catalan"),
				new Language(3, "Spanish")
				));
		when(dao.findAll()).thenReturn(languageList);
		assertEquals(3, dao.findAll().size());
		assertEquals(1, dao.findAll().get(0).getLanguageId());
	}

	@Test
	@DisplayName("getOne")
	void testGetOne() {
		List<Language> languageList = new ArrayList<>(Arrays.asList(
				new Language(1, "English"),
				new Language(2, "Catalan"),
				new Language(3, "Spanish")
				));
		when(dao.findById(2)).thenReturn(Optional.of(languageList.get(1)));
		var item = srv.getOne(2);
		assertTrue(item.isPresent());
		assertEquals("Catalan", item.get().getName());
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
		var language = new Language(4, "French");
		when(dao.save(language)).thenReturn(language);
		var result = srv.add(language);
		assertEquals(language, result);
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
		var language = new Language(5, "German");
		when(dao.existsById(5)).thenReturn(true);
		when(dao.findById(5)).thenReturn(Optional.of(language));
		when(dao.save(any(Language.class))).thenReturn(language);
		
		var result = srv.modify(language);
		
		verify(dao, times(1)).existsById(5);
		assertEquals(language, result);
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
		
		when(dao.existsById(0)).thenReturn(false);
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
		srv.deleteById(0);
		verify(dao, times(1)).deleteById(0);
	}

//	@Test
//	void testNovedades() {
//		fail("Not yet implemented");
//	}

}
