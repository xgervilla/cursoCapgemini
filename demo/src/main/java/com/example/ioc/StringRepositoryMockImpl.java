package com.example.ioc;

import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Primary;
import org.springframework.stereotype.Repository;

@Repository
//@Primary
public class StringRepositoryMockImpl implements StringRepository {

	@Override
	public String load() {
		return "Soy el doble de prueba de StringRepositoryImpl";
	}
	
	@Override
	public void save(String item) {
		System.out.println("No guardo el item " + item);
	}
}
