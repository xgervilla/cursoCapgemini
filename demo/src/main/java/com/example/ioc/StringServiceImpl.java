package com.example.ioc;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

@Service
@Qualifier("local")
public class StringServiceImpl implements StringService {
	@Autowired
	private StringRepository dao;
	
	public StringServiceImpl(StringRepository dao) {
		this.dao = dao;
	}
	@Override
	public String get(Integer id) {
		return dao.load() + " en local";
	}

	@Override
	public void add(String item) {
		dao.save(item);
	}

	@Override
	public void modify(String item) {
		dao.save(item);
	}

	//aunque no se vaya a borrar, mejor implementarlo con una función "no útil" --> si no hay nada que se vaya a borrar mejor crear una nueva interface que no tenga el método remove
	@Override
	public void remove(Integer id) {
		dao.save(id.toString());
	}

}
