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
	void testReleaseYearIsInvalid() {
		fail("Not yet implemented");
	}
	
	//duración del alquiler: positive > 0.0
	void testRentalDurationIsInvalid() {
		fail("Not yet implemented");
	}
	
	//positive, digits(2,2) > 0.0
	void testRentalRateIsInvalid() {
		fail("Not yet implemented");
	}
	
	//digits(3,2) > 0.0
	void testReplacementCostIsInvalid() {
		fail("Not yet implemented");
	}
}
