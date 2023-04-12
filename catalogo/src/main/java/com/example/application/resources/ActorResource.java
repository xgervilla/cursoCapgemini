package com.example.application.resources;

import java.net.URI;
import java.sql.Timestamp;
import java.time.Instant;
import java.util.List;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import com.example.domains.contracts.services.ActorService;
import com.example.domains.entities.dtos.ActorDTO;
import com.example.domains.entities.dtos.ActorShort;
import com.example.domains.entities.dtos.ElementoDTO;
import com.example.exceptions.BadRequestException;
import com.example.exceptions.InvalidDataException;
import com.example.exceptions.NotFoundException;

import io.swagger.v3.oas.annotations.Hidden;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;

import com.example.exceptions.DuplicateKeyException;

import jakarta.transaction.Transactional;
import jakarta.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpStatus;

@RestController
@Tag(name = "Actors-service", description = "Actor management")
@RequestMapping(path = { "/api/actores/v1", "/api/actores", "/api/actors/v1" , "/api/actors/v1"})
public class ActorResource {
	
	@Autowired
	private ActorService srv;
	
	@Operation(summary = "Get all actors", description = "Get all actors in Short format")
	@GetMapping
	public List<ActorShort> getAll(@RequestParam(required = false) String sort) {
		if (sort != null)
			return (List<ActorShort>)srv.getByProjection(Sort.by(sort), ActorShort.class);
		
		return srv.getByProjection(ActorShort.class);
	}
	
	@Operation(summary = "Actor newest releases", description = "Get the newest releases of actors")
	@GetMapping(params = "novedades")
	public List<ActorDTO> getNovedades(@RequestParam(required = false, name = "novedades", defaultValue = "") String fecha) {
		if (fecha.length() != 19)
			return srv.novedades(Timestamp.from(Instant.now().minusSeconds(3600))).stream().map(o -> ActorDTO.from(o)).toList();
		return srv.novedades(Timestamp.valueOf(fecha)).stream().map(o -> ActorDTO.from(o)).toList();
	}
	
	
	@Hidden
	@GetMapping(params="page")
	public Page<ActorShort> getAllPageable(Pageable pageable) {
		return srv.getByProjection(pageable, ActorShort.class);
	}

	@Operation(summary = "Get one actor", description = "Get all attributes of an actor")
	@GetMapping(path = "/{id:\\d+}")
	public ActorDTO getOne(@PathVariable int id) throws NotFoundException {
		var item = srv.getOne(id);
		
		if(item.isEmpty())
			throw new NotFoundException();
		
		return ActorDTO.from(item.get());
	}
	
	@Operation(summary = "Get all films of actor", description = "Get all films where the actor appears")
	@GetMapping(path = "/{id:\\d+}/pelis")
	@Transactional
	public List<ElementoDTO<Integer, String>> getFilms(@PathVariable int id) throws NotFoundException {
		var item = srv.getOne(id);
		
		if(item.isEmpty())
			throw new NotFoundException();
		
		return item.get().getFilmActors().stream().map(o -> new ElementoDTO<>(o.getFilm().getFilmId(), o.getFilm().getTitle())).toList();
	}
	
	
	@Operation(summary = "Create new actor", description = "Create a new actor")
	@PostMapping
	public ResponseEntity<Object> create(@Valid @RequestBody ActorDTO item) throws BadRequestException, DuplicateKeyException, InvalidDataException {
		
		var actorConverted = ActorDTO.from(item);
		
		var newItem = srv.add(actorConverted); 
		
		URI location = ServletUriComponentsBuilder.fromCurrentRequest().path("/{id}")
			.buildAndExpand(newItem.getActorId()).toUri();
		return ResponseEntity.created(location).build();

	}

	@Operation(summary = "Update an actor", description = "Update an actor with all its attributes")
	@PutMapping("/{id:\\d+}")
	@ResponseStatus(HttpStatus.NO_CONTENT)
	public void update(@PathVariable int id, @Valid @RequestBody ActorDTO item) throws BadRequestException, NotFoundException, InvalidDataException {
		if(id != item.getActorId())
			throw new BadRequestException("IDs of actor don't match");
		
		srv.modify(ActorDTO.from(item));
	}

	@Operation(summary = "Delete an actor", description = "Delete an actor")
	@DeleteMapping("/{id:\\d+}")
	@ResponseStatus(HttpStatus.NO_CONTENT)
	public void delete(@PathVariable int id) {
		srv.deleteById(id);
	}

}