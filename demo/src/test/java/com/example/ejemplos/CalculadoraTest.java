package com.example.ejemplos;

import static org.junit.jupiter.api.Assertions.*;
import static org.junit.jupiter.api.Assumptions.assumeFalse;
import static org.junit.jupiter.api.Assumptions.assumeTrue;
import static org.mockito.ArgumentMatchers.doubleThat;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import lombok.experimental.var;

class CalculadoraTest {
	Calculadora calc;

	//Instanciación del objeto antes de cada prueba. En caso de querer usar la misma instancia en todas se puede cambiar por @BeforeAll
	@BeforeEach
	void setUp() throws Exception {
		calc = new Calculadora();
	}

	//suma de dos numeros positivos
	@Test
	void testSumaPositivo() {
		
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
		
		//ejecución de la función/método a testear
		var rslt = calc.suma(9,-5);
		
		//valor que espero -- valor obtenido
		assertEquals(4, rslt);
	}
	
	//suma de dos numeros negativos
	@Test
	void testSumaNegativo() {
		
		//ejecución de la función/método a testear
		var rslt = calc.suma(-1,-7);
		
		//valor que espero -- valor obtenido
		assertEquals(-8, rslt);
	}
	
	//suma de dos numeros positivos
	@Test
	void testSumaDecimales() {
		
		//ejecución de la función/método a testear
		var rslt = calc.suma(0.3,1.4);
		
		//valor que espero -- valor obtenido
		assertEquals(1.7, rslt);
		
		/*var rslt = calc.suma(0.2,0.1);
		assertEquals(0.3, rslt);
		valor esperado: 0.3 -- valor obtenido: 0.30000000000000004 --> test fallido*/
	}

	//division de dos numeros enteros --> al implementar una segunda función de dividir con dos enteros como parámetros hay error al dividir enteros si el resultado contiene decimales
	@Test
	void testDividirEnteros() {
		//en caso de dividir dos enteros cuyo resultado NO tenga decimales (4/2 = 2 por ejemplo) cambiar a assertEquals para comprobar que en este caso el resultado SÍ que es el esperado
		//el caso de que la división tenga decimales se produce un error; como el fallo es conocido el test comprueba que el resultado sea NOT equal y así ver que efectivamente no devuelve el resultado esperado
		var rslt = calc.divide(1,2);
		
		assertNotEquals(0.5, rslt);
		
	}

	//division de dos numeros reales
	@Test
	void testDividirReales() {
		double dividendo = -1.0;
		double divisor = 4.0;
		
		/*En caso de dividir números reales por 0 el resultado da +-infinito. Hay varias alternativas para "tratar" el resultado:
		 * assertEquals con los valores Double.POSITIVE_INIFINITY y Double.NEGATIVE_INFINITY
		 * assumeTrue para comprobar que el valor divisor es diferente de 0 --equivalente a-- assumeFalse para comprobar que el valor divisor es igual a 0*/
		//si el divisor es 0 el test no está en condiciones "idóneas" para ejecutar por lo que el test se saltará (NO ES FALLO NI SUCCESS SINO SKIP)
		assumeTrue(divisor != 0.0, "Tried to divide by Zero");

		var rslt = calc.divide(dividendo,divisor);
		
		//Aunque el resultado de infinito al dividir por zero el test se realiza del mismo modo 
		assertEquals(-0.25, rslt);
	}
	
}
