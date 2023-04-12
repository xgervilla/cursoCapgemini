package com.example;

import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import io.swagger.v3.oas.annotations.OpenAPIDefinition;
import io.swagger.v3.oas.annotations.info.Info;

@OpenAPIDefinition(
    info = @Info(
            title = "Microservicio: Catalogo de peliculas",
            version = "1.0",
            description = "Implementacion de microservicios de un catalogo de peliculas siguiendo la base de datos de muestra de Sakila.<br><br>Desarrollado e implementado por Xavier Gervilla.")
    )
@SpringBootApplication
public class CatalogoApplication implements CommandLineRunner{

	public static void main(String[] args) {
		SpringApplication.run(CatalogoApplication.class, args);
	}
	
	
	
	@Override
	public void run(String... args) throws Exception {
		System.out.println("------------------ Aplicación arrancada ------------------");
	}

}
