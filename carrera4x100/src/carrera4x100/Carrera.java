package carrera4x100;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.Semaphore;

public class Carrera{
	
	private static Semaphore testigo;
	List<Atleta> a = new ArrayList<Atleta>();
	
	public Carrera() {
		testigo = new Semaphore(1);
		for (int i = 0; i < 4; i++) {
			a.add(new Atleta(i, testigo));
		}
	}
	
	public void inicio() {
		for(Atleta atleta : a) {
			atleta.start();
		}
	}
}
