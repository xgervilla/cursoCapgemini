package com.example.application.resources;

import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import java.math.BigDecimal;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import org.junit.jupiter.api.AfterEach;
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

import com.example.domains.contracts.services.ActorService;
import com.example.domains.entities.Actor;
import com.example.domains.entities.Film;
import com.example.domains.entities.Language;
import com.example.domains.entities.Film.Rating;
import com.example.domains.entities.FilmActor;
import com.example.domains.entities.dtos.ActorDTO;
import com.example.domains.entities.dtos.ActorShort;
import com.example.exceptions.InvalidDataException;
import com.fasterxml.jackson.databind.ObjectMapper;

import lombok.Value;

@WebMvcTest(ActorResource.class)
class ActorResourceTest {
	
	@Autowired
    private MockMvc mockMvc;
	
	@MockBean
	private ActorService srv;
	
	@Autowired
	ObjectMapper objectMapper;
	
	@BeforeEach
	void setUp() throws Exception {
	}

	@AfterEach
	void tearDown() throws Exception {
	}
	
	//como ActorShort es un interface sin clase de implementación la creamos de manera auxiliar para realizar los tests
	@Value
	static class ActorShortMock implements ActorShort {
		int actorId;
		String nombre;
	}
	
	@Nested
	class GetMethods{
		@Nested
		class OK{
			
			@Test
			@DisplayName("Get all actors")
			void testGetAll() throws Exception {
				
				List<ActorShort> lista = new ArrayList<>(
				        Arrays.asList(new ActorShortMock(1, "Pepito Grillo"),
				        		new ActorShortMock(2, "Carmelo Coton"),
				        		new ActorShortMock(3, "Capitan Tan"),
				        		new ActorShortMock(4, "Nombre Apellido")
				        		));
				
				when(srv.getByProjection(ActorShort.class)).thenReturn(lista);
				
				mockMvc.perform(get("/api/actores/v1").accept(MediaType.APPLICATION_JSON))
					.andExpectAll(
							status().isOk(), 
							content().contentType("application/json"),
							jsonPath("$.size()").value(4));
			}
			
			@Test
			@DisplayName("Get all actors in page format")
			void testGetAllPageable() throws Exception {
				List<ActorShort> lista = new ArrayList<>(
				        Arrays.asList(new ActorShortMock(1, "Pepito Grillo"),
				        		new ActorShortMock(2, "Carmelo Coton"),
				        		new ActorShortMock(3, "Capitan Tan")));

				when(srv.getByProjection(PageRequest.of(0, 20), ActorShort.class))
					.thenReturn(new PageImpl<>(lista));
				mockMvc.perform(get("/api/actores/v1").queryParam("page", "0"))
					.andExpectAll(
						status().isOk(), 
						content().contentType("application/json"),
						jsonPath("$.content.size()").value(3),
						jsonPath("$.size").value(3)
						);
			}
			
			@Test
			@DisplayName("Get all but empty")
			void testGetAllEmpty() throws Exception {
				
				List<ActorShort> lista = new ArrayList<>(
				        Arrays.asList());
				
				when(srv.getByProjection(ActorShort.class)).thenReturn(lista);
				mockMvc.perform(get("/api/actores/v1").accept(MediaType.APPLICATION_JSON))
					.andExpectAll(
							status().isOk(), 
							content().contentType("application/json"),
							jsonPath("$.size()").value(0)
							);
			}
			
			@Test
			@DisplayName("Get all films from actor")
			void testGetActorFilms() throws Exception{
				var id = 1; 
				List<Film> filmList = new ArrayList<>(
				        Arrays.asList(new Film(1, "Description of the first movie", 60, Rating.GENERAL_AUDIENCES, new Short("2023"), (byte) 5, new BigDecimal(10.0), new BigDecimal(30), "The revenge of the test part 1", new Language(1), new Language(2)),
				        			new Film(2, "Description of the second movie", 60, Rating.GENERAL_AUDIENCES, new Short("2023"), (byte) 5, new BigDecimal(10.0), new BigDecimal(30), "The revenge of the test part 2", new Language(1), new Language(2))
				        		));
				
				var actor = new Actor(id, "Nombre","APELLIDO");
				
				List<FilmActor> filmActors = new ArrayList<>(
						Arrays.asList(
								new FilmActor(filmList.get(0), actor),
								new FilmActor(filmList.get(1), actor)
								));
						
				
				actor.setFilmActors(filmActors);
				
				when(srv.getOne(id)).thenReturn(Optional.of(actor));
				
				mockMvc.perform(get("/api/actores/v1/{id}/pelis", id))
					.andExpectAll(
							status().isOk(),
							jsonPath("$[0].key").value(1),
							jsonPath("$[0].value").value("The revenge of the test part 1"),
							jsonPath("$[1].key").value(2),
							jsonPath("$[1].value").value("The revenge of the test part 2")
					)//.andDo(print())
					;
			}
			
			@Test
			@DisplayName("Novedades")
			void testGetNovedades() throws Exception {
				var timestampString = "2022-01-01 00:00:00";
				var timestamp = Timestamp.valueOf(timestampString);
				List<Actor> lista = new ArrayList<>(
				        Arrays.asList(new Actor(1, "Primera","INCORPORACION"),
				        		new Actor(2, "Segunda","ADICION"),
				        		new Actor(3, "Nuevo","BECARIO")
				        		));
				
				when(srv.novedades(timestamp)).thenReturn(lista);
				
				mockMvc.perform(get("/api/actores/v1").queryParam("novedades", timestampString))
					.andExpectAll(
							status().isOk(), 
							content().contentType("application/json"),
							jsonPath("$.length()").value(3),
							jsonPath("$[0].id").value(1),
							jsonPath("$[0].nombre").value("Primera"),
							jsonPath("$[0].apellidos").value("INCORPORACION"),
							jsonPath("$[1].id").value(2),
							jsonPath("$[1].nombre").value("Segunda"),
							jsonPath("$[1].apellidos").value("ADICION"),
							jsonPath("$[2].id").value(3),
							jsonPath("$[2].nombre").value("Nuevo"),
							jsonPath("$[2].apellidos").value("BECARIO")
					).andDo(print());
			}
			
			@Test
			@DisplayName("Get one actor by its id")
			void testGetOne() throws Exception {
				int id = 1;
				var ele = new Actor(id, "Aitana", "BONMATI");
				
				when(srv.getOne(id)).thenReturn(Optional.of(ele));
				
				mockMvc.perform(get("/api/actores/v1/{id}", id))
					.andExpect(status().isOk())
			        .andExpect(jsonPath("$.id").value(id))
			        .andExpect(jsonPath("$.nombre").value(ele.getFirstName()))
			        .andExpect(jsonPath("$.apellidos").value(ele.getLastName()));
			}
		}
		
		@Nested
		class KO{
			@Test
			@DisplayName("Get one actor invalid")
			void testGetOneInvalid() throws Exception {
				int id = 1;
				when(srv.getOne(id)).thenReturn(Optional.empty());
				mockMvc.perform(get("/api/actores/v1/{id}", id))
					.andExpect(status().isNotFound())
					.andExpect(jsonPath("$.title").value("Not Found"));
			}
		}
	}

	@Nested
	class PostMethods{
		@Test
		@DisplayName("Create new actor")
		void testCreate() throws Exception {
			int id = 1;
			var ele = new Actor(id, "Lionel", "MESSI");
			when(srv.add(ele)).thenReturn(ele);
			mockMvc.perform(post("/api/actores/v1")
				.contentType(MediaType.APPLICATION_JSON)
				.content(objectMapper.writeValueAsString(ActorDTO.from(ele)))
				)
				.andExpect(status().isCreated())
		        .andExpect(header().string("Location", "http://localhost/api/actores/v1/1"));
		        //.andDo(print());
		}
		
		@Test
		@DisplayName("Create new actor invalid")
		void testCreateInvalid() throws Exception {
			int id = 1;
			var ele = new Actor(id, "Nombre", "APELLIDOconminusculas");
			
			when(srv.add(ele)).thenThrow(new InvalidDataException());
			
			mockMvc.perform(post("/api/actores/v1")
				.contentType(MediaType.APPLICATION_JSON)
				.content(objectMapper.writeValueAsString(ActorDTO.from(ele))))
				.andExpect(status().isBadRequest())
				.andExpect(jsonPath("$.title").value("Bad Request"));
		}
	}
	
	@Nested
	class PutMethods{
		
		@Test
		@DisplayName("Update language")
		void testUpdate() throws Exception {
			int id = 1;
			var ele = new Actor(id,"Nombre", "APELLIDOMODIFICADO");
			
			when(srv.modify(ele)).thenReturn(ele);
			
			mockMvc.perform(put("/api/actores/v1/{id}", id)
				.contentType(MediaType.APPLICATION_JSON)
				.content(objectMapper.writeValueAsString(ActorDTO.from(ele))))
				.andExpect(status().isNoContent())
		        //.andDo(print())
		        ;
		}
		
		@Test
		@DisplayName("Update language invalid")
		void testUpdateInvalid() throws Exception {
			int id = 1;
			var ele = new Actor(id,"Nombre", "apellido con espacios");
			
			when(srv.modify(ele)).thenThrow(new InvalidDataException());
			
			mockMvc.perform(put("/api/actores/v1/{id}", id)
				.contentType(MediaType.APPLICATION_JSON)
				.content(objectMapper.writeValueAsString(ActorDTO.from(ele))))
				.andExpect(status().isBadRequest())
				.andExpect(jsonPath("$.title").value("Bad Request"))
		        //.andDo(print())
		        ;
		}
	}
	
	@Nested
	class DeleteMethods{
		@Test
		@DisplayName("Delete actor")
		void testDelete() throws Exception {
			var id = 1;
			
			doNothing().when(srv).deleteById(id);
			
			mockMvc.perform(delete("/api/actores/v1/{id}", id))
				.andExpect(status().isNoContent())
		        //.andDo(print())
				;
			
			verify(srv,times(1)).deleteById(id);
		}
	}

}