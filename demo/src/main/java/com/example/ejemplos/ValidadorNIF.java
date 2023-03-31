package com.example.ejemplos;

/* NIF Válido (personas físicas)
 		* 8 números + letra
 		* 8 números (la letra se puede obtener de manera automática)
 * NIF Inválido (personas físicas)
 	* Personas físicas:
 		* <8 números --> no se considera el relleno con 0s
 		* >8 números
 		* Combinación de letras y números --> 123J56LA
 		* 8 números + letra PERO que la letra no sea la correcta
 		* 00000000T --> caso "base" no permitido por la legislación
 * Algoritmo validador:
 	* 
 * */

public class ValidadorNIF {

    private static final String letrasNIF = "TRWAGMYFPDXBNJZSQVHLCKE";

    public ValidadorNIF() {}
    
    private static char calculaLetra(String aux) {
        return letrasNIF.charAt(Integer.parseInt(aux) % 23);
    }

    public static boolean isValido(String nif) {
    	
    	//Si el NIF es nulo devolvemos cierto
    	if (nif == null) return true;
    	//Si la longitud no es válida O es el "caso base" devolvemos falos
    	if (nif.length()<8 || nif.length()>9 || "00000000T".equals(nif)) return false;
    	
    	//Si tiene longitud viable (8 u 9 carácteres) comprobamos que los 8 primeros carácteres sean dígitos
    	String sub8 = nif.substring(0, 8);
    	//regex que da como válido únicamente una cadena de exactamente 8 dígitos
    	if(!sub8.matches("\\d{8}$"))
    		return false;
    	
    	//en caso de tener 8 dígitos Y no tener letra ya se considera un NIF válido
    	if (nif.length()==8) {
    		return true;
    	}
    	
    	//en caso de tener letra se comprueba que coincida con la que corresponde
    	return calculaLetra(sub8) == nif.toUpperCase().charAt(8);
    }
    
    public static boolean isNotValido(String nif) {
    	return !isValido(nif);
    }
}
