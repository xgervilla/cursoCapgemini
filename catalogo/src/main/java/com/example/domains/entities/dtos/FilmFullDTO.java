package com.example.domains.entities.dtos;

import java.math.BigDecimal;
import java.util.List;

import com.example.domains.entities.Actor;
import com.example.domains.entities.Category;
import com.example.domains.entities.Film;
import com.example.domains.entities.Film.Rating;
import com.example.domains.entities.Language;
import com.fasterxml.jackson.annotation.JsonProperty;

import lombok.Value;

@Value
public class FilmFullDTO {
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
	
	private List<Integer> actors;
	
	private List<Integer> categories;
	
	public static FilmFullDTO from(Film target) {
		return new FilmFullDTO(target.getFilmId(), target.getTitle(), target.getDescription(), target.getLength(), target.getRating(), target.getReleaseYear(), target.getRentalDuration(), target.getRentalRate(), target.getReplacementCost(),
				target.getLanguage() == null ? null : target.getLanguage(),
				target.getLanguageVO() == null ? null : target.getLanguageVO(),
				target.getActors().stream().map(item-> item.getActorId()).sorted().toList(),
				target.getCategories().stream().map(item->item.getCategoryId()).sorted().toList()
				);
	}
	
	public static Film from(FilmFullDTO target) {
		Film newFilm = new Film(target.getFilmId(), target.getDescription(), target.getLength(), target.getRating(), target.getReleaseYear(), target.getRentalDuration(), target.getRentalRate(), target.getReplacementCost(), target.getTitle(), target.getLanguage(), target.getLanguageVO());
		if (target.getActors() != null)
			target.getActors().stream().forEach(id -> newFilm.addActor(id));
		if (target.getCategories() != null)
			target.getCategories().stream().forEach(id -> newFilm.addCategory(id));
		
		return newFilm;
	}
}
