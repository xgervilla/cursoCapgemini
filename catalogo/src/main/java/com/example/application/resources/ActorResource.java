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
import com.example.domains.entities.Actor;
import com.example.domains.entities.Language;
import com.example.domains.entities.dtos.ActorDTO;
import com.example.domains.entities.dtos.ActorShort;
import com.example.domains.entities.dtos.ElementoDTO;
import com.example.exceptions.BadRequestException;
import com.example.exceptions.InvalidDataException;
import com.example.exceptions.NotFoundException;
import com.example.exceptions.DuplicateKeyException;

import jakarta.transaction.Transactional;
import jakarta.validation.Valid;

import org.hibernate.annotations.Parameter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpStatus;

@RestController
@RequestMapping(path = { "/api/actores/v1", "/api/actores", "/api/actors/v1" , "/api/actors/v1"})
public class ActorResource {
	
	@Autowired
	private ActorService srv;

	//get all actors (as ActorDTO)
	
	
	@GetMapping
	public List<ActorShort> getAll(@RequestParam(required = false) String sort) {
		if (sort != null)
			return (List<ActorShort>)srv.getByProjection(Sort.by(sort), ActorShort.class);
		
		return srv.getByProjection(ActorShort.class);
	}
	
	@GetMapping(params = "novedades")
	public List<ActorDTO> getNovedades(@RequestParam(required = false, name = "novedades") String fecha) {
		//"2022-01-01 00:00:00"
		if (fecha == null)
			return srv.novedades(Timestamp.from(Instant.now().minusSeconds(3600))).stream().map(o -> ActorDTO.from(o)).toList();
		return srv.novedades(Timestamp.valueOf(fecha)).stream().map(o -> ActorDTO.from(o)).toList();
	}
	
	
	
	@GetMapping(params="page")
	public Page<ActorShort> getAllPageable(Pageable pageable) {
		return srv.getByProjection(pageable, ActorShort.class);
	}

	//get one actor found by its id (as ActorDTO)
	@GetMapping(path = "/{id:\\d+}")
	public ActorDTO getOne(@PathVariable int id) throws NotFoundException {
		var item = srv.getOne(id);
		
		if(item.isEmpty())
			throw new NotFoundException();
		
		return ActorDTO.from(item.get());
	}
	
	@GetMapping(path = "/{id:\\d+}/pelis")
	@Transactional
	public List<ElementoDTO<Integer, String>> getFilms(@PathVariable int id) throws NotFoundException {
		var item = srv.getOne(id);
		
		if(item.isEmpty())
			throw new NotFoundException();
		
		return item.get().getFilmActors().stream().map(o -> new ElementoDTO<>(o.getFilm().getFilmId(), o.getFilm().getTitle())).toList();
	}
	
	
	//create new actor (received as ActorDTO BUT saved as Actor)
	@PostMapping
	public ResponseEntity<Object> create(@Valid @RequestBody ActorDTO item) throws BadRequestException, DuplicateKeyException, InvalidDataException {
		
		//conversión de ActorDTO a Actor
		var actorConverted = ActorDTO.from(item);
		
		//operación de create (post), se hace la validación del objeto por lo que si no es valida no se guardará 
		var newItem = srv.add(actorConverted); 
		
		//en caso de no ser válido saltan excepciones, en caso contrario se añade el nuevo actor a la url de manera dinámica según el current request path
		URI location = ServletUriComponentsBuilder.fromCurrentRequest().path("/{id}")
			.buildAndExpand(newItem.getActorId()).toUri();
		return ResponseEntity.created(location).build();

	}

	//modify existing actor (received as ActorDTO BUT modified as Actor)
	@PutMapping("/{id:\\d+}")
	@ResponseStatus(HttpStatus.NO_CONTENT)
	public void update(@PathVariable int id, @Valid @RequestBody ActorDTO item) throws BadRequestException, NotFoundException, InvalidDataException {
		//si lo que cambia es el actorID lanzamos excepción ya que es un atributo que no debe modificarse
		if(id != item.getActorId())
			throw new BadRequestException("IDs of actor don't match");
		
		//si los IDs son válidos modificamos el actor; dentro se hacen las validaciones
		srv.modify(ActorDTO.from(item));
	}

	//delete an actor by its id
	@DeleteMapping("/{id:\\d+}")
	@ResponseStatus(HttpStatus.NO_CONTENT)
	public void delete(@PathVariable int id) {
		srv.deleteById(id);
	}

}