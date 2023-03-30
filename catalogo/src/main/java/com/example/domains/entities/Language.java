package com.example.domains.entities;

import java.io.Serializable;
import jakarta.persistence.*;
import jakarta.validation.constraints.PastOrPresent;

import java.sql.Timestamp;
import java.util.List;

import com.example.domains.core.entities.EntityBase;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;


/**
 * The persistent class for the language database table.
 * 
 */
@Entity
@Table(name="language")
@NamedQuery(name="Language.findAll", query="SELECT l FROM Language l")
public class Language extends EntityBase<Language> implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	@Column(name="language_id", unique=true, nullable=false)
	@JsonProperty("ID")
	private int languageId;

	@Column(name="last_update", insertable=false, updatable=false, nullable=false)
	@JsonIgnore
	@PastOrPresent	//no puede actualizarse en el futuro 
	private Timestamp lastUpdate;

	@Column(nullable=false, length=20)
	@JsonProperty("Language")
	private String name;

	//bi-directional many-to-one association to Film
	@OneToMany(mappedBy="language")
	private List<Film> films;

	//bi-directional many-to-one association to Film
	@OneToMany(mappedBy="languageVO")
	private List<Film> filmsVO;

	public Language() {
	}

	@Override
	public String toString() {
		return "Language [languageId=" + languageId + ", lastUpdate=" + lastUpdate + ", name=" + name + "]";
	}

	public int getLanguageId() {
		return this.languageId;
	}

	public void setLanguageId(int languageId) {
		this.languageId = languageId;
	}

	public Timestamp getLastUpdate() {
		return this.lastUpdate;
	}

	public void setLastUpdate(Timestamp lastUpdate) {
		this.lastUpdate = lastUpdate;
	}

	public String getName() {
		return this.name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public List<Film> getFilms() {
		return this.films;
	}

	public void setFilms(List<Film> films) {
		this.films = films;
	}

	public Film addFilm(Film film) {
		getFilms().add(film);
		film.setLanguage(this);

		return film;
	}

	public Film removeFilm(Film film) {
		getFilms().remove(film);
		film.setLanguage(null);

		return film;
	}

	public List<Film> getFilmsVO() {
		return this.filmsVO;
	}

	public void setFilmsVO(List<Film> filmsVO) {
		this.filmsVO = filmsVO;
	}

	public Film addFilmsVO(Film filmsVO) {
		getFilmsVO().add(filmsVO);
		filmsVO.setLanguageVO(this);

		return filmsVO;
	}

	public Film removeFilmsVO(Film filmsVO) {
		getFilmsVO().remove(filmsVO);
		filmsVO.setLanguageVO(null);

		return filmsVO;
	}

}