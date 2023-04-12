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

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.Value;

@RestController
@Tag(name = "Catalogo-service", description = "Novedades management")
@RequestMapping(path = { "/api/catalogo/v1", "/api/catalogo"})
public class CatalogoResource {

	@Autowired
	CatalogoService srv;
	
	@Operation(summary = "Get newest releases", description = "Get all newest releases of films, actors, categories and languages")
	@GetMapping(path = "/novedades")
	public NovedadesDTO getNovedades(@RequestParam(required = false, name = "fecha", defaultValue = "") String fecha) {
		if (fecha.contains("%20"))
			fecha = fecha.replace("%20", " ");
		if (fecha.length() != 19)
			return srv.novedades(Timestamp.from(Instant.now().minusSeconds(3600)));
		
		var timestamp = Timestamp.valueOf(fecha);
		return srv.novedades(timestamp); 
	}
}
