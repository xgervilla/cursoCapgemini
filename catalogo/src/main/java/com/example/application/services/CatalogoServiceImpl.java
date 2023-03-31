package com.example.application.services;

import java.sql.Timestamp;
import java.time.Instant;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.domains.contracts.services.*;
import com.example.domains.entities.dtos.ActorDTO;
import com.example.domains.entities.dtos.FilmDTO;
import com.example.domains.entities.dtos.NovedadesDTO;

@Service
public class CatalogoServiceImpl implements CatalogoService{
	@Autowired
	private FilmService filmSrv;
	
	@Autowired
	private ActorService actorSrv;
	
	@Autowired
	private CategoryService catalogoSrv;
	
	@Autowired
	private LanguageService languageSrv;
	
	public NovedadesDTO novedades(Timestamp fecha) {
		if (fecha == null)
			fecha = Timestamp.from(Instant.now().minusSeconds(36000));
		return new NovedadesDTO(
				filmSrv.novedades(fecha).stream().map(item -> FilmDTO.from(item)).toList(),
				actorSrv.novedades(fecha).stream().map(item -> ActorDTO.from(item)).toList(),
				catalogoSrv.novedades(fecha),
				languageSrv.novedades(fecha));
	}
	
	

}