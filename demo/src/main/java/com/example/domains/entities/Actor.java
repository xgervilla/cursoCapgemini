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
import com.example.domains.core.validations.NIF;


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

	//Column(length) y Size(max) representan lo mismo pero se utilizan en contextos distintos por lo que se debe especificar "por duplicado"
	@Column(name="first_name", nullable=false, length=45)
	@NotBlank	//no puede ser una String llena de espacios vacíos "     "
	@Size(max=45, min=2)	//no puede tener más de 45 carácteres y añadimos una regla "de cliente" para forzar un mínimo de 2 carácteres
	//@NIF
	private String firstName;

	@Column(name="last_name", nullable=false, length=45)
	@Size(max=45, min=2)
	@NotBlank
	@Pattern(regexp = "[A-Z]+", message="Tiene que estar en mayusculas")
	private String lastName;

	@Column(name="last_update", insertable=true, updatable=false, nullable=false)
	@PastOrPresent	//no puede actualizarse en el futuro 
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

	public FilmActor removeFilmActor(FilmActor filmActor) {
		getFilmActors().remove(filmActor);
		filmActor.setActor(null);

		return filmActor;
	}

}