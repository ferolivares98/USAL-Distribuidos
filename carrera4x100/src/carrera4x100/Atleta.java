package carrera4x100;

import java.util.concurrent.Semaphore;

public class Atleta extends Thread{

	private int dorsal;
	private Semaphore testigo;
	
	public Atleta(int dorsal, Semaphore testigo) {
		this.dorsal = dorsal;
		this.testigo = testigo;
	}
	
	@Override
	public void run() {
		try {
			testigo.acquire();
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
		realizarTramo();
		testigo.release();
		System.out.println("Llegada del dorsal " + (dorsal+1) + ": " + System.currentTimeMillis() + ".");
	}
	
	private void realizarTramo(){
		int max = 11000;
		int min = 9000;
		try {
			Thread.sleep((long) ((Math.random() * (max - min)) + min));
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
	}
}
