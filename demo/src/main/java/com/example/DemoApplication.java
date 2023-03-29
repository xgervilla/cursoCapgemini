package com.example;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.domain.Sort;

import com.example.domains.contracts.repositories.ActorRepository;
import com.example.domains.entities.Actor;

import jakarta.transaction.Transactional;
import jakarta.validation.Validation;
import jakarta.validation.Validator;



@SpringBootApplication
public class DemoApplication implements CommandLineRunner{

	public static void main(String[] args) {
		SpringApplication.run(DemoApplication.class, args);
	}
	
	@Autowired
	ActorRepository dao;
	
	@Override
	@Transactional
	public void run(String... args) throws Exception {
		System.out.println("Aplicación arrancada");
		
		var actor = new Actor(0, "p","pollo");
		//validación automática dentro del save
		dao.save(actor);
	}

}
