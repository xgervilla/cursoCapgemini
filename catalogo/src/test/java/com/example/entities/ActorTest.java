package com.example.entities;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;

import com.example.domains.entities.Actor;

public class ActorTest {
	@BeforeEach
	void setUp() throws Exception {
		
	}
	
	@Test
	void testIsValid() {
		var fixture = new Actor(0,"Pepito","GRILLO");
		assertTrue(fixture.isValid());
	}
	
	
	@DisplayName("size must be between 2 and 45 and must not be blank.")
	@ParameterizedTest(name="FirstName: {0}, Error: {1}")
	@CsvSource({"'','ERRORES: firstName: must not be blank, size must be between 2 and 45.'",
		"' ','ERRORES: firstName: size must be between 2 and 45, must not be blank.'",
		"'     ','ERRORES: firstName: must not be blank.'",
		"A,'ERRORES: firstName: size must be between 2 and 45.'",
		"12345678901234567890123456789012345678901234567890,'ERRORES: firstName: size must be between 2 and 45.'"})
	void testNombreIsInvalid(String actorName, String error) {
		var actor = new Actor(0, actorName, "GRILLO");
		assertTrue(actor.isInvalid());
		assertEquals(error, actor.getErrorsMessage());
	}
}