package com.example;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.annotation.Bean;
import org.springframework.test.context.ContextConfiguration;

import com.example.ioc.StringRepository;
import com.example.ioc.StringRepositoryMockImpl;

@SpringBootTest
class DemoApplicationTests {

	@Autowired
	StringRepository dao;
	
	@Test
	void contextLoads() {
		assertEquals("Soy el StringRepositoryImpl", dao.load());
	}
	
	//Creamos una clase de configuración
	public static class IoCTestConfig{
		@Bean
		StringRepository getServicio() {
			return new StringRepositoryMockImpl();
		}
	}
	
	
	//con el contextConfiguration indicamos que queremos utilizar la clase creada en vez de la clase "que tocaría" (en este caso correspondería a StringRepositoryImpl
	@Nested
	@ContextConfiguration(classes = IoCTestConfig.class)
	class IoCTest {
		@Autowired
		StringRepository dao;
		
		@Test
		void contextLoads(){
			assertEquals("Soy el doble de pruebas de StringRepository", dao.load());
		}
	}

}
