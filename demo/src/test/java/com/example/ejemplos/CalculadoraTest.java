package com.example.ejemplos;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import lombok.experimental.var;

class CalculadoraTest {

	@BeforeEach
	void setUp() throws Exception {
	}

	//suma de dos numeros positivos
	@Test
	void testSumaPositivo() {
		//instanciación del objeto a testear
		var calc = new Calculadora();
		
		//ejecución de la función/método a testear
		var rslt = calc.suma(0.3,1.4);
		
		//valor que espero -- valor obtenido
		assertEquals(1.7, rslt);
		
		//si el valor esperado es diferente al obtenido el test falla
		//assertEquals(5, rslt);
	}
	
	//suma de un numero positivo y otro negativo
	@Test
	void testSumaPositivoNegativo() {
		//instanciación del objeto a testear
		var calc = new Calculadora();
		
		//ejecución de la función/método a testear
		var rslt = calc.suma(9,-5);
		
		//valor que espero -- valor obtenido
		assertEquals(4, rslt);
	}
	
	//suma de dos numeros negativos
	@Test
	void testSumaNegativo() {
		//instanciación del objeto a testear
		var calc = new Calculadora();
		
		//ejecución de la función/método a testear
		var rslt = calc.suma(-1,-7);
		
		//valor que espero -- valor obtenido
		assertEquals(-8, rslt);
	}
	
	//suma de dos numeros positivos
	@Test
	void testSumaDecimales() {
		//instanciación del objeto a testear
		var calc = new Calculadora();
		
		//ejecución de la función/método a testear
		var rslt = calc.suma(0.3,1.4);
		
		//valor que espero -- valor obtenido
		assertEquals(1.7, rslt);
		
		/*var rslt = calc.suma(0.2,0.1);
		assertEquals(0.3, rslt);
		valor esperado: 0.3 -- valor obtenido: 0.30000000000000004 --> test fallido*/
	}

}
