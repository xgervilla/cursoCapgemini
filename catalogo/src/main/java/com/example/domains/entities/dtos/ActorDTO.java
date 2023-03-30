package com.example.domains.entities.dtos;

import com.example.domains.entities.Actor;
import com.fasterxml.jackson.annotation.JsonProperty;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.Value;

@Value
public class ActorDTO {
	@JsonProperty("id")
	private int actorId;
	@JsonProperty("nombre")
	private String firstName;
	@JsonProperty("apellidos")
	private String lastName;
	
	//conversión de entidad a DTO
	public static ActorDTO from(Actor target) {
		return new ActorDTO(target.getActorId(), target.getFirstName(), target.getLastName());
	}
	
	//conversión de DTO a entidad
	public static Actor from(ActorDTO target) {
		return new Actor(target.getActorId(), target.getFirstName(), target.getLastName());
	}
	
}