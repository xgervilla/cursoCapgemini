package com.example.ioc;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.context.annotation.Profile;

/*
 * Selección de varios constructores
*/
@Configuration
public class MyConfig {
	
	@Bean
	@Profile("default")
	UnaTonteria unaTonteria() {
		return new UnaTonteria() {
			@Override
			public String dimeAlgo() {
				return "El default dice una tonteria";
			}
		};
	}
		
	@Bean
	@Profile("test")
	UnaTonteria unaTonteriaTest() {
		return new UnaTonteria() {
			@Override
			public String dimeAlgo() {
				return "El test dice una tonteria";
			}
		};
	}
	
	@Bean
	@Profile("prod")
	UnaTonteria unaTonteriaProd() {
		return new UnaTonteria() {
			@Override
			public String dimeAlgo() {
				return "El prod dice una tonteria";
			}
		};
	}
	
	/* //Genera error porque la clase Rango está inicializada mediante application.properties y tiene prioridad sobre esta inicialización
	@Bean
	@Primary
	Rango unRango() {
		return Rango.builder().min(15).max(30).build();
	};*/
}
