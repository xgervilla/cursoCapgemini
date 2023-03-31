package com.example.domains.entities.dtos;

import java.util.List;

import com.example.domains.entities.Category;
import com.example.domains.entities.Language;
import com.fasterxml.jackson.annotation.JsonProperty;

import lombok.Value;

@Value
public class NovedadesDTO {
	@JsonProperty("peliculas")
	private List<FilmDTO> films;
	
	@JsonProperty("actores")
	private List<ActorDTO> actors;
	
	@JsonProperty("categorias")
	private List<Category> categories;
	
	@JsonProperty("lenguajes")
	private List<Language> languages;
	
	public static NovedadesDTO from(List<FilmDTO> filmSource, List<ActorDTO> actorsSource, List<Category> categorySource, List<Language> languageSource) {
		return new NovedadesDTO(filmSource, actorsSource, categorySource, languageSource);
	}
}
