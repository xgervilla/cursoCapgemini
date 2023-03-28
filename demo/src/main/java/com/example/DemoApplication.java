package com.example;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.domain.Sort;

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
		
		//dao.findTop5ByFirstNameStartingWithOrderByLastNameDesc("P").forEach(System.out::println);
		//dao.findTop5ByFirstNameStartingWith("P", Sort.by("firstName")).forEach(System.out::println);
		
		System.out.println("");
		dao.findConNativeSQL(3).forEach(System.out::println);
		
		System.out.println("");
		dao.findConJPQL(3).forEach(System.out::println);
	}

}
