package com.example.ioc;

import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Primary;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Repository;
import com.example.exceptions.InvalidDataException;

@Primary
@Repository
@Scope("prototype")
public class StringRepositoryImpl implements StringRepository {
	private String ultimo = "";

	@Override
	public String load() {
		return "Soy el StringRepositoryImpl";
	}
	
	@Override
	public void save(String item) throws InvalidDataException {
		if (item == "") {
			throw new InvalidDataException("Data can't be empty");
		}
		System.out.println("Anterior: " + ultimo);
		System.out.println("Guardo el item " + item);
		this.ultimo = item;
	}
}
