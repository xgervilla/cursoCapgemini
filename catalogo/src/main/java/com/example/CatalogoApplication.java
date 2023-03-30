package com.example;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import com.example.domains.contracts.repositories.ActorRepository;
import com.example.domains.contracts.repositories.CategoryRepository;
import com.example.domains.contracts.repositories.FilmRepository;
import com.example.domains.contracts.repositories.LanguageRepository;
import com.example.domains.contracts.services.CategoryService;
import com.example.domains.contracts.services.FilmService;
import com.example.domains.contracts.services.LanguageService;
import com.example.domains.entities.Actor;
import com.example.domains.entities.Category;
import com.example.domains.entities.Film;
import com.example.domains.entities.Language;
import com.example.domains.entities.dtos.ActorDTO;
import com.example.domains.entities.dtos.ActorShort;

@SpringBootApplication
public class CatalogoApplication implements CommandLineRunner{

	public static void main(String[] args) {
		SpringApplication.run(CatalogoApplication.class, args);
	}
	
	@Autowired
	ActorRepository daoActor;
	
	@Autowired
	FilmRepository daoFilm;
	
	@Autowired
	CategoryRepository daoCategory;
	
	@Autowired
	LanguageRepository daoLanguage;
	
	@Override
	public void run(String... args) throws Exception {
		System.out.println("Aplicación arrancada");
		
		/*var actor = new Actor(0, "Peter","PAN");

		//si el actor no es válido, mostramos los errores
		if (actor.isInvalid())
			System.out.println(actor.getErrorsMessage());
		//si es valido lo almacenamos
		else
			daoActor.save(actor);*/
		
		//daoActor.findAllBy(Actor.class).forEach(System.out::println);
		
		//daoCategory.findAllBy(Category.class).forEach(System.out::println);
		
		//daoFilm.findAllBy(Film.class).forEach(System.out::println);
		
		daoLanguage.findAllBy(Language.class).forEach(System.out::println);
	}

}
