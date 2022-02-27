package carrera100metros;

import java.util.ArrayList;
import java.util.List;

public class Carrera {
	//private static Semaphore inicio;
	private List<Atleta> a = new ArrayList<Atleta>();
	//private ConcurrentHashMap<Integer, Long> listaTiempos = new ConcurrentHashMap<>();
	
	public Carrera() {
		//inicio = new Semaphore(1);
		for (int i = 0; i < 8; i++) {
			a.add(new Atleta(i, this));
		}
	}
	
	public void inicio() {
		try {
			for(Atleta atleta : a) {
				atleta.start();
			}
			System.out.println("Preparados...");
			Thread.sleep(1000);
			System.out.println("Listos...");
			Thread.sleep(1000);
			System.out.println("Ya!");
			Thread.sleep(1000);
			synchronized (this) {
				this.notifyAll();
			}
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
	}

	public synchronized void notificarLlegada(int dorsal) {
		//listaTiempos.put(dorsal, System.currentTimeMillis());
		System.out.println("Llegada del dorsal " + (dorsal+1) + ": " + System.currentTimeMillis() + ".");
	}
}
