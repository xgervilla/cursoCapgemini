package com.example.domains.entities.dtos;

import static org.junit.jupiter.api.Assertions.*;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import com.example.domains.entities.Film;
import com.example.domains.entities.FilmActor;
import com.example.domains.entities.FilmCategory;
import com.example.domains.entities.Language;
import com.example.domains.entities.Film.Rating;

class FilmFullDTOTest {

	@BeforeEach
	void setUp() throws Exception {
	}

	//conversión de Film a FilmFullDTO
	@Test
	void testFromFilm() {
		List<Integer> filmActorList = new ArrayList<Integer>();
		List<Integer> filmCategoryList = new ArrayList<Integer>();
		var film = new FilmFullDTO(0, "The revenge of the test part 3", "Description of the movie", 60, Rating.GENERAL_AUDIENCES, new Short("2023"), (byte) 5, new BigDecimal(10.0), new BigDecimal(30), new Language(1), new Language(2), filmActorList, filmCategoryList);
		var filmDTO = FilmFullDTO.from(film);
		assertEquals(Film.class,filmDTO.getClass());
		
		assertAll("Attributes",
				() -> assertEquals(0,filmDTO.getFilmId()),
				() -> assertEquals("Description of the movie",filmDTO.getDescription()),
				() -> assertEquals(60, filmDTO.getLength()),
				() -> assertEquals(Rating.GENERAL_AUDIENCES, filmDTO.getRating()),
				() -> assertEquals(new Short("2023"), filmDTO.getReleaseYear()),
				() -> assertEquals((byte) 5, filmDTO.getRentalDuration()),
				() -> assertEquals(new BigDecimal(10.0), filmDTO.getRentalRate()),
				() -> assertEquals(new BigDecimal(30), filmDTO.getReplacementCost()),
				() -> assertEquals("The revenge of the test part 3", filmDTO.getTitle()),
				() -> assertEquals(new Language(1), filmDTO.getLanguage()),
				() -> assertEquals(new Language(2), filmDTO.getLanguageVO())
				);
	}

	//conversión de FilmFullDTO a Film
	@Test
	void testFromFilmDTO() {
		var film = new Film(0, "Description of the movie", 60, Rating.GENERAL_AUDIENCES, new Short("2023"), (byte) 5, new BigDecimal(10.0), new BigDecimal(30), "The revenge of the test part 4", new Language(1), new Language(2));
		var filmDTO = FilmFullDTO.from(film);
		assertEquals(FilmFullDTO.class,filmDTO.getClass());
		
		assertAll("Attributes",
				() -> assertEquals(0,filmDTO.getFilmId()),
				() -> assertEquals("Description of the movie",filmDTO.getDescription()),
				() -> assertEquals(60, filmDTO.getLength()),
				() -> assertEquals(Rating.GENERAL_AUDIENCES, filmDTO.getRating()),
				() -> assertEquals(new Short("2023"), filmDTO.getReleaseYear()),
				() -> assertEquals((byte) 5, filmDTO.getRentalDuration()),
				() -> assertEquals(new BigDecimal(10.0), filmDTO.getRentalRate()),
				() -> assertEquals(new BigDecimal(30), filmDTO.getReplacementCost()),
				() -> assertEquals("The revenge of the test part 4", filmDTO.getTitle()),
				() -> assertEquals(new Language(1), filmDTO.getLanguage()),
				() -> assertEquals(new Language(2), filmDTO.getLanguageVO())
				);
	}

}
