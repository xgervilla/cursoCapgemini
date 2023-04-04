package com.example.domains.services;


import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.ComponentScan;

import com.example.domains.contracts.repositories.ActorRepository;
import com.example.domains.entities.Actor;
import com.example.exceptions.DuplicateKeyException;
import com.example.exceptions.InvalidDataException;
import com.example.exceptions.NotFoundException;


@DataJpaTest
@ComponentScan(basePackages = "com.example")
class ActorServiceImplMockTest {
	
	@MockBean
	ActorRepository dao;
	
	@Autowired
	ActorServiceImpl srv;
	
	

	@BeforeEach
	void setUp() throws Exception {
	}



	@Test
	@DisplayName("findAll")
	void testGetAll() {
		List<Actor> actorList = new ArrayList<>(Arrays.asList(
				new Actor(1, "Persona", "PRIMERA"),
				new Actor(2,"Jesu","CRISTO"),
				new Actor(3,"Carmen","MACHI")
				));
		when(dao.findAll()).thenReturn(actorList);
		assertEquals(3, dao.findAll().size());
		assertEquals(1, dao.findAll().get(0).getActorId());
	}

	@Test
	@DisplayName("Get one")
	void testGetOne() {
		List<Actor> actorList = new ArrayList<>(Arrays.asList(
				new Actor(1, "Persona", "PRIMERA"),
				new Actor(2,"Jesu","CRISTO"),
				new Actor(3,"Carmen","MACHI")
				));
		
		when(dao.findById(1)).thenReturn(Optional.of(actorList.get(0)));
		var item = dao.findById(1);
		assertTrue(item.isPresent());
		assertEquals("Persona", item.get().getFirstName());
	}
	
	@Test
	@DisplayName("Get one but no data available")
	void testGetOneEmptyList() {
		when(dao.findById(1)).thenReturn(Optional.empty());
		var item = srv.getOne(1);
		assertFalse(item.isPresent());
	}
	
	@Test
	@DisplayName("Add test")
	void testAdd() throws DuplicateKeyException, InvalidDataException {
		var actor = new Actor(1, "Juan", "PALOMO");
		when(dao.save(actor)).thenReturn(actor);
		
		var result = srv.add(actor);
		assertEquals(actor, result);
	}
	
	@Test
	@DisplayName("Add null actor")
	void testAddNull() throws DuplicateKeyException, InvalidDataException {
		assertThrows(InvalidDataException.class, () -> srv.add(null));
	}
	
	@Test
	@DisplayName("Add invalid actor")
	void testAddInvalid() throws DuplicateKeyException, InvalidDataException{
		assertThrows(InvalidDataException.class, () -> srv.add(new Actor(0,"     ","GRILLO")));
	}

	@Test
	@DisplayName("Modify actor")
	void testModify() throws NotFoundException, InvalidDataException {
		var actor = new Actor(0, "Lionel","MESSI");
		
		when(dao.existsById(0)).thenReturn(true);
		when(dao.findById(0)).thenReturn(Optional.of(actor));
		when(dao.save(actor)).thenReturn(actor);
		
		var result = srv.modify(actor);
		
		verify(dao, times(1)).existsById(0);
		assertEquals(actor, result);
	}
	
	@Test
	@DisplayName("Modify null actor")
	void testModifyNull() throws NotFoundException, InvalidDataException {
		assertThrows(InvalidDataException.class, () -> srv.modify(null));
	}
	
	@Test
	@DisplayName("Modify invalid actor")
	void testModifyInvalid() throws NotFoundException, InvalidDataException {
		assertThrows(InvalidDataException.class, () -> srv.modify(new Actor(0, "     ", "ACTORINVALID")));
	}
	
	@Test
	@DisplayName("Modify actor not found")
	void testModifyNotFound() throws NotFoundException, InvalidDataException {
		
		when(dao.existsById(0)).thenReturn(false);
		assertThrows(NotFoundException.class, () -> srv.modify(new Actor(0,"Not","FOUND")));
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
		srv.deleteById(0);
		verify(dao, times(1)).deleteById(0);
	}

//	@Test
//	void testNovedades() {
//		fail("Not yet implemented");
//	}
}