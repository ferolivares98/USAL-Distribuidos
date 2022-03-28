package cliente;

import javax.ws.rs.client.WebTarget;
import javax.ws.rs.core.MediaType;

public class Atleta extends Thread{
	
	private int dorsal;
	private WebTarget target;
	private final static int MIN = 9560;
	private final static int MAX = 11760;

	public Atleta(WebTarget target) {
		super();
		this.dorsal = 0;
		this.target = target;
	}
	
	@Override
	public void run() {
		try {
			String d = target.path("carrera100/preparado").request(MediaType.TEXT_PLAIN).get(String.class);
			dorsal = Integer.parseInt(d);
			target.path("carrera100/listo").request().post(null);
			Thread.sleep((long) ((Math.random() * (MAX - MIN)) + MIN));
			target.path("carrera100/llegada").queryParam("dorsal", dorsal).request(MediaType.TEXT_PLAIN).get(String.class);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
	}
}
