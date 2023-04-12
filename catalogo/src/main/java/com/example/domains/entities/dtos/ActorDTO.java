package com.example.domains.entities.dtos;

import com.example.domains.entities.Actor;
import com.fasterxml.jackson.annotation.JsonProperty;

import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.media.Schema.AccessMode;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.Value;

@Schema(name="Actor DTO", description = "Version completa de Actor")
@Value
public class ActorDTO {
	@Schema(description = "Actor identifier", required = true, accessMode = AccessMode.READ_ONLY)
	@JsonProperty("id")
	private int actorId;
	
	@Schema(description = "Actor name", required = true, minLength = 2)
	@JsonProperty("nombre")
	private String firstName;
	
	@Schema(description = "Actor surname", required = true)
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