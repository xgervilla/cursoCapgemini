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

@SpringBootApplication
public class CatalogoApplication implements CommandLineRunner{

	public static void main(String[] args) {
		SpringApplication.run(CatalogoApplication.class, args);
	}
	
	@Autowired
	ActorService srvActor;
	
	@Autowired
	FilmService srvFilm;
	
	@Autowired
	CategoryService srvCategory;
	
	@Autowired
	LanguageService srvLanguage;
	
	@Override
	public void run(String... args) throws Exception {
		System.out.println("------------------ Aplicación arrancada ------------------");
		
		//srvFilm.getAll().forEach(System.out::println);
		
		/*var movie = new Film("Hola Mundo", new Language(2));
		movie.setRentalDuration((byte)3);
		movie.setRating(Rating.GENERAL_AUDIENCES);
		movie.setLength(3);
		movie.addActor(1);
		movie.addActor(2);
		movie.addActor(3);
		movie.addCategory(2);
		movie.addCategory(4);*/
		//srvFilm.add(movie);
		//------------
		var movie = srvFilm.getOne(1000).get();
		movie.removeActor(new Actor(1));
		movie.removeActor(new Actor(2));
		movie.removeCategory(movie.getCategories().get(0));
		movie.addCategory(1);
		movie.setTitle("Adios mundo");
		//srvFilm.modify(movie);
		
	}

}
