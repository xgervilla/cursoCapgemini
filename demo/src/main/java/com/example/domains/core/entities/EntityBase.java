package com.example.domains.core.entities;

import java.util.Set;

import jakarta.persistence.Transient;
import jakarta.validation.ConstraintViolation;
import jakarta.validation.Validation;
import jakarta.validation.Validator;

import com.fasterxml.jackson.annotation.JsonIgnore;


//En vez de validar cada nuevo objeto uno por uno se utiliza la entidad base para reutilizar código ya que la estructura de validación es siempre la misma
public abstract class EntityBase<E> {
	
	@Transient
	@JsonIgnore
	@SuppressWarnings("unchecked")
	public Set<ConstraintViolation<E>> getErrors() {
		Validator validator = Validation.buildDefaultValidatorFactory().getValidator();
		return validator.validate((E)this);
	}
	
	@Transient
	@JsonIgnore
	public String getErrorsMessage() {
		if(isValid()) return "";
		StringBuilder sb = new StringBuilder("ERRORES: ");
		getErrors().stream().forEachOrdered(item -> sb.append(item.getPropertyPath() + ": " + item.getMessage() + ". "));
		//alternativa para mostrar los mensajes de error de manera ordenada
		//getErrors().stream().map(item -> item.getPropertyPath() + ": " + item.getMessage() + ". ").sorted().forEach(sb::append);
		return sb.toString().trim();
	}
	
	@Transient
	@JsonIgnore
	public boolean isValid() {
		return getErrors().size() == 0;
	}

	@Transient
	@JsonIgnore
	public boolean isInvalid() {
		return !isValid();
	}

}