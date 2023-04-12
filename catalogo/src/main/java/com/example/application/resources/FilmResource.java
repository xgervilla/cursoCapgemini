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

import com.example.domains.contracts.services.FilmService;
import com.example.domains.entities.dtos.ElementoDTO;
import com.example.domains.entities.dtos.FilmFullDTO;
import com.example.domains.entities.dtos.FilmShortDTO;
import com.example.exceptions.BadRequestException;
import com.example.exceptions.InvalidDataException;
import com.example.exceptions.NotFoundException;

import io.swagger.v3.oas.annotations.Hidden;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.enums.ParameterIn;
//import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.tags.Tag;

import com.example.exceptions.DuplicateKeyException;

import jakarta.validation.Valid;

import org.hibernate.annotations.Parameter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;

@RestController
@Tag(name = "films-service", description = "Film management")
@RequestMapping(path = { "/api/peliculas/v1", "/api/peliculas", "/api/films", "/api/films/v1"})
public class FilmResource {
	
	@Autowired
	private FilmService srv;
	
	@GetMapping
	@Hidden
	public List<FilmShortDTO> getAll() {
		return srv.getByProjection(FilmShortDTO.class);
	}
	
	@GetMapping(params="page")
	@Hidden
	public Page<FilmShortDTO> getAllPageable(Pageable page) {
		return srv.getByProjection(page, FilmShortDTO.class);
	}
	
	@Operation(summary = "Films latest releases", description = "Get the latest releases in Short format")
	@GetMapping(params = "novedades")
	public List<FilmShortDTO> getNovedades(@RequestParam(required = false, name = "novedades", defaultValue = "") String fecha) {
		//"2022-01-01 00:00:00"
		if (fecha.length() != 19)
			return srv.novedades(Timestamp.from(Instant.now().minusSeconds(3600*24*7))).stream().map(o -> FilmShortDTO.from(o)).toList();
		
		return srv.novedades(Timestamp.valueOf(fecha)).stream().map(o -> FilmShortDTO.from(o)).toList();
	}

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
	
	@PostMapping
	public ResponseEntity<Object> create(@Valid @RequestBody FilmFullDTO item) throws BadRequestException, DuplicateKeyException, InvalidDataException {
		
		var filmConverted = FilmFullDTO.from(item);
		
		var newItem = srv.add(filmConverted); 
		
		URI location = ServletUriComponentsBuilder.fromCurrentRequest().path("/{id}")
			.buildAndExpand(newItem.getFilmId()).toUri();
		return ResponseEntity.created(location).build();

	}

	@PutMapping("/{id:\\d+}")
	@ResponseStatus(HttpStatus.NO_CONTENT)
	public void update(@PathVariable int id, @Valid @RequestBody FilmFullDTO item) throws BadRequestException, NotFoundException, InvalidDataException {
		if(id != item.getFilmId())
			throw new BadRequestException("IDs of film don't match");
		
		srv.modify(FilmFullDTO.from(item));
	}

	@DeleteMapping("/{id:\\d+}")
	@ResponseStatus(HttpStatus.NO_CONTENT)
	public void delete(@PathVariable int id) {
		srv.deleteById(id);
	}

}