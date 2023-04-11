package com.example.application.resources;

import java.sql.Timestamp;
import java.time.Instant;
import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.example.application.services.CatalogoService;
import com.example.application.services.CatalogoServiceImpl;
import com.example.domains.entities.Category;
import com.example.domains.entities.Language;
import com.example.domains.entities.dtos.ActorDTO;
import com.example.domains.entities.dtos.FilmDTO;
import com.example.domains.entities.dtos.NovedadesDTO;
import com.example.exceptions.NotFoundException;

import lombok.Value;

@RestController
@RequestMapping(path = { "/api/catalogo/v1", "/api/catalogo" ,"/api/catalogo/v1", "/api/catalogo"})
public class CatalogoResource {

	@Autowired
	CatalogoServiceImpl srv;
	
	@Value
	public static class CatalogoResources{
		
	}
	
	@GetMapping(path = "/actors")
	public List<ActorDTO> getOne(@RequestParam(required = false, name = "fecha", defaultValue = "") String fecha) {
		//"2022-01-01 00:00:00"
		if (fecha.length() != 19)
			return srv.novedades(Timestamp.from(Instant.now().minusSeconds(3600))).getActors();
		var timestamp = Timestamp.valueOf(fecha);
		return srv.novedades(timestamp).getActors(); 
	}
	
	@GetMapping(path = "/films")
	public List<FilmDTO> getNovedadesFilm(@RequestParam(required = false, name = "fecha", defaultValue = "") String fecha) {
		//"2022-01-01 00:00:00"
		if (fecha.length() != 19)
			return srv.novedades(Timestamp.from(Instant.now().minusSeconds(3600))).getFilms();
		var timestamp = Timestamp.valueOf(fecha);
		return srv.novedades(timestamp).getFilms(); 
	}
	
	@GetMapping(path = "/categories")
	public List<Category> getNovedadesCategory(@RequestParam(required = false, name = "fecha", defaultValue = "") String fecha) {
		//"2022-01-01 00:00:00"
		if (fecha.length() != 19)
			return srv.novedades(Timestamp.from(Instant.now().minusSeconds(3600))).getCategories();
		var timestamp = Timestamp.valueOf(fecha);
		return srv.novedades(timestamp).getCategories(); 
	}
	
	
	@GetMapping(path = "/languages")
	public List<Language> getNovedadesLanguage(@RequestParam(required = false, name = "fecha", defaultValue = "") String fecha) {
		//"2022-01-01 00:00:00"
		if (fecha.length() != 19)
			return srv.novedades(Timestamp.from(Instant.now().minusSeconds(3600))).getLanguages();
		var timestamp = Timestamp.valueOf(fecha);
		return srv.novedades(timestamp).getLanguages(); 
	}
	
}
