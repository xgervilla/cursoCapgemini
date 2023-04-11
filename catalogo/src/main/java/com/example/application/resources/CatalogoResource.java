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
@RequestMapping(path = { "/api/catalogo/v1", "/api/catalogo"})
public class CatalogoResource {

	@Autowired
	CatalogoService srv;
	
	@GetMapping(path = "/novedades")
	public NovedadesDTO getNovedaesActor(@RequestParam(required = false, name = "fecha", defaultValue = "") String fecha) {
		//"2022-01-01 00:00:00"
		System.out.println(fecha);
		if (fecha.contains("%20"))
			fecha = fecha.replace("%20", " ");
		if (fecha.length() != 19)
			return srv.novedades(Timestamp.from(Instant.now().minusSeconds(3600)));
		
		var timestamp = Timestamp.valueOf(fecha);
		return srv.novedades(timestamp); 
	}
	
}
