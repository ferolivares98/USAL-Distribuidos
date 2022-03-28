package cliente;

import java.net.URI;

import javax.ws.rs.client.Client;
import javax.ws.rs.client.ClientBuilder;
import javax.ws.rs.client.WebTarget;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.UriBuilder;

import servicios.Carrera100;

public class MainCarrera {
	
	final static int MAXATLETAS = 3;
	
	public static void main(String[] args) {
		
		Client client = ClientBuilder.newClient();
		URI uri = UriBuilder.fromUri("http://localhost:8080/Carrera100m/rest").build();
		WebTarget target = client.target(uri);
		
		/* args[0] = Ordenador servidor+clientes*/
		
		boolean isServ = Boolean.parseBoolean(args[0]);
		
		System.out.println(isServ);
		
		if (isServ) {
			Carrera100 c = new Carrera100();
			target.path("carrera100/reinicio").request().post(null);
		}
		Atleta[] at = new Atleta[MAXATLETAS];
		for(int i = 0; i < MAXATLETAS; i++) {
			at[i] = new Atleta(target);
		}
		
		for(Atleta a : at) {
			a.start();
		}
		
		if(isServ) {
			String res = target.path("carrera100/resultados").request(MediaType.TEXT_PLAIN).get(String.class);
			System.out.println(res);
		}
	}
}
