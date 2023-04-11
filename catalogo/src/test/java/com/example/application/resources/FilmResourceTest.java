package com.example.application.resources;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.header;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.math.BigDecimal;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import org.junit.jupiter.api.AfterEach;
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

import com.example.domains.contracts.services.FilmService;
import com.example.domains.entities.Category;
import com.example.domains.entities.Film;
import com.example.domains.entities.Language;
import com.example.domains.entities.Film.Rating;
import com.example.domains.entities.dtos.ElementoDTO;
import com.example.domains.entities.dtos.FilmDTO;
import com.example.domains.entities.dtos.FilmShortDTO;
import com.example.exceptions.InvalidDataException;
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

	@Nested
	class GetMethods{
		
		@Nested
		class OK {
			
			@Test
			@DisplayName("Get all films")
			void testGetAll() throws Exception {
				
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
			
			@Test
			@DisplayName("Get all films in page format")
			void testGetAllPageable() throws Exception {
				List<FilmShortDTO> lista = new ArrayList<>(
				        Arrays.asList(new FilmShortDTO(1, "Pepito Grillo"),
				        		new FilmShortDTO(2, "Carmelo Coton"),
				        		new FilmShortDTO(3, "Capitan Tan")));

				when(srv.getByProjection(PageRequest.of(0, 20), FilmShortDTO.class))
					.thenReturn(new PageImpl<>(lista));
				
				mockMvc.perform(get("/api/films/v1").queryParam("page", "0"))
					.andExpectAll(
						status().isOk(), 
						content().contentType("application/json"),
						jsonPath("$.content.size()").value(3),
						jsonPath("$.size").value(3));
				}

			@Test
			@DisplayName("Get all but empty")
			void testGetAllEmpty() throws Exception {
				
				List<FilmShortDTO> lista = new ArrayList<>(
				        Arrays.asList());
				
				when(srv.getByProjection(FilmShortDTO.class)).thenReturn(lista);
				mockMvc.perform(get("/api/films/v1").accept(MediaType.APPLICATION_JSON))
					.andExpectAll(
							status().isOk(), 
							content().contentType("application/json"),
							jsonPath("$.size()").value(0)
							);
			}
			
			@Test
			@Disabled
			@DisplayName("Get one film in basic format")
			void testGetOneBasic() throws Exception {
				fail("Must be fixed");
				int id = 1;
				var ele = new ElementoDTO<Integer, String>(id, "Basic movie");
				//when(srv.getOne(id)).thenReturn(Optional.of(ele));
				mockMvc.perform(get("/api/peliculas/v1/{id}?basic", id))
					.andExpect(status().isOk())
			        .andExpect(jsonPath("$.key").value(id))
			        .andExpect(jsonPath("$.value").value(ele.getValue()))
			        .andDo(print());
			}
			
			@Test
			@DisplayName("Novedades")
			void testGetNovedades() throws Exception {
				var timestampString = "2022-01-01 00:00:00";
				var timestamp = Timestamp.valueOf(timestampString);
				List<Film> lista = new ArrayList<>(
				        Arrays.asList(new Film(1, "Description of the first new movie", 60, Rating.GENERAL_AUDIENCES, new Short("2023"), (byte) 5, new BigDecimal(10.0), new BigDecimal(30), "Pelicula nueva 1", new Language(1), new Language(2)),
				        		new Film(2, "Description of the second new movie", 72, Rating.GENERAL_AUDIENCES, new Short("2023"), (byte) 5, new BigDecimal(10.0), new BigDecimal(30), "Pelicula nueva 2", new Language(1), new Language(2))
				        		));
				
				when(srv.novedades(timestamp)).thenReturn(lista);
				
				mockMvc.perform(get("/api/films/v1").queryParam("novedades", timestampString))
					.andExpectAll(
							status().isOk(), 
							content().contentType("application/json"),
							jsonPath("$.length()").value(2),
							jsonPath("$[0].filmId").value(1),
							jsonPath("$[0].title").value("Pelicula nueva 1"),
							jsonPath("$[1].filmId").value(2),
							jsonPath("$[1].title").value("Pelicula nueva 2")
					).andDo(print());
			}
			
			@Test
			@DisplayName("Get one film by its id")
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
		}
		
		@Nested
		class KO {
			
			@Test
			@DisplayName("Get one film invalid")
			void testGetOneInvalid() throws Exception {
				int id = 1;
				when(srv.getOne(id)).thenReturn(Optional.empty());
				mockMvc.perform(get("/api/peliculas/v1/{id}", id))
					.andExpect(status().isNotFound())
					.andExpect(jsonPath("$.title").value("Not Found"))
			        .andDo(print());
			}
		}
	}

	@Nested
	class PostMethods{
		
		@Test
		@DisplayName("Create new film")
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
		
		@Test
		@DisplayName("Create new film invalid")
		void testCreateInvalid() throws Exception {
			int id = 1;
			var ele = new Film(1, "           ", 60, Rating.GENERAL_AUDIENCES, new Short("2023"), (byte) 5, new BigDecimal(10.0), new BigDecimal(30), "The revenge of the test part 2", new Language(1), new Language(2));
			when(srv.add(ele)).thenThrow(new InvalidDataException());
			mockMvc.perform(post("/api/peliculas/v1")
				.contentType(MediaType.APPLICATION_JSON)
				.content(objectMapper.writeValueAsString(FilmDTO.from(ele))))
				.andExpect(status().isBadRequest())
				.andExpect(jsonPath("$.title").value("Bad Request"))
		        .andDo(print())
		        ;
		}
	}

	@Nested
	class PutMethods{
		
		@Test
		@DisplayName("Update film")
		void testUpdate() throws Exception {
			int id = 1;
			var ele = new Film(id, "New movie title", 60, Rating.GENERAL_AUDIENCES, new Short("2023"), (byte) 5, new BigDecimal(10.0), new BigDecimal(30), "The revenge of the test part 2", new Language(1), new Language(2));
			when(srv.modify(ele)).thenReturn(ele);
			mockMvc.perform(put("/api/peliculas/v1/{id}", id)
				.contentType(MediaType.APPLICATION_JSON)
				.content(objectMapper.writeValueAsString(FilmDTO.from(ele))))
				.andExpect(status().isNoContent())
		        .andDo(print())
		        ;
		}
		
		@Test
		@DisplayName("Update film invalid")
		void testUpdateInvalid() throws Exception {
			int id = 1;
			var ele = new Film(id, "New description of the movie", 60, Rating.GENERAL_AUDIENCES, new Short("2023"), (byte) 5, new BigDecimal(10.0), new BigDecimal(30), "       ", new Language(1), new Language(2));
			when(srv.modify(ele)).thenThrow(new InvalidDataException());
			mockMvc.perform(put("/api/peliculas/v1/{id}", id)
				.contentType(MediaType.APPLICATION_JSON)
				.content(objectMapper.writeValueAsString(FilmDTO.from(ele))))
				.andExpect(status().isBadRequest())
				.andExpect(jsonPath("$.title").value("Bad Request"))
		        .andDo(print())
		        ;
		}
	}
	
	@Nested
	class DeleteMethods{
		@Test
		@DisplayName("Delete film")
		void testDelete() throws Exception {
			var id = 1;
			
			doNothing().when(srv).deleteById(id);
			
			mockMvc.perform(delete("/api/peliculas/v1/{id}", id))
				.andExpect(status().isNoContent())
		        .andDo(print());
			
			verify(srv,times(1)).deleteById(id);
		}
	}
}
