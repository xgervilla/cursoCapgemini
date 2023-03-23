package com.example.ejemplos;

import static org.junit.jupiter.api.Assertions.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.patch;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.RepeatedTest;
import org.junit.jupiter.api.RepetitionInfo;
import org.junit.jupiter.api.Test;

import lombok.experimental.var;

/*
 * 1- comprobar que se puede crear la clase
 *  * Se dejan "sueltas" para identificar correctamente el fallo, si estas comprobaciones fallan el resto de comprobaciones no se pueden hacer/no tendrán resultados válidos o esperados
 * 		1.1- El objeto creado no es nulo (se ha creado "algo")
 * 		1.2- El obejto creado es del tipo que nos interesa (instance of)
 * 2- comprobar que la inicialización es correcta
 *  * Se pueden agrupar bajo un assertAll ya que si una comprobación falla el resto se pueden realizar, además de permitir identificar los errores de manera más rápida sin tener que repetir caso por caso
 *  * Se pueden combinar con un DoesNotThrow para comprobar además que no saltan excepciones inesperadas
*/

class PersonaTest {

	@BeforeEach
	void setUp() throws Exception {
	}

	@RepeatedTest(value = 5, name = "{displayName} {currentRepetition}/{totalRepetitions}")
	void testCreate(RepetitionInfo repetitionInfo) {
		var p = Persona.builder()
				.id(repetitionInfo.getCurrentRepetition())
				.nombre("Pepito" + repetitionInfo.getCurrentRepetition())
				.apellidos("Grillo" + repetitionInfo.getCurrentRepetition()).build();
		
		assertNotNull(p);
		
		assertTrue(p instanceof Persona, "No es instancia de persona");
		
		//ejemplo para "forzar" el fallo
		//p.setNombre("Juan");
		
		assertAll("Inicialización de la persona", 
				()-> assertEquals(repetitionInfo.getCurrentRepetition(), p.getId(), "Fallo en el ID"),
				//si añadimos un condicional para que la iteración 4 compruebe algo diferente podemos ver que sólo falla esta iteración, el resto no dan fallos
				()-> assertEquals("Pepito" + (repetitionInfo.getCurrentRepetition() % 4 == 0 ? "__4" : repetitionInfo.getCurrentRepetition()), p.getNombre(), "Fallo en el nombre"),
				()-> assertEquals("Grillo" + repetitionInfo.getCurrentRepetition(), p.getApellidos(), "Fallo en el apellido"));
	}

}
