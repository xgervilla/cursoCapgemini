package com.example.domains.services;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.ComponentScan;

import com.example.domains.contracts.repositories.ActorRepository;
import com.example.domains.contracts.services.ActorService;
import com.example.domains.entities.Actor;
import com.example.exceptions.DuplicateKeyException;
import com.example.exceptions.InvalidDataException;
import com.example.exceptions.NotFoundException;


@DataJpaTest
@ComponentScan(basePackages = "com.example")
class ActorServiceImplTest {
	
	@MockBean
	ActorRepository dao;
	
	@Autowired
	ActorServiceImpl srv;
	
	

	@BeforeEach
	void setUp() throws Exception {
	}



	@Test
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
	void testGetOne() {
		List<Actor> actorList = new ArrayList<>(Arrays.asList(
				new Actor(1, "Persona", "PRIMERA"),
				new Actor(2,"Jesu","CRISTO"),
				new Actor(3,"Carmen","MACHI")
				));
		
		var item = dao.findById(0);
		when(dao.findById(1)).thenReturn(Optional.of(actorList.get(0)));
		assertTrue(item.isPresent());
		assertEquals("Persona", item.get().getFirstName());
	}

	@Test
	@Disabled
	void testGetAll_isNotEmpty() {
		List<Actor> lista = new ArrayList<>(
				Arrays.asList(new Actor(1,"Pepito", "GRILLO"), new Actor(2, "Carmelo", "COTON"), new Actor(3, "Capitan","TAN"))
			);
		when(dao.findAll()).thenReturn(lista);
		assertEquals(3, dao.findAll().size());
	}
	
	@Test
	@Disabled
	void testGetOne_valid() {
		List<Actor> lista = new ArrayList<>(
				Arrays.asList(new Actor(1,"Pepito","GRILLO"),
						new Actor(2, "Carmelo", "COTON"),
						new Actor(3, "Capitan","TAN")));
		when(dao.findById(1)).thenReturn(Optional.of(new Actor(1, "Pepito","GRILLO")));
		
		assertEquals(lista, dao.findById(1));
	}
	
	@Test
	@Disabled
	void testGetOne_notfound() {
		when(dao.findById(1)).thenReturn(Optional.empty());
		var result = srv.getOne(1);
		assertThat(result.isEmpty()).isTrue();
		
	}
	
	@Test
	void testAdd() throws DuplicateKeyException, InvalidDataException {
		var actor = new Actor(1, "Juan", "PALOMO");
		when(dao.save(actor)).thenReturn(actor);
		
		var result = srv.add(actor);
		assertEquals(actor, result);
	}

	@Test
	void testModify() throws NotFoundException, InvalidDataException {
		var actor = new Actor(0, "Lionel","MESSI");
		
		when(dao.existsById(0)).thenReturn(true);
		when(dao.findById(0)).thenReturn(Optional.of(actor));
		when(dao.save(actor)).thenReturn(actor);
		
		var result = srv.modify(actor);
		
		verify(dao, times(1)).existsById(0);
		assertEquals(actor, result);
	}

	//cuando se hace un delete de null salta una excepción, en caso contrario se ejecuta deleteById
	@Test
	void testDelete() {
		assertThrows(InvalidDataException.class, () -> srv.delete(null));
	}

	@Test
	void testDeleteById() throws InvalidDataException, NotFoundException{
		srv.deleteById(0);
		verify(dao, times(1)).deleteById(0);
	}

//	@Test
//	void testNovedades() {
//		fail("Not yet implemented");
//	}
}