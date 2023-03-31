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
	
	@Autowired
	ActorService srvActor;
	
	@Autowired
	FilmService srvFilm;
	
	@Autowired
	CategoryService srvCategory;
	
	@Autowired
	LanguageService srvLanguage;
	
	@Override
	@Transactional
	public void run(String... args) throws Exception {
		System.out.println("------------------ Aplicación arrancada ------------------");
		
		//srvFilm.getAll().forEach(System.out::println);
		
		
		//------------ ADD A NEW MOVIE
//		var movie = new Film("Prueba numero 2", new Language(3));
//		movie.setRentalDuration((byte)3);
//		movie.setRating(Rating.RESTRICTED);
//		movie.setLength(72);
//		movie.addActor(5);
//		movie.addActor(6);
//		movie.addActor(7);
//		movie.addCategory(1);
//		movie.addCategory(2);
//		movie.addCategory(4);
//		movie.addCategory(5);
//		srvFilm.add(movie);
//		
//		
//		System.out.println("\nCreated movie" + movie.getActors() + "\n" + movie.getCategories());
//		
//		//------------ MODIFY A MOVIE
//		movie = srvFilm.getOne(movie.getFilmId()).get();
//		movie.removeActor(new Actor(5));
//		movie.addActor(new Actor(8));
//		movie.addActor(new Actor(9));
//		movie.removeCategory(movie.getCategories().get(0));
//		movie.addCategory(3);
//		movie.setTitle("Fin de la pelicula");
//		srvFilm.modify(movie);
//		
//		System.out.println("\nModified movie" + movie.getActors() + "\n" + movie.getCategories());
//		
//		movie = srvFilm.getOne(movie.getFilmId()).get();
//		
//		System.out.println("\nModified from DB" + movie.getActors() + "\n" + movie.getCategories());
		
		
		//-------------- DELETE BY ID
//		srvFilm.deleteById(1015);
//		srvFilm.deleteById(1014);
//		srvFilm.deleteById(1013);
//		srvFilm.deleteById(1008);
		
	}

}
