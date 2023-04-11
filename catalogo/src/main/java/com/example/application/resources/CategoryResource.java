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

import com.example.domains.contracts.services.CategoryService;
import com.example.domains.entities.Category;
import com.example.domains.entities.dtos.FilmShortDTO;
import com.example.exceptions.BadRequestException;
import com.example.exceptions.InvalidDataException;
import com.example.exceptions.NotFoundException;
import com.example.exceptions.DuplicateKeyException;

import jakarta.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;

@RestController
@RequestMapping(path = { "/api/categorias/v1", "/api/categorias" })
public class CategoryResource {
	
	@Autowired
	private CategoryService srv;
	
	@GetMapping
	public List<Category> getAll() {
		return srv.getByProjection(Category.class);
	}
	
	@GetMapping(params = "page")
	public Page<Category> getAllPageable(Pageable page) {
		return srv.getByProjection(page, Category.class);
	}
	
	@GetMapping(params = "novedades")
	public List<Category> getNovedades(@RequestParam(required = false, name = "novedades", defaultValue = "") String fecha) {
		//"2022-01-01 00:00:00"
		if (fecha.length() != 19)
			return srv.novedades(Timestamp.from(Instant.now().minusSeconds(3600)));
		return srv.novedades(Timestamp.valueOf(fecha));
	}

	@GetMapping(path = "/{id:\\d+}")
	public Category getOne(@PathVariable int id) throws NotFoundException {
		var item = srv.getOne(id);
		
		if(item.isEmpty())
			throw new NotFoundException();
		
		return item.get();
	}
	
	@PostMapping
	public ResponseEntity<Object> create(@Valid @RequestBody Category item) throws BadRequestException, DuplicateKeyException, InvalidDataException {
		
		
		var newItem = srv.add(item); 
		
		URI location = ServletUriComponentsBuilder.fromCurrentRequest().path("/{id}")
			.buildAndExpand(newItem.getCategoryId()).toUri();
		return ResponseEntity.created(location).build();

	}

	@PutMapping("/{id:\\d+}")
	@ResponseStatus(HttpStatus.NO_CONTENT)
	public void update(@PathVariable int id, @Valid @RequestBody Category item) throws BadRequestException, NotFoundException, InvalidDataException {
		if(id != item.getCategoryId())
			throw new BadRequestException("IDs of category don't match");
		
		srv.modify(item);
	}
	
	@GetMapping(path = "/{id:\\d+}/pelis")
	public List<FilmShortDTO> getFilms(@PathVariable int id) throws NotFoundException {
		var item = srv.getOne(id);
		
		if(item.isEmpty())
			throw new NotFoundException();
		
		return item.get().getFilmCategories().stream().map(o -> new FilmShortDTO(o.getFilm().getFilmId(), o.getFilm().getTitle())).toList();
	}

	@DeleteMapping("/{id:\\d+}")
	@ResponseStatus(HttpStatus.NO_CONTENT)
	public void delete(@PathVariable int id) {
		srv.deleteById(id);
	}

}