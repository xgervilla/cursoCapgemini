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
import com.example.domains.contracts.services.LanguageService;
import com.example.domains.entities.Actor;
import com.example.domains.entities.Language;
import com.example.domains.entities.dtos.ElementoDTO;
import com.example.domains.entities.dtos.FilmDTO;
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
@RequestMapping(path = { "/api/lenguajes/v1", "/api/lenguajes" ,"/api/languages/v1", "/api/languages"})
public class LanguageResource {
	
	@Autowired
	private LanguageService srv;

	//get all languages
	
	@GetMapping
	public List<Language> getAll() {
		return srv.getByProjection(Language.class);
	}
	
	@GetMapping(params = "novedades")
	public List<Language> getNovedades(@RequestParam(required = false, name = "novedades") String fecha) {
		//"2022-01-01 00:00:00"
		if (fecha == null)
			return srv.novedades(Timestamp.from(Instant.now().minusSeconds(3600)));
		System.out.println(fecha);
		return srv.novedades(Timestamp.valueOf(fecha));
	}
	
	@GetMapping(params = "page")
	public Page<Language> getAllPageable(Pageable page) {
		return srv.getByProjection(page, Language.class);
	}

	//get one language found by its id
	@GetMapping(path = "/{id:\\d+}")
	public Language getOne(@PathVariable int id) throws NotFoundException {
		var item = srv.getOne(id);
		
		if(item.isEmpty())
			throw new NotFoundException();
		
		return item.get();
	}
	
	//create new language
	@PostMapping
	public ResponseEntity<Object> create(@Valid @RequestBody Language item) throws BadRequestException, DuplicateKeyException, InvalidDataException {
		
		var newItem = srv.add(item); 
		
		URI location = ServletUriComponentsBuilder.fromCurrentRequest().path("/{id}")
			.buildAndExpand(newItem.getLanguageId()).toUri();
		return ResponseEntity.created(location).build();

	}

	//modify existing language
	@PutMapping("/{id:\\d+}")
	@ResponseStatus(HttpStatus.NO_CONTENT)
	public void update(@PathVariable int id, @Valid @RequestBody Language item) throws BadRequestException, NotFoundException, InvalidDataException {
		if(id != item.getLanguageId())
			throw new BadRequestException("IDs of languages don't match");
		
		srv.modify(item);
	}
	
	@GetMapping(path = "/{id:\\d+}/pelis")
	@Transactional
	public List<ElementoDTO<Integer, String>> getFilms(@PathVariable int id) throws NotFoundException {
		var item = srv.getOne(id);
		
		if(item.isEmpty())
			throw new NotFoundException();
		
		return item.get().getFilms().stream().map(o -> new ElementoDTO<>(o.getFilmId(), o.getTitle())).toList();
	}

	//delete an actor by its id
	@DeleteMapping("/{id:\\d+}")
	@ResponseStatus(HttpStatus.NO_CONTENT)
	public void delete(@PathVariable int id) {
		srv.deleteById(id);
	}

}