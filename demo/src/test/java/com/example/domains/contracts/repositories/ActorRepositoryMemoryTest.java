package com.example.domains.contracts.repositories;

import static org.junit.jupiter.api.Assertions.*;

import java.sql.Timestamp;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;

import com.example.domains.entities.Actor;

@DataJpaTest
class ActorRepositoryMemoryTest {
	
	@Autowired	
	private TestEntityManager em;
	
	@Autowired
	ActorRepository dao;
	
	@BeforeEach
	void setUp() throws Exception {
		var item = new Actor(0, "Pepito", "GRILLO");
		item.setLastUpdate(Timestamp.valueOf("2023-01-01 00:00:00"));
		em.persist(item);
		
		item = new Actor(0, "Carmelo", "COTON");
		item.setLastUpdate(Timestamp.valueOf("2023-01-01 00:00:00"));
		em.persist(item);
		
		item = new Actor(0, "Capitan", "TAN");
		item.setLastUpdate(Timestamp.valueOf("2023-01-01 00:00:00"));
		em.persist(item);
	}
	
	@Test
	void testAll() {
		assertEquals(3, dao.findAll().size());
	}
	
	@Test
	@Disabled
	void testOne() {
		var item = dao.findById(1);
		assertTrue(item.isPresent());
		assertAll("",
			() -> assertEquals(1, item.get().getActorId()),
			() -> assertEquals("Pepito", item.get().getFirstName()),
			() -> assertEquals("Grillo", item.get().getLastName()));
	}
	
	@Test
	@Disabled
	void testSave() {
		var item = dao.save(new Actor(0));
		assertNotNull(item);
		assertAll("",
			() -> assertEquals(1, item.getActorId()),
			() -> assertEquals("Pepito", item.getFirstName()),
			() -> assertEquals("Grillo", item.getLastName()));
	}
	

}