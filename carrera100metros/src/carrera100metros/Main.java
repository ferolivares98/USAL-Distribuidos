/* 
 * Fernando Olivares Naranjo. 54126671N
 * En este ejercicio me decanté por utilizar wait y notify para que todos los procesos
 * esperasen el "pistoletazo de salida". Gracias a los segundos de Preparados, Listos, Ya... 
 * no resulta problemático. En un primer momento realicé el ejercicio con semáforos y un
 * HashMap concurrente, pero me pareció más sencillo con el wait. Por último, se muestran los
 * resultados con un método sincronizado.
 */
package carrera100metros;

public class Main {
	
	public static void main (String[] args) {
		System.out.println("Ejercicio de la carrera de 100 metros (8 atletas).");
		Carrera c = new Carrera();
		c.inicio();
	}
}
