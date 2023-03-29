package com.example;
import java.util.stream.Stream;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;

import com.example.domains.contracts.repositories.ActorRepository;
import com.example.domains.entities.Actor;
import com.example.domains.entities.dtos.ActorDTO;

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
		
//		var actor = new Actor(0, "4G","");
//		
//		if (actor.isInvalid())
//			System.out.println(actor.getErrorsMessage());
//		else {
//			dao.save(actor);
//		}
//		dao.findAll().forEach(System.out::println);
//		var rslt = dao.findAll(PageRequest.of(1,20, Sort.by("actorId")));
//		rslt.getContent().stream().map(item -> ActorDTO.from(item)).forEach(System.out::println);
		//dao.findByActorIdNotNull().forEach(System.out::println);
		dao.findByActorIdNotNull().forEach(item -> System.out.println(item.getActorId() + " " + item.getNombre()));
		
	}

}
