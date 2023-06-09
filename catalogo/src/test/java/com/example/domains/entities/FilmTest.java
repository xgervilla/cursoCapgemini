package com.example.domains.entities;

import static org.junit.jupiter.api.Assertions.*;

import java.math.BigDecimal;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;
import org.junit.jupiter.params.provider.NullSource;
import org.springframework.boot.test.autoconfigure.data.redis.DataRedisTest;

import com.example.domains.entities.Film.Rating;

import jakarta.annotation.Nullable;

/*
 * Entity Film:
 	* filmId			--> siempre 0, dejamos a la BD la responsabilidad de asignar el ID que corresponda
 	* Rating			--> Rating ennumeration
 	* description		--> descripción de la película: sin restricciones
 	* lastUpdate		--> "intocable", viene dado por la BD así que no se debe modificar 
 	* length			--> longitud de la pelicula: permite null, si hay valor debe ser positivo
 	* releaseYear		--> año de estreno: valor superior o igual a 1895
 	* rentalDuration	--> duración del alquiler: positive > 0.0
 	* rentalRate		--> valoración del alquiler: positive, digits(2,2) > 0.0
 	* replacementCost	--> coste del reemplazo: digits(3,2) > 0.0
 	* title				--> titulo de la pelicula: tamaño máximo de 128 carácteres Y no puede ser blank
 	* language			--> lenguaje de la pelicula: de tipo Language, no puede ser un atributo nulo
 	* languageVO		--> lenguaje de la pelicula en VO: de tipo Language, SÍ puede ser nulo
 	* filmActors		--> actores que aparecen en la película: sin restricciones en la construcción del objeto
 	* filmCategories	--> categorias de la pelicula: sin restricciones en la construcción del objeto
*/

class FilmTest {

	@BeforeEach
	void setUp() throws Exception {
	}

	@Test
	@DisplayName("Film construction partial")
	void testIsValidShort() {
		var item = new Film("Spanish", new Language(0));
		assertTrue(item.isValid());
	}
	
	@Test
	@DisplayName("Film construction complete")
	void testIsValidComplete() {
		var item = new Film(0, "Description of the movie", 60, Rating.GENERAL_AUDIENCES, new Short("2023"), (byte) 5, new BigDecimal(10.0), new BigDecimal(30), "The revenge of the test part 2", new Language(1), new Language(2));
		assertTrue(item.isValid());
	}
	
	//tamaño máximo de 128 carácteres Y no puede ser blank
	@DisplayName("Invalid film title")
	@ParameterizedTest(name="Title: {0}, Error: {1}")
	@CsvSource(value = {
			"'    ','ERRORES: title: must not be blank.'",
			"'123456789012345678901234567890123456789012345678901234567890123456789012345678901234567890123456789012345678901234567890123456789012345678901234567890','ERRORES: title: size must be between 0 and 128.'"
	})
	void testTitleIsInvalid(String title, String errorMessage) {
		var item = new Film(title, new Language(0)); 
		assertTrue(item.isInvalid());
		assertEquals(errorMessage,item.getErrorsMessage());
	}
	
	@DisplayName("Null film title")
	@Test
	void testTitleIsNull() {
		var item = new Film(null, new Language(0)); 
		assertTrue(item.isInvalid());
		assertEquals("ERRORES: title: must not be blank.",item.getErrorsMessage());
	}
	
	//de tipo Language, no puede ser un atributo nulo
	@DisplayName("Invalid language")
	@Test
	void testLanguageIsInvalid() {
		var item = new Film("Film title", null); 
		assertTrue(item.isInvalid());
		assertEquals("ERRORES: language: must not be null.",item.getErrorsMessage());
	}
	
	//permite null, si hay valor debe ser positivo
	@DisplayName("Invalid film length")
	@ParameterizedTest(name = "Length: {0}, Error: {1}")
	@CsvSource(value = {"-10,'ERRORES: length: must be greater than 0.'"})
	void testLengthIsInvalid(Integer length, String errorMessage) {
		var item = new Film(0, "Description of the movie", length, Rating.GENERAL_AUDIENCES, new Short("2023"), (byte) 5, new BigDecimal(10.0), new BigDecimal(30), "The revenge of the test part 2", new Language(1), new Language(2));
		assertTrue(item.isInvalid());
		assertEquals(errorMessage, item.getErrorsMessage());
	}
	
	//valor superior o igual a 1895
	@Test
	@DisplayName("Invalid releaseYear")
	void testReleaseYearIsInvalid(){
		var item = new Film(0, "Description of the movie", 60, Rating.GENERAL_AUDIENCES, new Short("1800"), (byte) 5, new BigDecimal(10.0), new BigDecimal(30), "The revenge of the test part 2", new Language(1), new Language(2)); 
		assertTrue(item.isInvalid());
		assertEquals("ERRORES: releaseYear: must be greater than or equal to 1895.",item.getErrorsMessage());
	}
	
	//duración del alquiler: positive >= 0
	@Test
	@DisplayName("Invalid rentalDuration")
	void testRentalDurationIsInvalid() {
		var item = new Film(0, "Description of the movie", 60, Rating.GENERAL_AUDIENCES, new Short("2019"), (byte) -1, new BigDecimal(10.0), new BigDecimal(30), "The revenge of the test part 2", new Language(1), new Language(2)); 
		assertTrue(item.isInvalid());
		assertEquals("ERRORES: rentalDuration: must be greater than or equal to 0.",item.getErrorsMessage());
	}
	
	//positive, digits(2,2) > 0.0
	@Test
	@DisplayName("Invalid rentalRate")
	void testRentalRateIsInvalid() {
		var item = new Film(0, "Description of the movie", 60, Rating.GENERAL_AUDIENCES, new Short("2019"), (byte) 5, new BigDecimal(-10.0), new BigDecimal(30), "The revenge of the test part 2", new Language(1), new Language(2)); 
		assertTrue(item.isInvalid());
		assertEquals("ERRORES: rentalRate: must be greater than 0.0, must be greater than 0.",item.getErrorsMessage());
	}
	
	//digits(3,2) > 0.0
	@Test
	@DisplayName("Invalid replacementCost")
	void testReplacementCostIsInvalid() {
		var item = new Film(0, "Description of the movie", 60, Rating.GENERAL_AUDIENCES, new Short("2019"), (byte) 5, new BigDecimal(10.0), new BigDecimal(-30), "The revenge of the test part 2", new Language(1), new Language(2)); 
		assertTrue(item.isInvalid());
		assertEquals("ERRORES: replacementCost: must be greater than 0.0.",item.getErrorsMessage());
	}
	
	
	void testMergeValid() {
		//creation of the original film
		var filmOriginal = new Film(0, "Description of the movie", 60, Rating.GENERAL_AUDIENCES, new Short("2019"), (byte) 5, new BigDecimal(-10.0), new BigDecimal(30), "Film to test: original", new Language(1), new Language(2)); 
		
		filmOriginal.addActor(new Actor(1,"Actor","ORIGINALFIRST"));
		
		filmOriginal.addActor(new Actor(2,"Actor","ORIGINALSECOND"));
		
		filmOriginal.addActor(new Actor(3,"Actor","ORIGINALTHIRD"));
		
		//creation of the modified actor
		//Modified actor changes its last name and appears on movies 7 and 11 instead of 7 and 9
		var filmModified = new Film(0, "Description of the movie", 60, Rating.GENERAL_AUDIENCES, new Short("2019"), (byte) 5, new BigDecimal(-10.0), new BigDecimal(30), "Film to test: modified", new Language(4), new Language(5)); 
		
		filmOriginal.addActor(new Actor(1,"Actor","ORIGINALFIRST"));
		
		filmOriginal.addActor(new Actor(4,"Actor","ORIGINALFOURTH"));
		
		filmOriginal.addActor(new Actor(5,"Actor","ORIGINALFIFTH"));
		
		//application of the merge method
		var filmMerged = filmOriginal.merge(filmModified);
		
		//asert to check all changes
		assertAll("Merge operation",
			() -> assertEquals(filmModified.getFilmId(), filmMerged.getFilmId()),
			() -> assertEquals(filmModified.getDescription(), filmMerged.getDescription()),
			() -> assertEquals(filmModified.getLanguage(), filmMerged.getLanguage()),
			() -> assertEquals(filmModified.getLanguageVO(), filmMerged.getLanguageVO()),
			() -> assertEquals(filmModified.getActors(), filmMerged.getActors()),
			() -> assertEquals(filmModified.getCategories(), filmMerged.getCategories()),
			() -> assertEquals(filmModified.getLength(), filmMerged.getLength()),
			() -> assertEquals(filmModified.getRating(), filmMerged.getRating()),
			() -> assertEquals(filmModified.getRentalRate(), filmMerged.getRentalRate()),
			() -> assertEquals(filmModified.getRentalDuration(), filmMerged.getRentalDuration()),
			() -> assertEquals(filmModified.getTitle(), filmMerged.getTitle()),
			() -> assertEquals(filmModified.getReplacementCost(), filmMerged.getReplacementCost())
		);
	}
}
