package com.example.application.resources;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.math.BigDecimal;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.web.servlet.MockMvc;

import com.example.application.services.CatalogoService;
import com.example.domains.entities.Category;
import com.example.domains.entities.Language;
import com.example.domains.entities.dtos.ActorDTO;
import com.example.domains.entities.dtos.FilmDTO;
import com.example.domains.entities.dtos.NovedadesDTO;
import com.example.domains.entities.Film.Rating;
import com.fasterxml.jackson.databind.ObjectMapper;

@WebMvcTest(CatalogoResource.class)
class CatalogoResourceTest {

	@Autowired
    private MockMvc mockMvc;
	
	@MockBean
	private CatalogoService srv;

	@Autowired
	ObjectMapper objectMapper;
	
	@BeforeEach
	void setUp() throws Exception {
	}

	@Test
	void testNovedades() throws Exception{
		var timestampString = "2022-01-01 00:00:00";
		
		var timestamp = Timestamp.valueOf(timestampString);
		List<FilmDTO> listaFilms = new ArrayList<>(
		        Arrays.asList(new FilmDTO(1, "Pelicula nueva 1", "Description of the first new movie", 60, Rating.GENERAL_AUDIENCES, new Short("2023"), (byte) 5, new BigDecimal(10.0), new BigDecimal(30), new Language(1), new Language(2)),
		        		new FilmDTO(2, "Pelicula nueva 2", "Description of the second new movie", 72, Rating.GENERAL_AUDIENCES, new Short("2023"), (byte) 5, new BigDecimal(10.0), new BigDecimal(30), new Language(1), new Language(2))
		        		));
		
		List<ActorDTO> listaActores = new ArrayList<>(
		        Arrays.asList(new ActorDTO(1, "Primera","INCORPORACION"),
		        		new ActorDTO(2, "Segunda","ADICION"),
		        		new ActorDTO(3, "Nuevo","BECARIO")
		        		));
		
		List<Category> listaCategories = new ArrayList<>(
		        Arrays.asList(new Category(1, "Drama"),
		        		new Category(2, "Comedy"),
		        		new Category(3, "Action")
		        		));
		
		List<Language> listaLanguages = new ArrayList<>(
		        Arrays.asList(new Language(1, "Spanish"),
		        		new Language(2, "English"),
		        		new Language(3, "French")
		        		));
		
		var novedades = new NovedadesDTO(listaFilms, listaActores, listaCategories, listaLanguages);
		
		when(srv.novedades(timestamp)).thenReturn(novedades);
		
		var result = mockMvc.perform(get("/api/catalogo/v1/novedades").queryParam("fecha", timestampString))
			.andExpectAll(
					status().isOk(), 
					content().contentType("application/json"),
					jsonPath("$.peliculas[0].id").value(1),
					jsonPath("$.peliculas[0].titulo").value("Pelicula nueva 1"),
					jsonPath("$.actores[0].id").value(1),
					jsonPath("$.actores[0].nombre").value("Primera"),
					jsonPath("$.actores[0].apellidos").value("INCORPORACION"),
					jsonPath("$.categorias[0].ID").value(1),
					jsonPath("$.categorias[0].Category").value("Drama"),
					jsonPath("$.lenguajes[0].ID").value(1),
					jsonPath("$.lenguajes[0].Name").value("Spanish")
			).andReturn();
		System.out.println(result.getResponse().getContentAsString());
	}
}
