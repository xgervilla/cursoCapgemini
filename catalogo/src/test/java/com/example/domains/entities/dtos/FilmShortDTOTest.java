package com.example.domains.entities.dtos;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import com.example.domains.entities.Actor;
import com.example.domains.entities.Film;
import com.example.domains.entities.Language;

class FilmShortDTOTest {

	@BeforeEach
	void setUp() throws Exception {
	}

	
	//conversión de Film a FilmShortDTO
	@Test
	void testFilmShortDTO() {
		var filmDTO = FilmShortDTO.from(new Film("FILM TITLE", new Language(0)));
		assertEquals(FilmShortDTO.class,filmDTO.getClass());
		
		assertAll("Attributes",
				() -> assertEquals("FILM TITLE",filmDTO.getTitle()),
				() -> assertEquals(0,filmDTO.getFilmId()));
	}

}
