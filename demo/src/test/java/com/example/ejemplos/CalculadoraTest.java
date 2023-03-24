package com.example.ejemplos;

import static org.junit.jupiter.api.Assertions.*;
import static org.junit.jupiter.api.Assumptions.assumeFalse;
import static org.junit.jupiter.api.Assumptions.assumeTrue;
import static org.mockito.ArgumentMatchers.doubleThat;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.DisplayNameGeneration;
import org.junit.jupiter.api.DisplayNameGenerator;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInfo;
import org.junit.jupiter.api.TestReporter;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;
import org.junit.platform.commons.annotation.Testable;

import com.example.core.test.Smoke;
import com.example.core.test.SpaceCamelCase;

import lombok.experimental.var;

@DisplayNameGeneration(SpaceCamelCase.class)
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
	class Suma {
		
		//Tests válidos
		@Nested
		class OK {
			
			@Test
			//@Smoke
			@DisplayName("Cotilla")
			void cotilla(TestInfo testInfo, TestReporter testReporter) {
				assertEquals("Cotilla", testInfo.getDisplayName());
				assertTrue(testInfo.getTags().contains("Smoke"));
				for(String tag: testInfo.getTags()) testReporter.publishEntry(tag);
			}
			
			//Disabled para no realizarlo, ya se considera de manera similar en el caso de decimales (evitamos repetir la prueba cada vez que ejecutamos pero se puede reactivar en cualquier momento.
			//suma de dos numeros positivos
			@Test
			@Disabled
			void testSumaPositivo() {
				
				//ejecución de la función/método a testear
				var rslt = calc.suma(4,5);
				
				//valor que espero -- valor obtenido
				assertEquals(9, rslt);
			}
			
			//suma de un numero positivo y otro negativo
			@Test
			//@Smoke
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
			@CsvSource(value = {"1,1,2", "0.1,0.2,0.3", "-1,-1,-2", "-1.9,0.7,-1.2"})
			void testSumaParametrizado(double operand1, double operand2, double result) {
				assertEquals(result, calc.suma(operand1, operand2));
			}
			
			//Test con un mock para ver el comportamiento del mock
			@Test
			@Smoke
			void testSumaMock() {
				Calculadora calc = mock(Calculadora.class);
				//declaramos que cuando se ejecute la suma con los parámetros 2,2 devuelva siempre el valor 3.0
				when(calc.suma(2,2)).thenReturn(3.0);
				//al realizar la operación vemos que el resultado devuelto es igual a 3 (el valor que le hemos dicho que devuelva siempre) -> en condiciones "funcionales" devolvería 4 pero mockeamos la clase para poder simular su comportamiento
				assertEquals(3, calc.suma(2, 2));
			}
			
		}

		//Tests inválidos
		@Nested
		class KO {
			/* Dividir dos enteros no se considera test inválido ya que es un escenario plausible, el problema se encuentra en la ambigüedad de las funciones de la clase Calculadora
			 * Se debería controlar desde la clase lanzando una excepción cuando el módulo es diferente de cero o eliminando directamente la función ambigua para hacer que se consideren en la función con doubles como parámetros
			 */
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
				 * assertEquals con los valores Double.POSITIVE_INIFINITY y Double.NEGATIVE_INFINITY
				 * assertTrue comprobando que el resultado es infinito
				 */
				
				//Opción 1: modificar la clase
				assertThrows(ArithmeticException.class, () -> calc.divide(dividendo,divisor));
				
				/* LA SEGUNDA OPCIÓN NO ES VÁLIDA
				 * Sí que "previene" dividir por cero pero no es un buen uso de assumeTrue, que se debe utilizar para asegurar que el test se realiza en las condiciones correctas.
				 * En este caso el test está en condiciones correctas pero con valores que requieren una gestión especial, por lo que sí que se deería ejecutar y detectar fallo o success y no hacer skip
				//Opción 2: Asume que el divisor es diferente de cero
				assumeTrue(divisor != 0.0, "Tried to divide by Zero");
				 */
				
				/* Sólo habilitar si no se envía excepción desde la clase Calculadora

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
