package com.example.ioc;

// extiendo el Repository genérico para especificar/centrarme en el tipo String --> preferible tener muchos extends "pequeños" que pocos extends "grandes"
public interface StringRepository extends Repository<String>{

}
