package com.example.ejemplos;

import java.util.Optional;

import com.example.exceptions.InvalidArgumentException;

import lombok.Builder;
import lombok.Data;

/*
 * Casos especiales de los atributos:
 *  - Valores nulos en el apellido (permitido)
 *  - Palabras con acentos (permitido)
 *  - Palabras con símbolos (no permitido? es posible al codificarlo como string, se debería hacer limpieza a caracteres del alfabeto?)
 *  - Números negativos en el ID (NO permitido)
 *  - Valores nulos en el nombre (NO permitido)
 *  - Nombres y apellidos con dos carácteres o menos (NO permitido)
 *  - Nombres y apellidos con string vacía (NO permitido)
 *  - IDs con más números de los que soporta el tipo int (NO permitido ni esperado, en la definición de los datos debe nespecificarse los rangos de valores y adaptar el tipo según sea necesario)
 *  - IDs empezando en 0 (permitido aunque no esperado?)
 */

@Builder
public class Persona {
	private int id;
	private String nombre;
	private String apellidos;
	
	public Persona(int id, String nombre, String apellidos) {
		super();
		setId(id);
		setNombre(nombre);
		setApellidos(apellidos);
	}
	
	public Persona(int id, String nombre) {
		super();
		setId(id);
		setNombre(nombre);
		removeApellidos();
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		if(id < 0) throw new IllegalArgumentException();
		this.id = id;
	}

	public String getNombre() {
		return nombre;
	}

	public void setNombre(String nombre) {
		if (nombre == null || nombre == "" || nombre.length() <= 2) throw new IllegalArgumentException();
		this.nombre = nombre;
	}

	public Optional<String> getApellidos() {
		return Optional.ofNullable(this.apellidos);
	}

	public void setApellidos(String apellidos) {
		if (apellidos == null || "".equals(apellidos) || apellidos.length() <= 2) throw new IllegalArgumentException();
		this.apellidos = apellidos;
	}
	
	public void removeApellidos() {
		this.apellidos = null;
	}
	
}
