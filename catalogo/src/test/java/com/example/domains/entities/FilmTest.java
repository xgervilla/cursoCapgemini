package com.example.domains.entities;

import static org.junit.jupiter.api.Assertions.*;

import java.math.BigDecimal;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import com.example.domains.entities.Film.Rating;

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
	void testTitleIsInvalid() {
		fail("Not yet implemented");
	}
	
	//de tipo Language, no puede ser un atributo nulo
	void testLanguageIsInvalid() {
		fail("Not yet implemented");
	}
	
	//permite null, si hay valor debe ser positivo
	void testLengthIsInvalid() {
		fail("Not yet implemented");
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
