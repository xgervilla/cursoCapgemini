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
import com.example.domains.entities.dtos.ElementoDTO;
import com.example.domains.entities.dtos.FilmShortDTO;
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
import org.springframework.http.HttpStatus;

@RestController
@Tag(name = "Category-service", description = "Category management")
@RequestMapping(path = { "/api/categorias/v1", "/api/categorias" , "/api/categories", "/api/categories/v1"})
public class CategoryResource {
	
	@Autowired
	private CategoryService srv;
	
	@Operation(summary = "Get all categories", description = "Get all categories")
	@GetMapping
	public List<Category> getAll() {
		return srv.getByProjection(Category.class);
	}
	
	@Hidden
	@GetMapping(params = "page")
	public Page<Category> getAllPageable(Pageable page) {
		return srv.getByProjection(page, Category.class);
	}
	
	@Operation(summary = "Category latest releases", description = "Get the newest releases of categories")
	@GetMapping(params = "novedades")
	public List<Category> getNovedades(@RequestParam(required = false, name = "novedades", defaultValue = "") String fecha) {
		if (fecha.length() != 19)
			return srv.novedades(Timestamp.from(Instant.now().minusSeconds(3600)));
		return srv.novedades(Timestamp.valueOf(fecha));
	}

	@Operation(summary = "Get one category", description = "Get all attributes of a category")	
	@GetMapping(path = "/{id:\\d+}")
	public Category getOne(@PathVariable int id) throws NotFoundException {
		var item = srv.getOne(id);
		
		if(item.isEmpty())
			throw new NotFoundException();
		
		return item.get();
	}
	
	@Operation(summary = "Create new category", description = "Create a new category")
	@PostMapping
	public ResponseEntity<Object> create(@Valid @RequestBody Category item) throws BadRequestException, DuplicateKeyException, InvalidDataException {
		
		
		var newItem = srv.add(item); 
		
		URI location = ServletUriComponentsBuilder.fromCurrentRequest().path("/{id}")
			.buildAndExpand(newItem.getCategoryId()).toUri();
		return ResponseEntity.created(location).build();

	}

	@Operation(summary = "Update a category", description = "Update a category with all its attributes")
	@PutMapping("/{id:\\d+}")
	@ResponseStatus(HttpStatus.NO_CONTENT)
	public void update(@PathVariable int id, @Valid @RequestBody Category item) throws BadRequestException, NotFoundException, InvalidDataException {
		if(id != item.getCategoryId())
			throw new BadRequestException("IDs of category don't match");
		
		srv.modify(item);
	}
	
	@Operation(summary = "Get all films of category", description = "Get all films that have the desired category")
	@GetMapping(path = "/{id:\\d+}/pelis")
	@Transactional
	public List<FilmShortDTO> getFilms(@PathVariable int id) throws NotFoundException {
		var item = srv.getOne(id);
		
		if(item.isEmpty())
			throw new NotFoundException();
		
		return item.get().getFilmCategories().stream().map(o -> new FilmShortDTO(o.getFilm().getFilmId(), o.getFilm().getTitle())).toList();
	}

	@Operation(summary = "Delete a category", description = "Delete a category")
	@DeleteMapping("/{id:\\d+}")
	@ResponseStatus(HttpStatus.NO_CONTENT)
	public void delete(@PathVariable int id) {
		srv.deleteById(id);
	}

}