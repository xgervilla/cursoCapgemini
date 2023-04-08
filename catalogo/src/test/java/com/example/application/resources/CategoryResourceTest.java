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

import com.example.domains.contracts.services.CategoryService;
import com.example.domains.contracts.services.LanguageService;
import com.example.domains.entities.Category;
import com.fasterxml.jackson.databind.ObjectMapper;

@WebMvcTest(CategoryResource.class)
class CategoryResourceTest {
	
	@Autowired
    private MockMvc mockMvc;
	
	@MockBean
	private CategoryService srv;

	@Autowired
	ObjectMapper objectMapper;

	@BeforeEach
	void setUp() throws Exception {
	}

	//getAll category as string (json)
	@Test
	void testGetAllString() throws Exception {
		
		List<Category> lista = new ArrayList<>(
		        Arrays.asList(new Category(1, "Drama"),
		        		new Category(2, "Action"),
		        		new Category(3, "Horror"),
		        		new Category(4, "Indie"),
		        		new Category(5, "Comedy")
		        		));
		
		when(srv.getByProjection(Category.class)).thenReturn(lista);
		mockMvc.perform(get("/api/categorias/v1").accept(MediaType.APPLICATION_JSON))
			.andExpectAll(
					status().isOk(), 
					content().contentType("application/json"),
					jsonPath("$.size()").value(5)
					);
	}

	//getAll languages as pageable
	@Test
	void testGetAllPageable() throws Exception {
		List<Category> lista = new ArrayList<>(
		        Arrays.asList(new Category(1, "Drama"),
		        		new Category(2, "Action"),
		        		new Category(3, "Horror"),
		        		new Category(4, "Indie"),
		        		new Category(5, "Comedy")
		        		));

		when(srv.getByProjection(PageRequest.of(0, 20), Category.class))
			.thenReturn(new PageImpl<>(lista));
		
		var result = mockMvc.perform(get("/api/categorias/v1").queryParam("page", "0"))
			.andExpectAll(
				status().isOk(), 
				content().contentType("application/json"),
				jsonPath("$.content.size()").value(5),
				jsonPath("$.size").value(5))
			.andReturn();
		System.out.println(result.getResponse().getContentAsString());
	}

	@Test
	void testGetOne() throws Exception {
		int id = 1;
		var ele = new Category(1,"Animation");
		when(srv.getOne(id)).thenReturn(Optional.of(ele));
		mockMvc.perform(get("/api/categorias/v1/{id}", id))
			.andExpect(status().isOk())
	        .andExpect(jsonPath("$.ID").value(id))
	        .andExpect(jsonPath("$.Category").value(ele.getName()))
	        .andDo(print());
	}
		
	@Test
	void testGetOne404() throws Exception {
		int id = 1;
		
		when(srv.getOne(id)).thenReturn(Optional.empty());
		mockMvc.perform(get("/api/categorias/v1/{id}", id))
			.andExpect(status().isNotFound())
			.andExpect(jsonPath("$.title").value("Not Found"))
	        .andDo(print());
	}

	@Test
	void testCreate() throws Exception {
		int id = 1;
		var ele = new Category(1,"Animation");
		when(srv.add(ele)).thenReturn(ele);
		mockMvc.perform(post("/api/categorias/v1")
			.contentType(MediaType.APPLICATION_JSON)
			.content(objectMapper.writeValueAsString(ele))
			)
			.andExpect(status().isCreated())
	        .andExpect(header().string("Location", "http://localhost/api/categorias/v1/1"))
	        .andDo(print())
	        ;
	}

}
