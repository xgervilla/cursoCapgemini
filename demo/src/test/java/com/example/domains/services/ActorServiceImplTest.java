package com.example.domains.services;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.when;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import java.util.Arrays;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.mock.mockito.MockBean;

import com.example.domains.contracts.repositories.ActorRepository;
import com.example.domains.contracts.services.ActorService;
import com.example.domains.entities.Actor;

class ActorServiceImplTest {

	@MockBean
	ActorRepository dao;
	
	@Autowired
	ActorService srv;
	
	@BeforeEach
	void setUp() throws Exception {
	}

//	@Test
//	void testGetByProjectionClassOfT() {
//		fail("Not yet implemented");
//	}
//
//	@Test
//	void testGetByProjectionSortClassOfT() {
//		fail("Not yet implemented");
//	}
//
//	@Test
//	void testGetByProjectionPageableClassOfT() {
//		fail("Not yet implemented");
//	}
//
//	@Test
//	void testGetAllSort() {
//		fail("Not yet implemented");
//	}
//
//	@Test
//	void testGetAllPageable() {
//		fail("Not yet implemented");
//	}
//
//	@Test
//	void testGetAll() {
//		fail("Not yet implemented");
//	}
//
//	@Test
//	void testGetOne() {
//		fail("Not yet implemented");
//	}
	
	@Test
	void testGetAll_isNotEmpty() {
		List<Actor> lista = new ArrayList<>(
				Arrays.asList(new Actor(1,"Pepito", "GRILLO"), new Actor(2, "Carmelo", "COTON"), new Actor(3, "Capitan","TAN"))
			);
		when(dao.findAll()).thenReturn(lista);
		assertEquals(3, dao.findAll().size());
	}
	
	@Test
	void testGetOne_valid() {
		List<Actor> lista = new ArrayList<>(
				Arrays.asList(new Actor(1,"Pepito","GRILLO"),
						new Actor(2, "Carmelo", "COTON"),
						new Actor(3, "Capitan","TAN")));
		when(dao.findById(1)).thenReturn(Optional.of(new Actor(1, "Pepito","GRILLO")));
		
		assertEquals(lista, dao.findById(1));
	}

	@Test
	void testAdd() {
		fail("Not yet implemented");
	}

	@Test
	void testModify() {
		fail("Not yet implemented");
	}

	@Test
	void testDelete() {
		fail("Not yet implemented");
	}

	@Test
	void testDeleteById() {
		fail("Not yet implemented");
	}

}
