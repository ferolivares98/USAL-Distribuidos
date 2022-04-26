package cli;

import java.util.ArrayList;

import javax.ws.rs.client.ClientBuilder;
import javax.ws.rs.client.WebTarget;
import javax.ws.rs.core.UriBuilder;

import servicio.Servidor;

public class MainNTP {
	public static void main(String[] args) {
		
		Servidor s = new Servidor();
		ArrayList<WebTarget> listServidores = new ArrayList<WebTarget>();
		
		/* args[0] = Ordenador servidor+cliente. true en los argumentos si = cliente*/
		boolean isCliente = Boolean.parseBoolean(args[0]);
		//System.out.println(isCliente);
		
		if (isCliente) {
			dirServidores(listServidores);
			//System.out.print(listServidores);
			Cliente c = new Cliente(listServidores);
			c.start();
		}
		
		
	}
	
	private static void dirServidores(ArrayList<WebTarget> listServidores) {
		WebTarget propio = ClientBuilder.newClient().target(UriBuilder.fromUri("http://localhost:8080/EjercicioNTP/rest").build());
		WebTarget s1 = ClientBuilder.newClient().target(UriBuilder.fromUri("http://192.168.8.100:8080/EjercicioNTP/rest").build());
		WebTarget s2 = ClientBuilder.newClient().target(UriBuilder.fromUri("http://192.168.8.102:8080/EjercicioNTP/rest").build());
		listServidores.add(propio);
		listServidores.add(s1);
		listServidores.add(s2);
	}
}
