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

	//division de dos numeros enteros --> al implementar una segunda función de dividir con dos enteros como parámetros hay error al dividir enteros si el resultado contiene decimales
	@Test
	void testDividirEnteros() {
		var calc = new Calculadora();

		//en caso de dividir dos enteros cuyo resultado NO tenga decimales (4/2 = 2 por ejemplo) cambiar a assertEquals para comprobar que en este caso el resultado SÍ que es el esperado
		//el caso de que la división tenga decimales se produce un error; como el fallo es conocido el test comprueba que el resultado sea NOT equal y así ver que efectivamente no devuelve el resultado esperado
		var rslt = calc.divide(1,2);
		
		assertNotEquals(0.5, rslt);
		
	}

	//division de dos numeros reales
	@Test
	void testDividirReales() {
		var calc = new Calculadora();

		var rslt = calc.divide(-1.0,0);
		
		//En caso de dividir números reales por 0 el resultado da +-infinito, por lo que el assertEquals se actualiza a Double.POSITIVE_INIFINITY y Double.NEGATIVE_INFINITY respectivamente en vez de utilizar un valor "tangible"
		
		//Aunque el resultado de infinito al dividir por zero el test se realiza del mismo modo 
		assertEquals(Double.NEGATIVE_INFINITY, rslt);
	}
	
}
