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

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import com.example.domains.contracts.services.LanguageService;
import com.example.domains.entities.Language;
import com.fasterxml.jackson.databind.ObjectMapper;

@WebMvcTest(LanguageResource.class)
class LanguageResourceTest {
	
	@Autowired
    private MockMvc mockMvc;
	
	@MockBean
	private LanguageService srv;

	@Autowired
	ObjectMapper objectMapper;

	@BeforeEach
	void setUp() throws Exception {
	}

	//getAll actors as string (json)
	@Test
	void testGetAllString() throws Exception {
		
		List<Language> lista = new ArrayList<>(
		        Arrays.asList(new Language(1, "Spanish"),
		        		new Language(2, "English"),
		        		new Language(3, "German"),
		        		new Language(4, "French"),
		        		new Language(5, "Italian"),
		        		new Language(6, "Russian")
		        		));
		
		when(srv.getByProjection(Language.class)).thenReturn(lista);
		mockMvc.perform(get("/api/languages/v1").accept(MediaType.APPLICATION_JSON))
			.andExpectAll(
					status().isOk(), 
					content().contentType("application/json"),
					jsonPath("$.size()").value(6)
					);
	}

	//getAll languages as pageable
	@Test
	void testGetAllPageable() throws Exception {
		List<Language> lista = new ArrayList<>(
		        Arrays.asList(new Language(1, "Spanish"),
		        		new Language(2, "English"),
		        		new Language(3, "German"),
		        		new Language(4, "French"),
		        		new Language(5, "Italian"),
		        		new Language(6, "Russian")
		        		));

		when(srv.getByProjection(PageRequest.of(0, 20), Language.class))
			.thenReturn(new PageImpl<>(lista));
		
		var result = mockMvc.perform(get("/api/lenguajes/v1").queryParam("page", "0"))
			.andExpectAll(
				status().isOk(), 
				content().contentType("application/json"),
				jsonPath("$.content.size()").value(6),
				jsonPath("$.size").value(6))
			.andReturn();
		System.out.println(result.getResponse().getContentAsString());
	}

	@Test
	void testGetOne() throws Exception {
		int id = 1;
		var ele = new Language(1,"Catalan");
		when(srv.getOne(id)).thenReturn(Optional.of(ele));
		mockMvc.perform(get("/api/lenguajes/v1/{id}", id))
			.andExpect(status().isOk())
	        .andExpect(jsonPath("$.ID").value(id))
	        .andExpect(jsonPath("$.Name").value(ele.getName()))
	        .andDo(print());
	}
		
	@Test
	void testGetOne404() throws Exception {
		int id = 1;
		
		when(srv.getOne(id)).thenReturn(Optional.empty());
		mockMvc.perform(get("/api/languages/v1/{id}", id))
			.andExpect(status().isNotFound())
			.andExpect(jsonPath("$.title").value("Not Found"))
	        .andDo(print());
	}

	@Test
	void testCreate() throws Exception {
		int id = 1;
		var ele = new Language(1,"Catalan");
		when(srv.add(ele)).thenReturn(ele);
		mockMvc.perform(post("/api/languages/v1")
			.contentType(MediaType.APPLICATION_JSON)
			.content(objectMapper.writeValueAsString(ele))
			)
			.andExpect(status().isCreated())
	        .andExpect(header().string("Location", "http://localhost/api/languages/v1/1"))
	        .andDo(print())
	        ;
	}

}
