package com.example.domains.entities.dtos;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import com.example.domains.entities.Actor;

class ActorDTOTest {

	@BeforeEach
	void setUp() throws Exception {
	}

	//conversión de ActorDTO a Actor
	@Test
	void testFromActorDTO() {
		var actor = ActorDTO.from(new ActorDTO(0, "Pep","GIRASOL"));
		assertEquals(Actor.class,actor.getClass());
		
		assertAll("Attributes",
				() -> assertEquals(0,actor.getActorId()),
				() -> assertEquals("Pep",actor.getFirstName()),
				() -> assertEquals("GIRASOL", actor.getLastName()));
	}

	//conversión de Actor a ActorDTO
	@Test
	void testFromActor() {
		var actorDTO = ActorDTO.from(new Actor(0, "Pep","GIRASOL"));
		assertEquals(ActorDTO.class,actorDTO.getClass());
		
		assertAll("Attributes",
				() -> assertEquals(0,actorDTO.getActorId()),
				() -> assertEquals("Pep",actorDTO.getFirstName()),
				() -> assertEquals("GIRASOL", actorDTO.getLastName()));
	}

}
