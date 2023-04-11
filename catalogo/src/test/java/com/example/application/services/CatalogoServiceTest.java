package com.example.application.services;

import static org.junit.jupiter.api.Assertions.*;

import java.sql.Timestamp;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.annotation.ComponentScan;

@SpringBootTest
@ComponentScan(basePackages = "com.example")
class CatalogoServiceTest {
	
	@Autowired
	CatalogoServiceImpl srv;

	@BeforeEach
	void setUp() throws Exception {
	}

	@Test
	void testNovedades() {
		var timestamp = Timestamp.valueOf("2006-01-01 00:00:00");
		var result = srv.novedades(timestamp);
		assertAll("Novedades correctas",
				() -> assertTrue(result.getFilms().size() >= 1000, "Failed films"),
				() -> assertTrue(result.getActors().size() >= 200, "Failed actors"),
				() -> assertTrue(result.getCategories().size() >= 16, "Failed categories"),
				() -> assertTrue(result.getLanguages().size() >= 8, "Failed languages")
				);
	}

}
