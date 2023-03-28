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
		dao.findAll().forEach(System.out::println);
	}

}
