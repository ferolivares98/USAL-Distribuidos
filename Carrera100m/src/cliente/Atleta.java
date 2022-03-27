package cliente;

import javax.ws.rs.client.WebTarget;
import javax.ws.rs.core.MediaType;

public class Atleta extends Thread{
	
	private int dorsal;
	private WebTarget target;
	private final static int MIN = 9560;
	private final static int MAX = 11760;

	public Atleta(int dorsal, WebTarget target) {
		super();
		this.dorsal = dorsal;
		this.target = target;
	}
	
	@Override
	public void run() {
		try {
			target.path("carrera100/preparado").request().post(null);
			target.path("carrera100/listo").request().post(null);
			Thread.sleep((long) ((Math.random() * (MAX - MIN)) + MIN));
			target.path("carrera100/llegada").queryParam("dorsal", dorsal).request(MediaType.TEXT_PLAIN).get(String.class);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
	}
}
