package com.example.domains.entities;

import java.io.Serializable;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.PastOrPresent;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;

import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

import com.example.domains.core.entities.EntityBase;


/**
 * The persistent class for the actor database table.
 * 
 */
@Entity
@Table(name="actor")
@NamedQuery(name="Actor.findAll", query="SELECT a FROM Actor a")
public class Actor extends EntityBase<Actor> implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	@Column(name="actor_id", unique=true, nullable=false)
	private int actorId;

	@Column(name="first_name", nullable=false, length=45)
	@NotBlank
	@Size(max=45, min=2)
	private String firstName;

	@Column(name="last_name", nullable=false, length=45)
	@Size(max=45, min=2)
	@NotBlank
	@Pattern(regexp = "[A-Z]+", message="must be in capital letters")
	private String lastName;

	@Column(name="last_update", insertable=false, updatable=false, nullable=false)
	@PastOrPresent
	private Timestamp lastUpdate;

	//bi-directional many-to-one association to FilmActor
	@OneToMany(mappedBy = "actor", fetch = FetchType.LAZY)
	private List<FilmActor> filmActors = new ArrayList<>();

	//constructor sin parámetros
	public Actor() {
	}
	
	//constructor con parámetros
	public Actor(int actorId) {
		super();
		this.actorId = actorId;
	}
	
	//constructor con parámetros
	public Actor(int actorId, String firstName, String lastName) {
		super();
		this.actorId = actorId;
		this.firstName = firstName;
		this.lastName = lastName;
	}


	//si es necesario modificar más de un atributo es 'necesario' crear un método para que se gestion todo de manera automática
	public void jubilate() {
	}
	
	//ejemplo: al recibir un premio se debería añadir a la lista de premios, pasarlo a inactivo...
	public void recibePremio(String premio) {
		
	}

	@Override
	public String toString() {
		return "Actor [actorId=" + actorId + ", firstName=" + firstName + ", lastName=" + lastName + ", lastUpdate="
				+ lastUpdate + "]";
	}

	@Override
	public int hashCode() {
		return Objects.hash(actorId);
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Actor other = (Actor) obj;
		return actorId == other.actorId;
	}

	public int getActorId() {
		return this.actorId;
	}

	public void setActorId(int actorId) {
		this.actorId = actorId;
	}

	public String getFirstName() {
		return this.firstName;
	}

	public void setFirstName(String firstName) {
		this.firstName = firstName;
	}

	public String getLastName() {
		return this.lastName;
	}

	public void setLastName(String lastName) {
		this.lastName = lastName;
	}

	public Timestamp getLastUpdate() {
		return this.lastUpdate;
	}

	public void setLastUpdate(Timestamp lastUpdate) {
		this.lastUpdate = lastUpdate;
	}

	public List<FilmActor> getFilmActors() {
		return this.filmActors;
	}

	public void setFilmActors(List<FilmActor> filmActors) {
		this.filmActors = filmActors;
	}

	public FilmActor addFilmActor(FilmActor filmActor) {
		getFilmActors().add(filmActor);
		filmActor.setActor(this);

		return filmActor;
	}
	
	public List<Film> getFilms(){
		return this.filmActors.stream().map(item -> item.getFilm()).toList();
	}
	
	public void clearFilms() {
		filmActors = new ArrayList<FilmActor>();
	}
	
	public void removeFilm(Film film) {
		var filmActor = filmActors.stream().filter(item -> item.getFilm().equals(film)).findFirst();
		if(filmActor.isEmpty())
			return;
		filmActors.remove(filmActor.get());
	}
	
	public FilmActor addFilmActor(Film film) {
		FilmActor filmActor = new FilmActor(film, this);
		getFilmActors().add(filmActor);
		return filmActor;
	}
	
	public void addFilm(Film film) {
		FilmActor filmActor = new FilmActor(film, this);
		getFilmActors().add(filmActor);
	}
	
	public void addFilm(int filmId) {
		addFilm(new Film(filmId));
	}
	
	public Actor merge(Actor target) {
		target.firstName = firstName;
		target.lastName = lastName;
		
		//borro los actores que sobran
		target.getFilms().stream()
			.filter(item -> !getFilms().contains(item))
			.forEach(item -> target.removeFilm(item));
		
		//añado los actores que faltan
		getFilms().stream()
			.filter(item -> !target.getFilms().contains(item))
			.forEach(item -> target.addFilm(item));
		
		return target;
	}

	public FilmActor removeFilmActor(FilmActor filmActor) {
		getFilmActors().remove(filmActor);
		filmActor.setActor(null);

		return filmActor;
	}

}