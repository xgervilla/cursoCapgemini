package com.example.ejemplos;

import static org.junit.jupiter.api.Assertions.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.patch;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import lombok.experimental.var;

/*
 * 1- comprobar que se puede crear la clase
 * 		1.1- El objeto creado no es nulo (se ha creado "algo")
 * 		1.2- El obejto creado es del tipo que nos interesa (instance of)*/

class PersonaTest {

	@BeforeEach
	void setUp() throws Exception {
	}

	@Test
	void testCreate() {
		var p = Persona.builder().id(1).nombre("Pepito").apellidos("Grillo").build();
		
		assertNotNull(p);
		
		assertTrue(p instanceof Persona, "No es instancia de persona");
	}

}
