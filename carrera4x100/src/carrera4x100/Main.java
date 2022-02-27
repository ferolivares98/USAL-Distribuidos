/*
 * Fernando Olivares Naranjo. 54126671N
 * En este primer ejercicio, la sincronización se realiza a través de un semáforo.
 * Los procesos acceden y finalizan de uno en uno, "pasando" el permiso de acceso al
 * semáforo ("paso del testigo").
 */
package carrera4x100;

public class Main {
	
	public static void main (String[] args) {
		System.out.println("Ejercicio de la carrera de 4x100 (4 atletas).");
		Carrera c = new Carrera();
		c.inicio();
	}
}
