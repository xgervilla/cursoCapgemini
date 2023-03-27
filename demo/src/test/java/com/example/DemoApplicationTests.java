package com.example;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.context.TestConfiguration;
import org.springframework.context.annotation.Bean;
import org.springframework.test.context.ContextConfiguration;

import com.example.ioc.StringRepository;
import com.example.ioc.StringRepositoryMockImpl;

import lombok.experimental.var;

@SpringBootTest(properties= {"mi.valor=Falso"})
class DemoApplicationTests {

	@Autowired
	StringRepository dao;
	
	@Test
	void contextLoads() {
		assertEquals("Soy el StringRepositoryImpl", dao.load());
	}
	
	@Value("${mi.valor:(Sin valor)}")
	private String config;
	@Test
	void valueLoads() {
		assertEquals("Valor por defecto",config);
	}
	
	//Creamos una clase de configuración (mock o clase especial para modificar el comportamiento normal --> cambios entre test e integración por ejemplo)
	public static class IoCTestConfig{
		@Bean
		StringRepository getServicio() {
			return new StringRepositoryMockImpl();
		}
	}
	
	//Una misma clase se puede reutilizar varias veces mediante ContextConfiguration (si
	
	//con el contextConfiguration indicamos que queremos utilizar la clase creada en vez de la clase "que tocaría" (en este caso correspondería a StringRepositoryImpl)
	@Nested
	@ContextConfiguration(classes = IoCTestConfig.class)
	class IoCTest {
		@Autowired
		StringRepository dao;
		
		@Test
		void contextLoads(){
			assertEquals("Soy el doble de prueba de StringRepositoryImpl", dao.load());
		}
	}
	
	//Si en cambio se quiere utilizar la clase en un único sitio es mejor utilizar una configuración a nivel de test (no se puede reaprovechar la clase en varios tests)
	
	@Nested
	class IoCUnicoTest {
		@TestConfiguration
		public static class IoCParticularTestConfig {
			@Bean
			StringRepository getServicio() {
				return new StringRepositoryMockImpl();
			}
		}
		
		@Autowired
		StringRepository dao;
		
		@Test
		void contextLoads() {
			assertEquals("Soy el doble de prueba de StringRepositoryImpl", dao.load());
		}
	}
}
