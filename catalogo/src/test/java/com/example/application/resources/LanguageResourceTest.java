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
import org.junit.jupiter.api.Disabled;
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

import com.example.domains.contracts.services.LanguageService;
import com.example.domains.entities.Category;
import com.example.domains.entities.Film;
import com.example.domains.entities.FilmCategory;
import com.example.domains.entities.Language;
import com.example.domains.entities.Film.Rating;
import com.example.domains.entities.dtos.FilmShortDTO;
import com.example.exceptions.InvalidDataException;
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

	@Nested
	class GetMethods{
		
		@Nested
		class OK{

			@Test
			@DisplayName("Get all languages")
			void testGetAll() throws Exception {
				
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
			
			@Test
			@DisplayName("Get all languages in page format")
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
			@DisplayName("Novedades")
			void testGetNovedades() throws Exception {
				var timestampString = "2022-09-23 00:00:00";
				var timestamp = Timestamp.valueOf(timestampString);
				List<Language> lista = new ArrayList<>(
				        Arrays.asList(new Language(1, "Spanish"),
				        		new Language(2, "English"),
				        		new Language(3, "French")
				        		));
				
				when(srv.novedades(timestamp)).thenReturn(lista);
				
				mockMvc.perform(get("/api/lenguajes/v1").queryParam("novedades", timestampString))
					.andExpectAll(
							status().isOk(), 
							content().contentType("application/json"),
							jsonPath("$.length()").value(3),
							jsonPath("$[0].ID").value(1),
							jsonPath("$[0].Name").value("Spanish"),
							jsonPath("$[1].ID").value(2),
							jsonPath("$[1].Name").value("English"),
							jsonPath("$[2].ID").value(3),
							jsonPath("$[2].Name").value("French")
					).andDo(print());
			}
			
			@Test
			@DisplayName("Get all films from language")
			void testGetLanguageFilms() throws Exception{
				var id = 1; 
				List<Film> filmList = new ArrayList<>(
				        Arrays.asList(new Film(1, "Description of the first movie", 60, Rating.GENERAL_AUDIENCES, new Short("2023"), (byte) 5, new BigDecimal(10.0), new BigDecimal(30), "The revenge of the test part 1", new Language(1), new Language(2)),
				        			new Film(2, "Description of the second movie", 60, Rating.GENERAL_AUDIENCES, new Short("2023"), (byte) 5, new BigDecimal(10.0), new BigDecimal(30), "The revenge of the test part 2", new Language(1), new Language(2))
				        		));

				var language = new Language(id, "Lenguaje");
				language.addFilm(filmList.get(0));
				language.addFilm(filmList.get(1));
				
				when(srv.getOne(id)).thenReturn(Optional.of(language));
				
				mockMvc.perform(get("/api/lenguajes/v1/{id}/pelis", id))
					.andExpectAll(
							status().isOk(),
							jsonPath("$[0].filmId").value(1),
							jsonPath("$[0].title").value("The revenge of the test part 1"),
							jsonPath("$[1].filmId").value(2),
							jsonPath("$[1].title").value("The revenge of the test part 2")
					).andDo(print());
			}

			@Test
			@DisplayName("Get one language by its id")
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
			@DisplayName("Get all but empty")
			void testGetAllEmpty() throws Exception {
				
				List<Language> lista = new ArrayList<>(
				        Arrays.asList());
				
				when(srv.getByProjection(Language.class)).thenReturn(lista);
				mockMvc.perform(get("/api/lenguajes/v1").accept(MediaType.APPLICATION_JSON))
					.andExpectAll(
							status().isOk(), 
							content().contentType("application/json"),
							jsonPath("$.size()").value(0)
							);
			}
			
		}
		
		@Nested
		class KO{
			@Test
			void testGetOneInvalid() throws Exception {
				int id = 1;
				
				when(srv.getOne(id)).thenReturn(Optional.empty());
				mockMvc.perform(get("/api/languages/v1/{id}", id))
					.andExpect(status().isNotFound())
					.andExpect(jsonPath("$.title").value("Not Found"))
			        .andDo(print());
			}
		}
	}
		
	@Nested
	class PostMethods{
		@Test
		@DisplayName("Create new language")
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
		
		@Test
		@DisplayName("Create new language invalid")
		void testCreateInvalid() throws Exception {
			int id = 1;
			var ele = new Language(1,"     ");
			when(srv.add(ele)).thenThrow(new InvalidDataException());
			mockMvc.perform(post("/api/languages/v1")
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
		@DisplayName("Update language")
		void testUpdate() throws Exception {
			int id = 1;
			var ele = new Language(1,"Portuguese");
			
			when(srv.modify(ele)).thenReturn(ele);
			
			mockMvc.perform(put("/api/lenguajes/v1/{id}", id)
				.contentType(MediaType.APPLICATION_JSON)
				.content(objectMapper.writeValueAsString(ele)))
				.andExpect(status().isNoContent())
		        .andDo(print())
		        ;
		}
		
		@Test
		@DisplayName("Update language invalid")
		void testUpdateInvalid() throws Exception {
			int id = 1;
			var ele = new Language(id+1,"Animation");
			
			when(srv.modify(ele)).thenReturn(ele);
			
			mockMvc.perform(put("/api/lenguajes/v1/{id}", id)
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
		@DisplayName("Delete language")
		void testDelete() throws Exception {
			var id = 1;
			
			doNothing().when(srv).deleteById(id);
			
			mockMvc.perform(delete("/api/lenguajes/v1/{id}", id))
				.andExpect(status().isNoContent())
		        .andDo(print());
			
			verify(srv,times(1)).deleteById(id);
		}
	}

}
