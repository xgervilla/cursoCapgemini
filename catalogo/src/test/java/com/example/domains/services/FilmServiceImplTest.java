package com.example.domains.services;

import static org.junit.jupiter.api.Assertions.*;
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

import com.example.domains.contracts.repositories.FilmRepository;
import com.example.domains.entities.Actor;
import com.example.domains.entities.Film;
import com.example.domains.entities.Language;
import com.example.exceptions.DuplicateKeyException;
import com.example.exceptions.InvalidDataException;
import com.example.exceptions.NotFoundException;
import com.example.domains.entities.Film.Rating;

@DataJpaTest
@ComponentScan(basePackages = "com.example")
class FilmServiceImplTest {
	
	@MockBean
	FilmRepository dao;
	
	@Autowired
	FilmServiceImpl srv;

	@BeforeEach
	void setUp() throws Exception {
	}

	@Test
	@DisplayName("findAll")
	void testGetAll() {
		List<Film> filmList = new ArrayList<>(Arrays.asList(
				new Film(1, "Film description 1", 82, Rating.GENERAL_AUDIENCES, new Short("2019"), (byte) 5, new BigDecimal(10.0), new BigDecimal(35), "The revenge of the test part 1", new Language(1), new Language(2)), 
				new Film(2, "Film description 2", 91, Rating.ADULTS_ONLY, new Short("2017"), (byte) 5, new BigDecimal(11.0), new BigDecimal(30), "The revenge of the test part 2", new Language(3), new Language(1)),
				new Film(3, "Film description 3", 78, Rating.PARENTAL_GUIDANCE_SUGGESTED, new Short("2004"), (byte) 5, new BigDecimal(20.0), new BigDecimal(45), "The revenge of the test part 3", new Language(4), new Language(2)), 
				new Film(4, "Film description 4", 65, Rating.PARENTS_STRONGLY_CAUTIONED, new Short("2011"), (byte) 5, new BigDecimal(30.0), new BigDecimal(40), "The revenge of the test part 4", new Language(2), new Language(3)) 
				));
		when(dao.findAll()).thenReturn(filmList);
		assertEquals(4, dao.findAll().size());
		assertEquals(1, dao.findAll().get(0).getFilmId());
	}

	@Test
	@DisplayName("getOne")
	void testGetOne() {
		List<Film> filmList = new ArrayList<>(Arrays.asList(
				new Film(1, "Film description 1", 82, Rating.GENERAL_AUDIENCES, new Short("2019"), (byte) 5, new BigDecimal(10.0), new BigDecimal(35), "The revenge of the test part 1", new Language(1), new Language(2)), 
				new Film(2, "Film description 2", 91, Rating.ADULTS_ONLY, new Short("2017"), (byte) 5, new BigDecimal(11.0), new BigDecimal(30), "The revenge of the test part 2", new Language(3), new Language(1)),
				new Film(3, "Film description 3", 78, Rating.PARENTAL_GUIDANCE_SUGGESTED, new Short("2004"), (byte) 5, new BigDecimal(20.0), new BigDecimal(45), "The revenge of the test part 3", new Language(4), new Language(2)), 
				new Film(4, "Film description 4", 65, Rating.PARENTS_STRONGLY_CAUTIONED, new Short("2011"), (byte) 5, new BigDecimal(30.0), new BigDecimal(40), "The revenge of the test part 4", new Language(2), new Language(3)) 
				));
		when(dao.findById(2)).thenReturn(Optional.of(filmList.get(1)));
		var item = srv.getOne(2);
		assertTrue(item.isPresent());
		assertEquals("Film description 2", item.get().getDescription());
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
		fail("Not yet implemented");
		
		var film = new Film(4, "Film description 4", 65, Rating.PARENTS_STRONGLY_CAUTIONED, new Short("2011"), (byte) 5, new BigDecimal(30.0), new BigDecimal(40), "The revenge of the test part 4", new Language(2), new Language(3));
		when(dao.save(film)).thenReturn(film);
		var result = srv.add(film);
		assertEquals(film, result);
	}

	@Test
	void testModify() throws NotFoundException, InvalidDataException {
		fail("Not yet implemented");
		var film = new Film(4, "Film description 4", 65, Rating.PARENTS_STRONGLY_CAUTIONED, new Short("2011"), (byte) 5, new BigDecimal(30.0), new BigDecimal(40), "The revenge of the test part 4", new Language(2), new Language(3));
		
		when(dao.existsById(0)).thenReturn(true);
		when(dao.findById(0)).thenReturn(Optional.of(film));
		when(dao.save(film)).thenReturn(film);
		
		var result = srv.modify(film);
		
		verify(dao, times(1)).existsById(0);
		assertEquals(film, result);
	}

	@Test
	void testDelete() {
		fail("Not yet implemented");
	}

	@Test
	void testDeleteById() {
		fail("Not yet implemented");
	}

//	@Test
//	void testNovedades() {
//		fail("Not yet implemented");
//	}

}
