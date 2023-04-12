package com.example.domains.entities.dtos;

import java.math.BigDecimal;
import java.util.List;

import com.example.domains.entities.Actor;
import com.example.domains.entities.Category;
import com.example.domains.entities.Film;
import com.example.domains.entities.Film.Rating;
import com.example.domains.entities.Language;
import com.fasterxml.jackson.annotation.JsonProperty;

import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.media.Schema.AccessMode;
import lombok.Value;

@Schema(name = "Film DTO", description = "Film DTO with all parameters except actos and categories")
@Value
public class FilmDTO {
	@Schema(description = "Film identifier", accessMode = AccessMode.READ_ONLY)
	@JsonProperty("id")
	private int filmId;
	
	@Schema(description = "Film title", required = true)
	@JsonProperty("titulo")
	private String title;
	
	@Schema(description = "Description of the film's plot")
	@JsonProperty("descripcion")
	private String description;
	
	@Schema(description = "Length of the movie in minutes")
	@JsonProperty("duracion")
	private int length;
	
	@Schema(description = "Film's viewership classification")
	@JsonProperty("clasificacion")
	private Rating rating;
	
	@Schema(description = "Release year of the film")
	@JsonProperty("release_year")
	private Short releaseYear;
	
	@Schema(description = "Number of days the film is rented")
	@JsonProperty("rental_duration")
	private byte rentalDuration;
	
	@Schema(description = "Cost of renting the film")
	@JsonProperty("rental_rate")
	private BigDecimal rentalRate;
	
	@Schema(description = "Cost in case the client doesn't return the film")
	@JsonProperty("replacement_cost")
	private BigDecimal replacementCost;
	
	@Schema(description = "Film's screening  language")
	@JsonProperty("lenguaje")
	private Language language;
	
	@Schema(description = "Film's original lagnguage")
	@JsonProperty("vo")
	private Language languageVO;
	
	public static FilmDTO from(Film target) {
		return new FilmDTO(target.getFilmId(), target.getTitle(), target.getDescription(), target.getLength(), target.getRating(), target.getReleaseYear(), target.getRentalDuration(), target.getRentalRate(), target.getReplacementCost(),
				target.getLanguage() == null ? null : target.getLanguage(),
				target.getLanguageVO() == null ? null : target.getLanguageVO()
				);
	}
	
	
	public static Film from(FilmDTO target) {
		return new Film(target.getFilmId(), target.getDescription(), target.getLength(), target.getRating(), target.getReleaseYear(), target.getRentalDuration(), target.getRentalRate(), target.getReplacementCost(), target.getTitle(), target.getLanguage(), target.getLanguageVO());
	}
}
