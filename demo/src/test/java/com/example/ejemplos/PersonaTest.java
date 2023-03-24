package com.example.ejemplos;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.patch;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.RepeatedTest;
import org.junit.jupiter.api.RepetitionInfo;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;
import org.mockito.Mock;

import com.example.core.test.Smoke;
import com.example.ioc.PersonaRepository;

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
	
	//Tests básicos (versión inicial de Persona sin control de valores)
	@Nested
	@DisplayName("Inicialización básica")
	class Basic {
		
		@RepeatedTest(value = 5, name = "{displayName} {currentRepetition}/{totalRepetitions}")
		void testCreate(RepetitionInfo repetitionInfo) {
			var p = Persona.builder()
					.id(repetitionInfo.getCurrentRepetition())
					.nombre("Pepito" + repetitionInfo.getCurrentRepetition())
					.apellidos("Grillo" + repetitionInfo.getCurrentRepetition()).build();
			
			assertNotNull(p);
			
			assertTrue(p instanceof Persona, "No es instancia de persona");
			
			assertAll("Inicialización de la persona", 
					()-> assertEquals(repetitionInfo.getCurrentRepetition(), p.getId(), "Fallo en el ID"),
					()-> assertEquals("Pepito" + repetitionInfo.getCurrentRepetition(), p.getNombre(), "Fallo en el nombre"),
					()-> assertEquals("Grillo" + repetitionInfo.getCurrentRepetition(), p.getApellidos().get(), "Fallo en el apellido"));
					//en la version inicial de Persona para obtener el apellido se utiliza p.getApellidos() ya que el return es de tipo String, no Optional<String>
		}
	}

	//Tests complejos (segunda versión de Persona con control de valores no nulos y con longitud mínima)
	@Nested
	@DisplayName("Incialización compleja")
	class Controlled {
		
		//Tests con valores "correctos" (válidos y esperables en condiciones normales)
		@Nested
		class OK {
			@ParameterizedTest(name = "Id: {0}, {1} {2}")
			@Smoke
			@CsvSource(value = {"13,Juan,Sanchez", "9,Marcos,López", "19999994,Lionel,Messi", "010,Ansu,Fati","999999999,Carolina,Muñoz"})
			void testPersonaCompleta(int id, String nombre, String apellidos) {
				var p = Persona.builder().id(id).nombre(nombre).apellidos(apellidos).build();
				
				assertNotNull(p);
				
				assertTrue(p instanceof Persona, "No es instancia de persona");
				
				assertAll("Inicialización de la persona", 
						()-> assertEquals(id, p.getId(), "Fallo en el ID"),
						()-> assertEquals(nombre, p.getNombre(), "Fallo en el nombre"),
						()-> assertEquals(apellidos, p.getApellidos().get(), "Fallo en el apellido"));
			}

			
			@ParameterizedTest(name = "Id: {0}, {1} {2}")
			@Smoke
			@CsvSource(value = {"3765,Juan Carlos", "1234,Penelope", "9876,Scarlet", "2012,IronMan"})
			void testPersonaSinApellido(int id, String nombre) {
				var p = new Persona(id, nombre);
				
				assertNotNull(p);
				
				assertTrue(p instanceof Persona, "No es instancia de persona");
				
				assertAll("Inicialización de la persona", 
						()-> assertEquals(id, p.getId(), "Fallo en el ID"),
						()-> assertEquals(nombre, p.getNombre(), "Fallo en el nombre"));
			}
		}
		
		//Tests con valores no válidos (si por cualquier motivo se llegasen a utilizar se produce un fallo)
		@Nested
		class KO {
			
			@ParameterizedTest(name = "Id: {0}, {1} {2}")
			@Smoke
			@CsvSource(value = {"9001,Po,Llofrito", "3333,MC,Donalds","1234,Evaris,To", "9876,Pimpine,La", "122,null,pop", "12,Pop,apellido_vacio", "13,Pulpo,nombre_vacio"})
			void testPersonaLongitudNombreApellido(int id, String nombre, String apellidos) {
				if("null".equals(nombre)) {
					assertThrows(IllegalArgumentException.class, ()-> new Persona(id, null, apellidos));
				}
				else if ("apellido_vacio".equals(apellidos)){
					assertThrows(IllegalArgumentException.class, ()-> new Persona(id, nombre, ""));
				}
				else if ("nombre_vacio".equals(apellidos)){
					assertThrows(IllegalArgumentException.class, ()-> new Persona(id, "", apellidos));
				}
				else {
					assertThrows(IllegalArgumentException.class, ()-> new Persona(id, nombre, apellidos));
				}
			}
			
			//Test de Persona con ID invalido -> Illegal Argument Exception si es < 0
			@ParameterizedTest(name = "Id: {0}, {1} {2}")
			@Smoke
			@CsvSource(value = {"-8891,Vinicius,JR"})
			//"9999999999999,Kun,Aguero" 	--> 	es ID no válido porque supera el valor soportado por el tipo int pero es un fallo de nivel "superior" (especificación de los datos, si el rango de valores está dentro de lo que permite int entonces utilizar un valor superior no será válido ni esperado)
			void testPersonaIdInvalido(int id, String nombre, String apellidos) {	
				assertThrows(IllegalArgumentException.class, ()-> new Persona(id, nombre, apellidos));
			}
		}
			
		//Tests ocn un mock de persona
		@Nested
		@Smoke
		class PersonaRepositoryTest {
			@Mock
			PersonaRepository dao;
			
			@Test
			void testLoad() {
				PersonaRepository dao = mock(PersonaRepository.class);
				when(dao.load()).thenReturn(Persona.builder().id(1).nombre("Pepito").apellidos("Grillo").build());
				var p = dao.load();
				
				assertNotNull(p);
				
				assertTrue(p instanceof Persona, "No es instancia de persona");
				
				assertAll("Inicialización de la persona", 
						()-> assertEquals(1, p.getId(), "Fallo en el ID"),
						()-> assertEquals("Pepito", p.getNombre(), "Fallo en el nombre"),
						()-> assertEquals("Grillo", p.getApellidos().get(), "Fallo en el apellido"));
			}
		}
	}

}
