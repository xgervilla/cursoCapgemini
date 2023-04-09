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
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
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


	@Nested
	class GetMethods{
		
		@Nested
		class OK {
			@Test
			@DisplayName("Get all categories")
			void testGetAll() throws Exception {
				
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
			
			@Test
			@DisplayName("Get all categories in page format")
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
				
				mockMvc.perform(get("/api/categorias/v1").queryParam("page", "0"))
					.andExpectAll(
						status().isOk(), 
						content().contentType("application/json"),
						jsonPath("$.content.size()").value(5),
						jsonPath("$.size").value(5));
				}
		
			@Test
			@DisplayName("Get category films")
			void testGetCategoryFilms() throws Exception{
				fail("Not yet implemented");
			}
			
			@Test
			@DisplayName("Get all but empty")
			void testGetAllEmpty() throws Exception {
				
				List<Category> lista = new ArrayList<>(
				        Arrays.asList());
				
				when(srv.getByProjection(Category.class)).thenReturn(lista);
				mockMvc.perform(get("/api/categorias/v1").accept(MediaType.APPLICATION_JSON))
					.andExpectAll(
							status().isOk(), 
							content().contentType("application/json"),
							jsonPath("$.size()").value(0)
							);
			}
			
			@Test
			@DisplayName("Get one category by id")
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
		}
		
		@Nested
		class KO {
			@Test
			@DisplayName("Get one invalid")
			void testGetOneInvalid() throws Exception {
				int id = 1;
				
				when(srv.getOne(id)).thenReturn(Optional.empty());
				mockMvc.perform(get("/api/categorias/v1/{id}", id))
					.andExpect(status().isNotFound())
					.andExpect(jsonPath("$.title").value("Not Found"))
			        .andDo(print());
			}
		}
		
	}

	@Nested
	class PostMethods{
		
		@Test
		@DisplayName("Create new category")
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
		
		@Test
		@DisplayName("Create new category invalid")
		void testCreateInvalid() throws Exception {
			fail("Not yet implemented");
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
	
	@Nested
	class PutMethods{
		
		@Test
		@DisplayName("Update category")
		void testUpdate() throws Exception {
			fail("Not yet implemented");
		}
		
		@Test
		@DisplayName("Update category invalid")
		void testUpdateInvalid() throws Exception {
			fail("Not yet implemented");
		}
	}
	
	@Nested
	class DeleteMethods{
		@Test
		@DisplayName("Delete film")
		void testDelete() throws Exception {
			fail("Not yet implemented");
		}
	}

}
