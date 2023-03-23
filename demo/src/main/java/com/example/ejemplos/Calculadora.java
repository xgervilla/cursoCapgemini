//Clase sobre la que hacer los tests
package com.example.ejemplos;

import java.math.BigDecimal;
import java.math.RoundingMode;

public class Calculadora {
	//suma de dos numeros enteros
	public double suma(double a, double b) {
		//Adaptación de la suma a BigDecimal para evitar errores de precisión
		BigDecimal rsltBigDecimal = BigDecimal.valueOf(a + b);
		return rsltBigDecimal.setScale(15, RoundingMode.HALF_DOWN).doubleValue();
	}

	//division de dos numeros reales (a / b)
	public double divide(double a, double b) {
		if (b== 0){
			throw new ArithmeticException("Tried to divide by Zero");
		}
		return a / b;
	}
	
	//division de dos enteros (a / b) --> si el resultado da decimal causará un error
	public double divide(int a, int b) {
		return a / b;
	}
}