package com.example.domains.services;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;

import java.sql.Timestamp;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.annotation.ComponentScan;

import com.example.domains.contracts.repositories.ActorRepository;
import com.example.domains.entities.Actor;
import com.example.exceptions.DuplicateKeyException;
import com.example.exceptions.InvalidDataException;
import com.example.exceptions.NotFoundException;

@SpringBootTest
@ComponentScan(basePackages = "com.example")
class ActorServiceImplTest {
	
	@Autowired
	ActorRepository dao;
	
	@Autowired
	ActorServiceImpl srv;

	@BeforeEach
	void setUp() throws Exception {
	}

	@Test
	@DisplayName("getAll")
	void testGetAll() {
		assertThat(srv.getAll().size()).isGreaterThanOrEqualTo(200);
	}

	@Test
	@DisplayName("getOne")
	void testGetOne() {
		var item = srv.getOne(1);
		assertTrue(item.isPresent());
	}
	
	@Test
	@DisplayName("Get one but no valid ID")
	void testGetOneNotFound() {
		var item = srv.getOne(300);
		assertFalse(item.isPresent());
	}
	
	@Test
	void testAdd() throws DuplicateKeyException, InvalidDataException {
		
		var originalSize = srv.getAll().size();
		
		var actor = new Actor(0, "Persona", "PRIMERA");
		
		var result = srv.add(actor);
		assertEquals(originalSize+1, srv.getAll().size());
		
		srv.deleteById(result.getActorId());
		
	}

	@Test
	@DisplayName("Add null actor")
	void testAddNull() throws DuplicateKeyException, InvalidDataException {
		assertThrows(InvalidDataException.class, () -> srv.add(null));
	}
	
	@Test
	@DisplayName("Add invalid actor")
	void testAddInvalid() throws DuplicateKeyException, InvalidDataException{
		assertThrows(InvalidDataException.class, () -> srv.add(new Actor(0,"     ","EMPTY")));
	}
	
	@Test
	@DisplayName("Modify actor")
	void testModify() throws NotFoundException, InvalidDataException {
		
		var actor = new Actor(0, "Persona", "ORIGINAL");
		var addedActor = srv.add(actor);
		addedActor.setLastName("MODIFICADA");
		
		var result = srv.modify(actor);
		assertEquals("MODIFICADA", result.getLastName());
		assertEquals(actor.getActorId(), result.getActorId());
		
		srv.deleteById(result.getActorId());
	}
	
	@Test
	@DisplayName("Modify null actor")
	void testModifyNull() throws NotFoundException, InvalidDataException {
		assertThrows(InvalidDataException.class, () -> srv.modify(null));
	}
	
	@Test
	@DisplayName("Modify invalid actor")
	void testModifyInvalid() throws NotFoundException, InvalidDataException {
		assertThrows(InvalidDataException.class, () -> srv.modify(new Actor(0,"     ","EMPTY")));
	}
	
	@Test
	@DisplayName("Modify actor not found")
	void testModifyNotFound() throws NotFoundException, InvalidDataException {
		assertThrows(NotFoundException.class, () -> srv.modify(new Actor(0,"NotFound","NOTFOUND")));
	}

	//cuando se hace un delete de null salta una excepción, en caso contrario se ejecuta deleteById
	@Test
	@DisplayName("Delete null value")
	void testDelete() {
		assertThrows(InvalidDataException.class, () -> srv.delete(null));
	}

	@Test
	@DisplayName("Delete by id")
	void testDeleteById() throws InvalidDataException, NotFoundException{
		
		var actor = new Actor(0, "Persona", "DELETE");
		
		var addedActorId = srv.add(actor).getActorId();
		
		var originalSize = srv.getAll().size();
		
		srv.deleteById(addedActorId);
		
		assertEquals(originalSize-1, srv.getAll().size());
	}
	
	@Test
	@DisplayName("Delete by id not exists")
	void testDeleteByIdNotExists() throws InvalidDataException, NotFoundException{
		
		var actor = new Actor(0, "Persona", "DELETE");
		
		var addedActorId = srv.add(actor).getActorId();
		
		var originalSize = srv.getAll().size();
		
		srv.deleteById(addedActorId+1);
		
		assertEquals(originalSize, srv.getAll().size());
		
		srv.deleteById(addedActorId);
	}
	
	@Test
	@DisplayName("Novedades of actor")
	void testNovedadesActor() {
		var timestamp = Timestamp.valueOf("2007-01-01 00:00:00");
		var result = srv.novedades(timestamp);
		
		assertAll("Novedades of actor",
				() -> assertEquals(9, result.size()));
	}
}