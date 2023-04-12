package com.example.domains.entities.dtos;

import com.example.domains.entities.Film;
import com.fasterxml.jackson.annotation.JsonProperty;

import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.media.Schema.AccessMode;
import lombok.Value;

@Value
@Schema(name = "Film DTO (short version)", description = "Short version of actor DTO")
public class FilmShortDTO {
	
	@Schema(description = "Film identifier", required = true, accessMode = AccessMode.READ_ONLY)
	private int filmId;
	
	@Schema(description = "Film title", required = true)
	private String title;
	
	public static FilmShortDTO from(Film source) {
		return new FilmShortDTO(source.getFilmId(), source.getTitle());
	}

}
