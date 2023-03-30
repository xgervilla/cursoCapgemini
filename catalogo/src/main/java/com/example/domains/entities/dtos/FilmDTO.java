package com.example.domains.entities.dtos;

import java.math.BigDecimal;

import com.example.domains.entities.Film;
import com.example.domains.entities.Film.Rating;
import com.example.domains.entities.Language;
import com.fasterxml.jackson.annotation.JsonProperty;

import lombok.Value;

@Value
public class FilmDTO {
	@JsonProperty("id")
	private int filmId;
	@JsonProperty("titulo")
	private String title;
	@JsonProperty("descripcion")
	private String description;
	@JsonProperty("duracion")
	private int length;
	@JsonProperty("valoracion")
	private Rating rating;
	@JsonProperty("release_year")
	private Short releaseYear;
	@JsonProperty("rental_duration")
	private byte rentalDuration;
	@JsonProperty("rental_rate")
	private BigDecimal rentalRate;
	@JsonProperty("replacement_cost")
	private BigDecimal replacementCost;
	@JsonProperty("lenguaje")
	private Language language;
	@JsonProperty("vo")
	private Language languageVO;
	
	public static FilmDTO from(Film target) {
		return new FilmDTO(target.getFilmId(), target.getTitle(), target.getDescription(), target.getLength(), target.getRating(), target.getReleaseYear(), target.getRentalDuration(), target.getRentalRate(), target.getReplacementCost(), target.getLanguage(), target.getLanguageVO());
	}
	
	public static Film from(FilmDTO target) {
		return new Film(target.getFilmId(), target.getDescription(), target.getLength(), target.getRating(), target.getReleaseYear(), target.getRentalDuration(), target.getRentalRate(), target.getReplacementCost(), target.getTitle(), target.getLanguage(), target.getLanguageVO());
	}
}