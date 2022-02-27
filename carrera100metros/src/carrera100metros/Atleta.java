package carrera100metros;

public class Atleta extends Thread{

	private int dorsal;
	//private Semaphore inicio;
	Carrera c;
	
	public Atleta(int dorsal, Carrera c) {
		this.dorsal = dorsal;
		//this.inicio = inicio;
		this.c = c;
	}
	
	@Override
	public void run() {
		try {
			synchronized (c) {
				c.wait();
			}
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
		realizarCarrera();
		c.notificarLlegada(dorsal);
	}
	
	private void realizarCarrera(){
		//inicio.release();
		int max = 11000;
		int min = 9000;
		try {
			Thread.sleep((long) ((Math.random() * (max - min)) + min));
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
	}
}
