package com.example.domains.services;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;
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

import com.example.domains.contracts.repositories.FilmRepository;
import com.example.domains.entities.Film;
import com.example.domains.entities.Language;
import com.example.exceptions.DuplicateKeyException;
import com.example.exceptions.InvalidDataException;
import com.example.exceptions.NotFoundException;
import com.example.domains.entities.Film.Rating;

@SpringBootTest
@ComponentScan(basePackages = "com.example")
class FilmServiceImplTest {
	
	@Autowired
	FilmRepository dao;
	
	@Autowired
	FilmServiceImpl srv;

	@BeforeEach
	void setUp() throws Exception {
	}

	@Test
	@DisplayName("getAll")
	void testGetAll() {
		assertThat(srv.getAll().size()).isGreaterThanOrEqualTo(1000);
	}

	@Test
	@DisplayName("getOne")
	void testGetOne() {
		var item = srv.getOne(1);
		assertTrue(item.isPresent());
	}
	
	@Test
	@DisplayName("Add new film")
	void testAdd() throws DuplicateKeyException, InvalidDataException {
		var originalSize = srv.getAll().size();
		var film = new Film(0, "New film added", 65, Rating.PARENTS_STRONGLY_CAUTIONED, new Short("2011"), (byte) 5, new BigDecimal(30.0), new BigDecimal(40), "Film age 2", new Language(2), new Language(3));
		var result = srv.add(film);
		assertEquals(originalSize+1, srv.getAll().size());
		
		srv.deleteById(result.getFilmId());
		
	}

	@Test
	@DisplayName("Add null film")
	void testAddNull() throws DuplicateKeyException, InvalidDataException {
		assertThrows(InvalidDataException.class, () -> srv.add(null));
	}
	
	@Test
	@DisplayName("Add invalid film")
	void testAddInvalid() throws DuplicateKeyException, InvalidDataException{
		assertThrows(InvalidDataException.class, () -> srv.add(new Film("     ",new Language(1))));
	}
	
	@Test
	@DisplayName("Modify film")
	void testModify() throws NotFoundException, InvalidDataException, DuplicateKeyException {
		
		var film = new Film(0, "Film with original description", 65, Rating.PARENTS_STRONGLY_CAUTIONED, new Short("2011"), (byte) 5, new BigDecimal(30.0), new BigDecimal(40), "The revenge of the test part 4", new Language(2), new Language(3));
	
		var addedFilm = srv.add(film);
		
		addedFilm.setDescription("Film with modified description");
		
		var result = srv.modify(addedFilm);
		assertEquals("Film with modified description", result.getDescription());
		assertEquals(addedFilm.getFilmId(), result.getFilmId());//*/
		
		srv.deleteById(result.getFilmId());
	}
	
	@Test
	@DisplayName("Modify null film")
	void testModifyNull() throws NotFoundException, InvalidDataException {
		assertThrows(InvalidDataException.class, () -> srv.modify(null));
	}
	
	@Test
	@DisplayName("Modify invalid film")
	void testModifyInvalid() throws NotFoundException, InvalidDataException {
		assertThrows(InvalidDataException.class, () -> srv.modify(new Film("     ", new Language(0))));
	}
	
	@Test
	@DisplayName("Modify film not found")
	void testModifyNotFound() throws NotFoundException, InvalidDataException {
		var film = new Film(2000, "Film not found", 65, Rating.PARENTS_STRONGLY_CAUTIONED, new Short("2011"), (byte) 5, new BigDecimal(30.0), new BigDecimal(40), "NOT FOUND", new Language(2), new Language(3));
		assertThrows(NotFoundException.class, () -> srv.modify(film));
	}

	//cuando se hace un delete de null salta una excepción, en caso contrario se ejecuta deleteById
	@Test
	@DisplayName("Delete null value")
	void testDelete() {
		assertThrows(InvalidDataException.class, () -> srv.delete(null));
	}

	@Test
	@DisplayName("Delete by id")
	void testDeleteById() throws InvalidDataException, NotFoundException, DuplicateKeyException{
		
		var film = new Film(0, "Film to be deleted", 65, Rating.PARENTS_STRONGLY_CAUTIONED, new Short("2011"), (byte) 5, new BigDecimal(30.0), new BigDecimal(40), "Film deleted begins", new Language(2), new Language(3));
		var addedFilmId = srv.add(film).getFilmId();
		
		var originalSize = srv.getAll().size();
		srv.deleteById(addedFilmId);
		
		assertEquals(originalSize-1, srv.getAll().size());
	}
	
	@Test
	@DisplayName("Delete by id not exists")
	void testDeleteByIdNotExists() throws InvalidDataException, NotFoundException, DuplicateKeyException{
		
		var film = new Film(0, "Latest film added", 65, Rating.PARENTS_STRONGLY_CAUTIONED, new Short("2011"), (byte) 5, new BigDecimal(30.0), new BigDecimal(40), "Latest film", new Language(2), new Language(3));
		var addedFilmId = srv.add(film).getFilmId();
		
		var originalSize = srv.getAll().size();
		srv.deleteById(addedFilmId+1);
		
		assertEquals(originalSize, srv.getAll().size());
		
		srv.deleteById(addedFilmId);
	}
	
	@Test
	@DisplayName("Novedades of film")
	void testNovedadesFilm() {
		var timestamp = Timestamp.valueOf("2007-01-01 00:00:00");
		var result = srv.novedades(timestamp);
		
		assertAll("Novedades of film",
				() -> assertEquals(22, result.size()));
	}

}
