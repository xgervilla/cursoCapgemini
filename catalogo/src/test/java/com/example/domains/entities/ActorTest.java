package com.example.domains.entities;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;

/*
 * Entidad Actor:
 	* firstName 	--> nombre del actor: entre 2 y 45 carácteres, permite el uso de minúsculas
 	* lastName		--> apellidos del actor: entre 2 y 45 carácteres Y todo en mayúsculas
 	* actorId 		--> siempre 0, dejamos a la BD la responsabilidad de asignar el ID que corresponda
 	* lastUpdate	--> "intocable", viene dado por la BD así que no se debe modificar
 	* filmActor 	--> lista de películas en las que participa el actor: sin restricciones en la construcción del objeto
*/

class ActorTest {

	@BeforeEach
	void setUp() throws Exception {
	}

	@Test
	void testIsValid() {
		var fixture = new Actor(0,"Pepito","GRILLO");
		assertTrue(fixture.isValid());
	}
	
	
	// 2< |nombre| > 45 && no puede ser null ni blank
	@DisplayName("firstName invalid")
	@ParameterizedTest(name="FirstName: {0}, Error: {1}")
	@CsvSource({"'','ERRORES: firstName: must not be blank, size must be between 2 and 45.'",
		"' ','ERRORES: firstName: must not be blank, size must be between 2 and 45.'",
		"'     ','ERRORES: firstName: must not be blank.'",
		"A,'ERRORES: firstName: size must be between 2 and 45.'",
		"12345678901234567890123456789012345678901234567890,'ERRORES: firstName: size must be between 2 and 45.'"})
	void testNombreIsInvalid(String actorName, String error) {
		var actor = new Actor(0, actorName, "GRILLO");
		assertTrue(actor.isInvalid());
		assertEquals(error, actor.getErrorsMessage());
	}
	
	// 2< |apellido| > 45 && no puede ser null ni blank && tiene que estar todo en mayusculas
	@DisplayName("lastName invalid")
	@ParameterizedTest(name="FirstName: {0}, Error: {1}")
	@CsvSource({
		"'','ERRORES: lastName: must not be blank, size must be between 2 and 45, must be in capital letters.'",
		"' ','ERRORES: lastName: must not be blank, size must be between 2 and 45, must be in capital letters.'",
		"'     ','ERRORES: lastName: must not be blank, must be in capital letters.'",
		"A,'ERRORES: lastName: size must be between 2 and 45.'",
		"a,'ERRORES: lastName: size must be between 2 and 45, must be in capital letters.'",
		"12345678901234567890123456789012345678901234567890,'ERRORES: lastName: must be in capital letters, size must be between 2 and 45.'",
		"Grillo,'ERRORES: lastName: must be in capital letters.'"})
	void testApellidoIsInvalid(String actorSurname, String error) {
		var actor = new Actor(0, "Pepito", actorSurname);
		assertTrue(actor.isInvalid());
		assertEquals(error, actor.getErrorsMessage());
	}

}
