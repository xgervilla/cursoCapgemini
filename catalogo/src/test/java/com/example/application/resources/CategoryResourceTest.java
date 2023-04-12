package com.example.application.resources;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.header;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.math.BigDecimal;
import java.sql.Timestamp;
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
import com.example.domains.entities.Category;
import com.example.domains.entities.Film;
import com.example.domains.entities.FilmCategory;
import com.example.domains.entities.Language;
import com.example.domains.entities.Film.Rating;
import com.example.exceptions.InvalidDataException;
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
			@DisplayName("Get all films from category")
			void testGetCategoryFilms() throws Exception{
				var id = 1; 
				List<Film> filmList = new ArrayList<>(
				        Arrays.asList(new Film(1, "Description of the first movie", 60, Rating.GENERAL_AUDIENCES, new Short("2023"), (byte) 5, new BigDecimal(10.0), new BigDecimal(30), "The revenge of the test part 1", new Language(1), new Language(2)),
				        			new Film(2, "Description of the second movie", 60, Rating.GENERAL_AUDIENCES, new Short("2023"), (byte) 5, new BigDecimal(10.0), new BigDecimal(30), "The revenge of the test part 2", new Language(1), new Language(2))
				        		));
				
				var category = new Category(id, "Categoria");
				
				List<FilmCategory> filmCategories = new ArrayList<>(
						Arrays.asList(
								new FilmCategory(filmList.get(0), category),
								new FilmCategory(filmList.get(1), category)
								));
						
				
				category.setFilmCategories(filmCategories);
				
				when(srv.getOne(id)).thenReturn(Optional.of(category));
				
				mockMvc.perform(get("/api/categorias/v1/{id}/pelis", id))
					.andExpectAll(
							status().isOk(),
							jsonPath("$.length()").value(2),
							jsonPath("$[0].filmId").value(1),
							jsonPath("$[0].title").value("The revenge of the test part 1"),
							jsonPath("$[1].filmId").value(2),
							jsonPath("$[1].title").value("The revenge of the test part 2")
					).andDo(print());
			}
			
			@Test
			@DisplayName("Novedades")
			void testGetNovedades() throws Exception {
				var timestampString = "2022-12-01 00:00:00";
				var timestamp = Timestamp.valueOf(timestampString);
				List<Category> lista = new ArrayList<>(
				        Arrays.asList(new Category(1, "Drama"),
				        		new Category(2, "Comedy"),
				        		new Category(3, "Action")
				        		));
				
				when(srv.novedades(timestamp)).thenReturn(lista);
				
				mockMvc.perform(get("/api/categorias/v1").queryParam("novedades", timestampString))
					.andExpectAll(
							status().isOk(), 
							content().contentType("application/json"),
							jsonPath("$.length()").value(3),
							jsonPath("$[0].ID").value(1),
							jsonPath("$[0].Category").value("Drama"),
							jsonPath("$[1].ID").value(2),
							jsonPath("$[1].Category").value("Comedy"),
							jsonPath("$[2].ID").value(3),
							jsonPath("$[2].Category").value("Action")
					).andDo(print());
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
			var ele = new Category(id,"Animation");
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
			var ele = new Category(1, "            ");
			
			when(srv.add(ele)).thenThrow(new InvalidDataException());
			
			mockMvc.perform(post("/api/categorias/v1")
				.contentType(MediaType.APPLICATION_JSON)
				.content(objectMapper.writeValueAsString(ele)))
				.andExpect(status().isBadRequest())
				.andExpect(jsonPath("$.title").value("Invalid Data"))
		        .andDo(print());
		}	
	}
	
	
	@Nested
	class PutMethods{
		
		@Test
		@DisplayName("Update category")
		void testUpdate() throws Exception {
			int id = 1;
			var ele = new Category(1,"Animation");
			
			when(srv.modify(ele)).thenReturn(ele);
			
			mockMvc.perform(put("/api/categorias/v1/{id}", id)
				.contentType(MediaType.APPLICATION_JSON)
				.content(objectMapper.writeValueAsString(ele))
				)
				.andExpect(status().isNoContent())
		        .andDo(print())
		        ;
		}
		
		@Test
		@DisplayName("Update category invalid")
		void testUpdateInvalid() throws Exception {
			int id = 1;
			var ele = new Category(id+1,"Animation");
			
			when(srv.modify(ele)).thenReturn(ele);
			
			mockMvc.perform(put("/api/categorias/v1/{id}", id)
				.contentType(MediaType.APPLICATION_JSON)
				.content(objectMapper.writeValueAsString(ele)))
				.andExpect(status().isBadRequest())
				.andExpect(jsonPath("$.title").value("Bad Request"))
		        .andDo(print())
		        ;
		}
	}
	
	@Nested
	class DeleteMethods{
		@Test
		@DisplayName("Delete category")
		void testDelete() throws Exception {
			var id = 1;
			
			doNothing().when(srv).deleteById(id);
			
			mockMvc.perform(delete("/api/categorias/v1/{id}", id))
				.andExpect(status().isNoContent())
		        .andDo(print());
			
			verify(srv,times(1)).deleteById(id);
		}
	}

}
