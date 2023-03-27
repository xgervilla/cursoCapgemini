package com.example.ejemplos;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.DisplayNameGeneration;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;

import com.example.core.test.SpaceCamelCase;

/* NIF Válido:
	* Personas físicas:
		* 8 números + letra
		* 8 números (la letra se puede obtener de manera automática)
* NIF Inválido:
	* Personas físicas:
		* <8 números
		* >8 números
		* Letra al principio
		* 8 números + letra PERO que la letra no sea la correcta
		* Caso adicional: mezcla de números y letras
* 
*/

@DisplayNameGeneration(SpaceCamelCase.class)
class ValidadorNIFTest {
	
	@BeforeEach
	void setUp() throws Exception {
		
	}
	
	@Nested
	@DisplayName("NIF válidos")
	class OK {
		
		//8 números (sin letra)
		@ParameterizedTest(name = "NIF: {0}")
		@CsvSource(value = {"49487953", "01234567"})
		void testNIFSinLetra(String nif) {
			var validador = new ValidadorNIF();
			assertTrue(validador.isvalido(nif));
		}
		
		//8 números + letra
		@ParameterizedTest(name = "NIF: {0}")
		@CsvSource(value = {"49487953A"})
		void testNIFConLetra(String nif) {
			var validador = new ValidadorNIF();
			assertTrue(validador.isvalido(nif));
		}
		
	}
	
	@Nested
	@DisplayName("NIF inválidos")
	class KO{
		
		//7 números (con o sin letra)
		@ParameterizedTest(name = "NIF: {0}")
		@CsvSource(value = {"0","11111","1"})
		void testLongitudInferior(String nif) {
			var validador = new ValidadorNIF();
			assertFalse(validador.isvalido(nif));
		}
		
		//>8 números (con o sin letra)
		@ParameterizedTest(name = "NIF: {0}")
		@CsvSource(value = {"123456789"})
		void testLetraLongitudSuperior(String nif) {
		}
		
		//8 números + letra PERO letra no válida
		@ParameterizedTest(name = "NIF: {0}")
		@CsvSource(value = {"49487953R"})
		void testLetraNoValida(String nif) {
			var validador = new ValidadorNIF();
			assertFalse(validador.isvalido(nif));
		}
		
		//letra + 8 números ("orden erróneo")
		@ParameterizedTest(name= "NIF: {0}")
		@CsvSource(value = {"A49487953"})
		void testOrdenInverso(String nif) {
			var validador = new ValidadorNIF();
			assertFalse(validador.isvalido(nif));
		}
		
		//test opcional: combinación de letras y números
		@ParameterizedTest(name= "NIF: {0}")
		@CsvSource(value = {"A4B4C7953"})
		void testNifErroneo(String nif) {
			var validador = new ValidadorNIF();
			assertFalse(validador.isvalido(nif));
		}
	}

}
