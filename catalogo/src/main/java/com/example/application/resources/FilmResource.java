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
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import com.example.domains.contracts.services.FilmService;
import com.example.domains.entities.dtos.ElementoDTO;
import com.example.domains.entities.dtos.FilmDTO;
import com.example.domains.entities.dtos.FilmFullDTO;
import com.example.domains.entities.dtos.FilmShortDTO;
import com.example.exceptions.BadRequestException;
import com.example.exceptions.InvalidDataException;
import com.example.exceptions.NotFoundException;
import com.example.exceptions.DuplicateKeyException;

import jakarta.transaction.Transactional;
import jakarta.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;

@RestController
@RequestMapping(path = { "/api/peliculas/v1", "/api/peliculas", "/api/films", "/api/films/v1"})
public class FilmResource {
	
	@Autowired
	private FilmService srv;

	//get all films (as FilmDTO)
	
	@GetMapping
	public List<FilmShortDTO> getAll() {
		return srv.getByProjection(FilmShortDTO.class);
	}
	
	@GetMapping(params="page")
	public Page<FilmShortDTO> getAllPageable(Pageable page) {
		return srv.getByProjection(page, FilmShortDTO.class);
	}

	//get one actor found by its id (as FilmDTO)
	@GetMapping(path = "/{id:\\d+}")
	public FilmFullDTO getOne(@PathVariable int id) throws NotFoundException {
		var item = srv.getOne(id);
		
		if(item.isEmpty())
			throw new NotFoundException();
		
		return FilmFullDTO.from(item.get());
	}
	
	@GetMapping(path = "/{id:\\d+}", params = "basic")
	public ElementoDTO<Integer, String> getOneBasic(@PathVariable int id, String param) throws NotFoundException {
		var item = srv.getOne(id);
		
		if(item.isEmpty())
			throw new NotFoundException();
		
		return new ElementoDTO<Integer, String>(item.get().getFilmId(), item.get().getTitle());
	}
	
	//create new film (received as FilmDTO BUT saved as Film)
	@PostMapping
	public ResponseEntity<Object> create(@Valid @RequestBody FilmDTO item) throws BadRequestException, DuplicateKeyException, InvalidDataException {
		
		var filmConverted = FilmDTO.from(item);
		
		var newItem = srv.add(filmConverted); 
		
		URI location = ServletUriComponentsBuilder.fromCurrentRequest().path("/{id}")
			.buildAndExpand(newItem.getFilmId()).toUri();
		return ResponseEntity.created(location).build();

	}

	//modify existing actor (received as FilmDTO BUT modified as Film)
	@PutMapping("/{id:\\d+}")
	@ResponseStatus(HttpStatus.NO_CONTENT)
	public void update(@PathVariable int id, @Valid @RequestBody FilmDTO item) throws BadRequestException, NotFoundException, InvalidDataException {
		//si lo que cambia es el actorID lanzamos excepción ya que es un atributo que no debe modificarse
		if(id != item.getFilmId())
			throw new BadRequestException("IDs of film don't match");
		
		//si los IDs son válidos modificamos el actor; dentro se hacen las validaciones
		srv.modify(FilmDTO.from(item));
	}

	//delete an actor by its id
	@DeleteMapping("/{id:\\d+}")
	@ResponseStatus(HttpStatus.NO_CONTENT)
	public void delete(@PathVariable int id) {
		srv.deleteById(id);
	}

}