package com.example;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import com.example.domains.contracts.repositories.ActorRepository;
import com.example.domains.contracts.repositories.CategoryRepository;
import com.example.domains.contracts.repositories.FilmRepository;
import com.example.domains.contracts.repositories.LanguageRepository;
import com.example.domains.contracts.services.ActorService;
import com.example.domains.contracts.services.CategoryService;
import com.example.domains.contracts.services.FilmService;
import com.example.domains.contracts.services.LanguageService;
import com.example.domains.entities.Actor;
import com.example.domains.entities.Category;
import com.example.domains.entities.Film;
import com.example.domains.entities.Film.Rating;
import com.example.domains.entities.FilmCategory;
import com.example.domains.entities.FilmCategoryPK;
import com.example.domains.entities.Language;
import com.example.domains.entities.dtos.ActorDTO;
import com.example.domains.entities.dtos.ActorShort;

import jakarta.transaction.Transactional;

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
