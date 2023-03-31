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
import org.junit.jupiter.params.provider.EmptySource;
import org.junit.jupiter.params.provider.NullSource;

import com.example.core.test.SpaceCamelCase;

import lombok.experimental.PackagePrivate;

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
		
		//8 números (sin letra), 8 números + letra y caso nif = null (mediante NullSource
		@ParameterizedTest(name = "NIF: {0}")
		@CsvSource(value = {"40556524", "01234567", "98238867W", "12345678Z"})
		@NullSource
		void testNIFSinLetra(String nif) {
			var validador = new ValidadorNIF();
			assertTrue(validador.isValido(nif));
		}
		
		//prueba de la función isNotValido con el NIF vacío
		@ParameterizedTest(name="NIF: {0}")
		@EmptySource
		void testNIFNotValid(String nif) {
			var validador = new ValidadorNIF();
			assertTrue(validador.isNotValido(nif));
		}
		
	}
	
	@Nested
	@DisplayName("NIF inválidos")
	class KO{
		
		//<8 números (con o sin letra) -> incluye el caso de nif vacío ""
		@ParameterizedTest(name = "NIF: {0}")
		@CsvSource(value = {"0","11111","4g"})
		void testLongitudInferior(String nif) {
			var validador = new ValidadorNIF();
			assertFalse(validador.isValido(nif));
		}
		
		//>8 números (con o sin letra)
		@ParameterizedTest(name = "NIF: {0}")
		@CsvSource(value = {"123456789", "12345678A10"})
		void testLetraLongitudSuperior(String nif) {
		}
		
		//8 números + letra PERO letra no correcta
		@ParameterizedTest(name = "NIF: {0}")
		@CsvSource(value = {"17137272K", "98795469L"})
		void testLetraNoValida(String nif) {
			var validador = new ValidadorNIF();
			assertFalse(validador.isValido(nif));
		}
		
		//letra + 8 números ("orden erróneo")
		@ParameterizedTest(name= "NIF: {0}")
		@CsvSource(value = {"Z78664821"})
		void testOrdenInverso(String nif) {
			var validador = new ValidadorNIF();
			assertFalse(validador.isValido(nif));
		}
	}

}
