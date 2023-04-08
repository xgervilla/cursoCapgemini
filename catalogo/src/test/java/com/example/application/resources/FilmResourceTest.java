package com.example.application.resources;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.header;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import com.example.domains.contracts.services.FilmService;
import com.example.domains.entities.Film;
import com.example.domains.entities.Language;
import com.example.domains.entities.Film.Rating;
import com.example.domains.entities.dtos.FilmDTO;
import com.example.domains.entities.dtos.FilmShortDTO;
import com.fasterxml.jackson.databind.ObjectMapper;

@WebMvcTest(FilmResource.class)
class FilmResourceTest {
	@Autowired
    private MockMvc mockMvc;
	
	@MockBean
	private FilmService srv;

	@Autowired
	ObjectMapper objectMapper;
	
	@BeforeEach
	void setUp() throws Exception {
	}

	@AfterEach
	void tearDown() throws Exception {
	}
	
	//getAll actors as string (json)
	@Test
	void testGetAllString() throws Exception {
		
		List<FilmShortDTO> lista = new ArrayList<>(
		        Arrays.asList(new FilmShortDTO(1, "Jurassic Park"),
		        		new FilmShortDTO(2, "Jurassic Park 2"),
		        		new FilmShortDTO(3, "Jurassic Park 3"),
		        		new FilmShortDTO(4, "The empire strikes back"),
		        		new FilmShortDTO(5, "The revenge of the Sith"),
		        		new FilmShortDTO(6, "Superman returns")
		        		));
		
		when(srv.getByProjection(FilmShortDTO.class)).thenReturn(lista);
		mockMvc.perform(get("/api/films/v1").accept(MediaType.APPLICATION_JSON))
			.andExpectAll(
					status().isOk(), 
					content().contentType("application/json"),
					jsonPath("$.size()").value(6)
					);
	}

	//getAll actors as pageable
	@Test
	void testGetAllPageable() throws Exception {
		List<FilmShortDTO> lista = new ArrayList<>(
		        Arrays.asList(new FilmShortDTO(1, "Pepito Grillo"),
		        		new FilmShortDTO(2, "Carmelo Coton"),
		        		new FilmShortDTO(3, "Capitan Tan")));

		when(srv.getByProjection(PageRequest.of(0, 20), FilmShortDTO.class))
			.thenReturn(new PageImpl<>(lista));
		
		var result = mockMvc.perform(get("/api/films/v1").queryParam("page", "0"))
			.andExpectAll(
				status().isOk(), 
				content().contentType("application/json"),
				jsonPath("$.content.size()").value(3),
				jsonPath("$.size").value(3))
			.andReturn();
		System.out.println(result.getResponse().getContentAsString());
	}

	@Test
	void testGetOne() throws Exception {
		int id = 1;
		var ele = new Film(1, "Description of the movie", 60, Rating.GENERAL_AUDIENCES, new Short("2023"), (byte) 5, new BigDecimal(10.0), new BigDecimal(30), "The revenge of the test part 2", new Language(1), new Language(2));
		when(srv.getOne(id)).thenReturn(Optional.of(ele));
		mockMvc.perform(get("/api/peliculas/v1/{id}", id))
			.andExpect(status().isOk())
	        .andExpect(jsonPath("$.id").value(id))
	        .andExpect(jsonPath("$.titulo").value(ele.getTitle()))
	        .andExpect(jsonPath("$.descripcion").value(ele.getDescription()))
	        .andDo(print());
	}
	
	@Test
	void testGetOne404() throws Exception {
		int id = 1;
		var ele = new Film(1, "Description of the movie", 60, Rating.GENERAL_AUDIENCES, new Short("2023"), (byte) 5, new BigDecimal(10.0), new BigDecimal(30), "The revenge of the test part 2", new Language(1), new Language(2));
		when(srv.getOne(id)).thenReturn(Optional.empty());
		mockMvc.perform(get("/api/peliculas/v1/{id}", id))
			.andExpect(status().isNotFound())
			.andExpect(jsonPath("$.title").value("Not Found"))
	        .andDo(print());
	}

	@Test
	void testCreate() throws Exception {
		int id = 1;
		var ele = new Film(1, "Description of the movie", 60, Rating.GENERAL_AUDIENCES, new Short("2023"), (byte) 5, new BigDecimal(10.0), new BigDecimal(30), "The revenge of the test part 2", new Language(1), new Language(2));
		when(srv.add(ele)).thenReturn(ele);
		mockMvc.perform(post("/api/peliculas/v1")
			.contentType(MediaType.APPLICATION_JSON)
			.content(objectMapper.writeValueAsString(FilmDTO.from(ele)))
			)
			.andExpect(status().isCreated())
	        .andExpect(header().string("Location", "http://localhost/api/peliculas/v1/1"))
	        .andDo(print())
	        ;
	}

}
