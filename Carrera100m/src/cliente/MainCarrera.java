package cliente;

import java.net.URI;

import javax.ws.rs.client.Client;
import javax.ws.rs.client.ClientBuilder;
import javax.ws.rs.client.WebTarget;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.UriBuilder;

import servicios.Atleta;
import servicios.Carrera100;

public class MainCarrera {
	
	final static int MAXATLETAS = 4;
	
	public static void main(String[] args) {
		Client client = ClientBuilder.newClient();
		URI uri = UriBuilder.fromUri("http://localhost:8080/Carrera100m/carrera100").build();
		WebTarget target = client.target(uri);
		
		Carrera100 c = new Carrera100();
		Atleta[] at = new Atleta[MAXATLETAS];
		for(int i = 0; i < MAXATLETAS; i++) {
			at[i] = new Atleta(i+1, c, target);
		}
		
		c.reinicio();
		for(Atleta a : at) {
			a.start();
		}
		//String res = c.resultados();
		try {
			Thread.sleep(20000);
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		String res = target.path("resultados").request(MediaType.TEXT_PLAIN).get(String.class);
		System.out.println(res);
	}
}
