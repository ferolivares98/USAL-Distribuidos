/* 
 * Fernando Olivares Naranjo. 54126671N
 * En este ejercicio me decant� por utilizar wait y notify para que todos los procesos
 * esperasen el "pistoletazo de salida". Gracias a los segundos de Preparados, Listos, Ya... 
 * no resulta problem�tico. En un primer momento realic� el ejercicio con sem�foros y un
 * HashMap concurrente, pero me pareci� m�s sencillo con el wait. Por �ltimo, se muestran los
 * resultados con un m�todo sincronizado.
 */
package carrera100metros;

public class Main {
	
	public static void main (String[] args) {
		System.out.println("Ejercicio de la carrera de 100 metros (8 atletas).");
		Carrera c = new Carrera();
		c.inicio();
	}
}
