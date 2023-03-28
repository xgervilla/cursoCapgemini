package com.example;

import java.sql.ResultSet;
import java.sql.SQLException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Profile;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;

import com.example.ioc.Rango;
import com.example.ioc.StringRepository;
import com.example.ioc.StringRepositoryImpl;
import com.example.ioc.StringRepositoryMockImpl;
import com.example.ioc.StringService;
import com.example.ioc.StringServiceImpl;
import com.example.ioc.UnaTonteria;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.experimental.var;


@SpringBootApplication
public class DemoApplication implements CommandLineRunner{

	public static void main(String[] args) {
		SpringApplication.run(DemoApplication.class, args);
	}

	//inyección de datos directamente en el atributo (de manera automática)
	/*	si se cambia el @Repository de la clase StringRepositoryImpl a StringRepositoryMockImpl el comportamiento cambia a la clase de pruebas -> la inyección cambia a la nueva clase según la anotación
		si no hay @Repository salta un error y el servicio no se ejecuta ("ante la duda, error y soluciona")
		si hay dos Repository salta un error y el servicio no se ejecuta (no puede haber ambigüdedad)
	Como solucionar la ambiguedad:
		- dejar uno como "primario" (de mayor prioridad) > a pesar de haber dos Repository distintos 
		- utilizar 'qualifiers' para clasificarlos y así poder seleccionarlo de manera automática
	*/
	@Autowired
	@Qualifier("remote")
	private StringService srvRem;
	
	@Autowired
	@Qualifier("local")
	private StringService srvLoc;
	
	@Autowired
	private Rango rango;
	
	//si no se encuentra el valor, devuelve "Sin valor"
	@Value("${mi.valor:(Sin valor)}")
	private String config;
	
	@Autowired
	UnaTonteria unaTonteria;
	
	
	@Autowired
	JdbcTemplate jdbcTemplate;

	@Data
	@AllArgsConstructor
	class Actor {
		private int id;
		private String first_name, last_name;
	}
	class ActorRowMapper implements RowMapper<Actor> {
	      @Override
	      public Actor mapRow(ResultSet rs, int rowNum) throws SQLException {
	            return new Actor(rs.getInt("actor_id"), rs.getString("last_name"), rs.getString("first_name"));
	      }
	}
	
	
	@Override
	public void run(String... args) throws Exception {
		System.out.println("Aplicación arrancada");
		//var lst = jdbcTemplate.query("""
		//		SELECT actor_id, first_name, last_name from actor
		//		""", new ActorRowMapper());
		//lst.forEach(System.out::println);
		
		//example of 'complex' query to find all movie titles from actresses named Penelope
		jdbcTemplate.queryForList("""
				SELECT title FROM film f
				WHERE f.film_id IN
				(SELECT film_id FROM actor a INNER JOIN film_actor fa WHERE a.first_name='PENELOPE' AND a.actor_id=fa.actor_id)
				""", String.class).forEach(System.out::println);
		
	}

}
