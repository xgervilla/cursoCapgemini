package com.example.domains.entities;

import java.io.Serializable;
import jakarta.persistence.*;
import java.sql.Timestamp;


/**
 * The persistent class for the film_category database table.
 * 
 */
@Entity
@Table(name="film_category")
@NamedQuery(name="FilmCategory.findAll", query="SELECT f FROM FilmCategory f")
public class FilmCategory implements Serializable {
	private static final long serialVersionUID = 1L;

	@EmbeddedId
	private FilmCategoryPK id;

	@Column(name="last_update", nullable=false)
	private Timestamp lastUpdate;

	//bi-directional many-to-one association to Category
	@ManyToOne
	@JoinColumn(name="category_id", nullable=false, insertable=false, updatable=false)
	private Category category;

	//bi-directional many-to-one association to Film
	@ManyToOne
	@JoinColumn(name="film_id", nullable=false, insertable=false, updatable=false)
	private Film film;

	public FilmCategory() {
	}

	public FilmCategory(FilmCategoryPK id, Category category, Film film) {
		super();
		this.id = id;
		this.category = category;
		this.film = film;
	}
	
	public FilmCategory(Film film, Category category) {
		super();
		this.id = new FilmCategoryPK(film.getFilmId(), (byte)category.getCategoryId());
		this.category = category;
		this.film = film;
	}

	public FilmCategory(FilmCategoryPK id) {
		super();
		this.id = id;
	}

	@Override
	public String toString() {
		return "FilmCategory [id=" + id + ", category=" + category + ", film=" + film + "]";
	}

	public FilmCategoryPK getId() {
		return this.id;
	}

	public void setId(FilmCategoryPK id) {
		this.id = id;
	}

	public Timestamp getLastUpdate() {
		return this.lastUpdate;
	}

	public void setLastUpdate(Timestamp lastUpdate) {
		this.lastUpdate = lastUpdate;
	}

	public Category getCategory() {
		return this.category;
	}

	public void setCategory(Category category) {
		this.category = category;
	}

	public Film getFilm() {
		return this.film;
	}

	public void setFilm(Film film) {
		this.film = film;
	}

}