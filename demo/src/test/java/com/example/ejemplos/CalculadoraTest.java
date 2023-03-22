package com.example.ejemplos;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import lombok.experimental.var;

class CalculadoraTest {

	@BeforeEach
	void setUp() throws Exception {
	}

	@Test
	void testSuma() {
		//instanciación del objeto a testear
		var calc = new Calculadora();
		
		//ejecución de la función/método a testear
		var rslt = calc.suma(2,2);
		
		//valor que espero -- valor obtenido
		assertEquals(4, rslt);
		
		//si no el valor esperado es diferente al obtenido el test falla
		//assertEquals(5, rslt);
	}

}
