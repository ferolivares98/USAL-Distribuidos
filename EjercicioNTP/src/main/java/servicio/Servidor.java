package servicio;

import javax.inject.Singleton;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;


@Singleton
@Path("servidor")
public class Servidor {
	
	private final static int MIN = 2000;
	private final static int MAX = 2400;
	
	@GET
	@Produces(MediaType.TEXT_PLAIN)
	@Path("pedirTiempo")
	public String pedirTiempo() {
		long t1 = System.currentTimeMillis();
		try {
			Thread.sleep((long) ((Math.random() * (MAX - MIN)) + MIN));
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
		long t2 = System.currentTimeMillis();
		return String.format("%d/%d", t1, t2);
	}
	
}
