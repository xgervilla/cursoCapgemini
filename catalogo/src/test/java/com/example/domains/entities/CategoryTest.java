package com.example.domains.entities;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;

/*
 * Entidad Category:
 	*  categoryId		--> siempre 0, dejamos a la BD la responsabilidad de asignar el ID que corresponda 
 	*  name				--> nombre de la categoría: hasta un máximo de 25 carácteres, sin límite inferior PERO no puede ser blank ni nulo
 	*  last_update		--> "intocable", viene dado por la BD así que no se debe modificar 
 	*  filmCategories	--> lista de peliculas que tienen la catgoría: sin restricciones en la construcción del objeto
*/

class CategoryTest {

	@BeforeEach
	void setUp() throws Exception {
	}

	@Test
	void testIsValid() {
		var item = new Category(0,"Comedy");
		assertTrue(item.isValid());
	}
	
	
	@DisplayName("name invalid")
	@ParameterizedTest(name="Name: {0}, Error: {1}")
	@CsvSource({"'','ERRORES: name: must not be blank.'",
		"'     ','ERRORES: name: must not be blank.'",
		"12345678901234567890123456789012345678901234567890,'ERRORES: name: size must be between 0 and 25.'"})
	void testNameIsInvalid(String categoryName, String error) {
		var category = new Category(0, categoryName);
		assertTrue(category.isInvalid());
		assertEquals(error, category.getErrorsMessage());
	}

}
