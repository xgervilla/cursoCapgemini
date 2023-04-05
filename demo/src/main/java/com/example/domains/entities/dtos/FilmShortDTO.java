package com.example.domains.entities.dtos;

import com.example.domains.entities.Film;
import com.example.domains.entities.Language;
import com.fasterxml.jackson.annotation.JsonProperty;

import lombok.Value;

@Value
public class FilmShortDTO {
	@JsonProperty("id")
	private int filmId;
	@JsonProperty("title")
	private String title;
	
	@JsonProperty("language")
	Language language;
	
	public static FilmShortDTO from(Film source) {
		return new FilmShortDTO(source.getFilmId(), source.getTitle(), source.getLanguage());
	}
	
	public static Film from(FilmShortDTO source) {
		return new Film(source.getTitle(), source.getLanguage());
	}

}
