package servicios;

import javax.ws.rs.client.WebTarget;
import javax.ws.rs.core.MediaType;

public class Atleta extends Thread{
	
	private int dorsal;
	private Carrera100 c;
	private WebTarget target;
	private final static int MIN = 9560;
	private final static int MAX = 11760;

	public Atleta(int dorsal, Carrera100 c, WebTarget target) {
		this.dorsal = dorsal;
		this.c = c;
		this.target = target;
	}
	
	@Override
	public void run() {
		try {
			//c.preparado();
			target.path("preparado").request().post(null);
			//c.listo();
			target.path("listo").request().post(null);
			Thread.sleep((long) ((Math.random() * (MAX - MIN)) + MIN));
			//c.llegada(dorsal);
			target.path("llegada").queryParam("dorsal", dorsal).request(MediaType.TEXT_PLAIN).get(String.class);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
	}
}
