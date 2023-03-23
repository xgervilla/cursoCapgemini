package com.example.ejemplos;

import static org.junit.jupiter.api.Assertions.*;
import static org.junit.jupiter.api.Assumptions.assumeFalse;
import static org.junit.jupiter.api.Assumptions.assumeTrue;
import static org.mockito.ArgumentMatchers.doubleThat;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.DisplayNameGeneration;
import org.junit.jupiter.api.DisplayNameGenerator;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;
import org.junit.platform.commons.annotation.Testable;

import lombok.experimental.var;

class CalculadoraTest {
	Calculadora calc;

	//Instanciación del objeto antes de cada prueba. En caso de querer usar la misma instancia en todas se puede cambiar por @BeforeAll
	@BeforeEach
	void setUp() throws Exception {
		calc = new Calculadora();
	}
	
	//Clase interna para agrupar métodos

	@Nested
	@DisplayName("Pruebas del método suma")
	//@DisplayNameGeneration(DisplayNameGenerator.ReplaceUnderscores.class)
	class Suma {
		
		//Tests válidos
		@Nested
		class OK {
			//suma de dos numeros positivos
			@Test
			void testSumaPositivo() {
				
				//ejecución de la función/método a testear
				var rslt = calc.suma(4,5);
				
				//valor que espero -- valor obtenido
				assertEquals(9, rslt);
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
			
			//suma de dos enteros con decimales
			@Test
			void testSumaDecimales() {
				
				//ejecución de la función/método a testear
				var rslt = calc.suma(0.2,0.1);
				
				//valor que espero -- valor obtenido
				assertEquals(0.3, rslt);
			}
			
			@ParameterizedTest(name = "{0} + {1} = {2}")
			@DisplayName("Test de suma parametrizado")
			@CsvSource(value = {"1,1,2", "0.1,0.2,0.3", "-1,-1,2", "-1.9,0.7,-1.2"})
			//-1 + -1 = -2 ->  aunque el test de fallo el resto de tests si que se ejecutan
			void testSumaParametrizado(double operand1, double operand2, double result) {
				assertEquals(result, calc.suma(operand1, operand2));
			}
			
		}

		//Tests inválidos
		@Nested
		class KO {
		}
		
	}
	
	@Nested
	@DisplayName("Pruebas del método divide")
	class Divide {
		
		//Tests válidos
		@Nested
		class OK{
			//division de dos numeros enteros --> Si el resultado no da decimal es correcto
			@Test
			void testDividirEnteros() {
				var rslt = calc.divide(4,2);
				
				assertEquals(2, rslt);
				
			}
			
			//division de dos numeros reales
			@Test
			void testDividirReales() {
				double dividendo = -1.0;
				double divisor = 4.0;
			
				assertEquals(-0.25, calc.divide(dividendo, divisor));
			}
			
		}
		
		//Tests inválidos
		@Nested
		class KO {
			//division de dos numeros reales
			@Test
			void testDividirPorCero() {
				double dividendo = -1.0;
				double divisor = 0.0;
				
				
				/*En caso de dividir números reales por 0 el resultado da +-infinito. Hay varias alternativas para "tratar" el resultado:
				 * modificar la clase para que lance una excepción al intentar dividir por cero
				 * assumeTrue para comprobar que el valor divisor es diferente de 0 --equivalente a-- assumeFalse para comprobar que el valor divisor es igual a 0
				 * assertEquals con los valores Double.POSITIVE_INIFINITY y Double.NEGATIVE_INFINITY
				 * assertTrue comprobando que el resultado es infinito
				 */
				
				//Opción 1: modificar la clase
				assertThrows(ArithmeticException.class, () -> calc.divide(dividendo,divisor));
				
				/* Sólo habilitar si no se envía excepción desde la clase Calculadora
				//Opción 2: Asume que el divisor es diferente de cero
				assumeTrue(divisor != 0.0, "Tried to divide by Zero");

				var rslt = calc.divide(dividendo,divisor);
				//Opción 3: Equals con infinity
				assertEquals(Double.NEGATIVE_INFINITY, rslt);
				//Opción 4: el resultado es infinito
				assertTrue(Double.isInfinite(rslt));
				*/
			}
		}
	}
	
}
