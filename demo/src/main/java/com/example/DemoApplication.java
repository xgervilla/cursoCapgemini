package com.example;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import com.example.domains.contracts.repositories.ActorRepository;
import com.example.domains.entities.Actor;



@SpringBootApplication
public class DemoApplication implements CommandLineRunner{

	public static void main(String[] args) {
		SpringApplication.run(DemoApplication.class, args);
	}
	
	@Autowired
	ActorRepository dao;
	
	@Override
	public void run(String... args) throws Exception {
		System.out.println("Aplicación arrancada");
		
		//ejemplo para almacenar un nuevo actor
		//var actor = new Actor(0,"Pepito","Grillo");
		//dao.save(actor);
		
		//ejemplo de consulta a la base de datos para obtener todos los actores mediante ActorRepository
		//dao.findAll().forEach(System.out::println);
		
		//obtenemos el actor con id 201 -> devuelve un objeto Optional para comprobar si es nulo o no
		var item = dao.findById(201);
		//si el actor se ha encontrado, lo modificamos y volvemos a guardar
		if (item.isPresent()) {
			//obtenemos el actor
			var actor = item.get();
			//le modificamos el apellido
			actor.setLastName(actor.getLastName().toUpperCase());
			//lo guardamos de nuevo en la base de datos (update)
			dao.save(actor);
			//imprimimos los actores de nuevo para ver los cambios
			dao.findAll().forEach(System.out::println);
		}
		else {
			System.out.println("Actor no encontrado");
		}
	}

}
