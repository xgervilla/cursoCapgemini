//Clase sobre la que hacer los tests
package com.example.ejemplos;

public class Calculadora {
	//suma de dos numeros enteros
	public double suma(double a, double b) {
		return a + b;
	}

	//division de dos numeros reales (a / b)
	public double divide(double a, double b) {
		return a / b;
	}
	
	//division de dos enteros (a / b) --> si el resultado da decimal causará un error
	public double divide(int a, int b) {
		return a / b;
	}
}