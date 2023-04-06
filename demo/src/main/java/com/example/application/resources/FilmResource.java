package com.example.application.resources;

import java.net.URI;
import java.util.List;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import com.example.domains.contracts.services.FilmService;
import com.example.domains.entities.dtos.FilmDTO;
import com.example.exceptions.BadRequestException;
import com.example.exceptions.InvalidDataException;
import com.example.exceptions.NotFoundException;
import com.example.exceptions.DuplicateKeyException;

import jakarta.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;

@RestController
@RequestMapping(path = { "/api/peliculas/v1", "/api/peliculas" })
public class FilmResource {
	
	@Autowired
	private FilmService srv;

	//get all films (as FilmDTO)
	
	@GetMapping
	public List<FilmDTO> getAll() {
		return srv.getByProjection(FilmDTO.class);
	}

	//get one actor found by its id (as FilmDTO)
	@GetMapping(path = "/{id}")
	public FilmDTO getOne(@PathVariable int id) throws NotFoundException {
		var item = srv.getOne(id);
		
		if(item.isEmpty())
			throw new NotFoundException();
		System.out.println(item);
		
		return FilmDTO.from(item.get());
	}
	
	//create new film (received as FilmDTO BUT saved as Film)
	@PostMapping
	public ResponseEntity<Object> create(@Valid @RequestBody FilmDTO item) throws BadRequestException, DuplicateKeyException, InvalidDataException {
		
		//conversión de FilmDTO a Film
		var filmConverted = FilmDTO.from(item);
		
		//operación de create (post), se hace la validación del objeto por lo que si no es valida no se guardará 
		var newItem = srv.add(filmConverted); 
		
		//en caso de no ser válido saltan excepciones, en caso contrario se añade el nuevo actor a la url de manera dinámica según el current request path
		URI location = ServletUriComponentsBuilder.fromCurrentRequest().path("/{id}")
			.buildAndExpand(newItem.getFilmId()).toUri();
		return ResponseEntity.created(location).build();

	}

	//modify existing actor (received as FilmDTO BUT modified as Film)
	@PutMapping("/{id}")
	@ResponseStatus(HttpStatus.NO_CONTENT)
	public void update(@PathVariable int id, @Valid @RequestBody FilmDTO item) throws BadRequestException, NotFoundException, InvalidDataException {
		//si lo que cambia es el actorID lanzamos excepción ya que es un atributo que no debe modificarse
		if(id != item.getFilmId())
			throw new BadRequestException("IDs of film don't match");
		
		//si los IDs son válidos modificamos el actor; dentro se hacen las validaciones
		srv.modify(FilmDTO.from(item));
	}

	//delete an actor by its id
	@DeleteMapping("/{id}")
	@ResponseStatus(HttpStatus.NO_CONTENT)
	public void delete(@PathVariable int id) {
		srv.deleteById(id);
	}

}