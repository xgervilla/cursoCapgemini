package com.example.entities;

import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;

import com.example.domains.entities.Actor;

public class ActorTest {
	@BeforeEach
	void setUp() throws Exception {
		
	}
	
	
	@Nested
	@DisplayName("Personas correctas")
	class OK{
		@ParameterizedTest(name="actorId: {0}, firstName: {1}, lastName: {2}")
		@CsvSource({"0,Juan,PALOMO", "-3,Pinto,POLLO"})
		void testValid(int actorId, String firstName, String lastName) {
			var a = new Actor(actorId, firstName, lastName);
			
			assertNotNull(a);
			
			assertTrue(a instanceof Actor, "No es instancia de actor");
			
			assertTrue(a.isValid());
		}
	}
}
