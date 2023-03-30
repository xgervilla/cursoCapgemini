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
		System.out.println("Aplicación arrancada");
		
		/*var actor = new Actor(0, "Peter","PAN");

		//si el actor no es válido, mostramos los errores
		if (actor.isInvalid())
			System.out.println(actor.getErrorsMessage());
		//si es valido lo almacenamos
		else
			daoActor.save(actor);*/
		
		srvActor.getAll().forEach(System.out::println);
		
		System.out.println("");
		
		srvCategory.getAll().forEach(System.out::println);
		
		System.out.println("");
		
		srvFilm.getAll().forEach(System.out::println);
		
		System.out.println("");
		
		srvLanguage.getAll().forEach(System.out::println);
		
		/*Film f =new Film(0, "Title", new Language(0,"Catalan"));
		Category c = new Category(0, "Drama");
		
		FilmCategory fc = new FilmCategory(new FilmCategoryPK(f.getFilmId(), (byte)c.getCategoryId()), c, f);
		
		System.out.println(fc.toString());*/
	}

}
