package com.example.ioc;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

import com.example.exceptions.InvalidArgumentException;
import com.example.exceptions.InvalidDataException;

@Service
@Qualifier("remote")
public class StringRemoteServiceImpl implements StringService {
	@Autowired
	private StringRepository dao;
	
	public StringRemoteServiceImpl(StringRepository dao) {
		this.dao = dao;
	}
	@Override
	public String get(Integer id) {
		return dao.load() + " en remote";
	}

	@Override
	public void add(String item){
		try {
			dao.save(item);
		} catch (InvalidDataException e) {
			e.printStackTrace();
		}
	}

	@Override
	public void modify(String item) {
		try {
			dao.save(item);
		} catch (InvalidDataException e) {
			e.printStackTrace();
		}
	}

	//aunque no se vaya a borrar, mejor implementarlo con una función "no útil" --> si no hay nada que se vaya a borrar mejor crear una nueva interface que no tenga el método remove
	@Override
	public void remove(Integer id) {
		try {
			dao.save(id.toString());
		} catch (InvalidDataException e) {
			e.printStackTrace();
		}
	}

}