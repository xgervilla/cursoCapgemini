package com.example.domains.entities;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;

/*
 * Entidad Language:
 	*  languageId	--> siempre 0, dejamos a la BD la responsabilidad de asignar el ID que corresponda 
 	*  name			--> nombre del lenguaje: hasta un máximo de 20 carácteres, sin límite inferior PERO no puede ser blank ni nulo
 	*  last_update	--> "intocable", viene dado por la BD así que no se debe modificar 
 	*  films		--> lista de peliculas que tienen el lenguaje: sin restricciones en la construcción del objeto
 	*  filmsVO		--> lista de peliculas que tienen el lenguaje en VO: sin restricciones en la construcción del objeto
*/

class LanguageTest {

	@BeforeEach
	void setUp() throws Exception {
	}

	@Test
	void testIsValid() {
		var item = new Language(0,"Spanish");
		assertTrue(item.isValid());
	}
	
	
	@DisplayName("name invalid")
	@ParameterizedTest(name="Name: {0}, Error: {1}")
	@CsvSource({"'','ERRORES: name: must not be blank.'",
		"'     ','ERRORES: name: must not be blank.'",
		"12345678901234567890123456789012345678901234567890,'ERRORES: name: size must be between 0 and 20.'"})
	void testNameIsInvalid(String languageName, String error) {
		var language = new Language(0, languageName);
		assertTrue(language.isInvalid());
		assertEquals(error, language.getErrorsMessage());
	}

}
