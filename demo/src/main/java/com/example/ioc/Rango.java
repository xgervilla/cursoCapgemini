package com.example.ioc;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

import lombok.Builder;
import lombok.Data;

@Data
@Component
//@Builder //-> comentado porque sino genera error (por cargar los valores de application.properties que tiene prioridad)
@ConfigurationProperties("rango")
public class Rango {
	/*
	 * Al asignar los valores mediante application.properties los nombres deben coincidir (el orden no importa)
	 * Si los nombres no coinciden (no se encuentran en application.properties) se asigna null (0 en caso de números)
	*/
	private int min;
	private int max;
}
