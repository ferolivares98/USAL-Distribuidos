package cli;

import java.util.ArrayList;
import javax.ws.rs.client.WebTarget;
import javax.ws.rs.core.MediaType;

public class Cliente extends Thread{
	
	ArrayList<WebTarget> ls = new ArrayList<WebTarget>();
	ArrayList<Long> auxListTiempos = new ArrayList<Long>();
	
	public Cliente(ArrayList<WebTarget> listServidores) {
		this.ls = listServidores;
	}
	
	@Override
	public void run() {
		double o, d;
		double mejorO;
		double mejorD;
		
		for(WebTarget target : ls) {
			mejorO = 100000;
			mejorD = 100000;
			for(int i = 0; i < 8; i++) {
				long t0 = System.currentTimeMillis();
				String tiempos = target.path("servidor/pedirTiempo").request(MediaType.TEXT_PLAIN).get(String.class);
				auxListTiempos = separarTiempos(tiempos);
				long t3 = System.currentTimeMillis(); //Se podrIa adelantar a la separaciOn de la cadena de tiempos.
				o = determinarOffset(t0, auxListTiempos.get(0), auxListTiempos.get(1), t3);
				d = determinarDelay(t0, auxListTiempos.get(0), auxListTiempos.get(1), t3);
				if(d < mejorD) {
					mejorD = d;
					mejorO = o;
				}
			}
			String res = imprimirResultado(target, mejorD, mejorO);
			System.out.println(res);
		}
	}
	
	private String imprimirResultado(WebTarget target, double d, double o) {
		StringBuilder sb = new StringBuilder();
		
		String[] dirServidor = target.toString().split(":");
		
		sb.append("Tiempo para: ").append(dirServidor[1]).append("\n");
		//sb.append(String.format("Delay: %10d  |  Offset: %10d", d, o)).append("\n\n");
		sb.append(String.format("Delay: %10f  |  Offset: %10f", d, o)).append("\n\n");
		
		return sb.toString();
	}
	
	private ArrayList<Long> separarTiempos(String tiempos) {
		ArrayList<Long> aux = new ArrayList<Long>();
		String[] tiemposSeparados = tiempos.split("/");
		aux.add(Long.parseLong(tiemposSeparados[0]));
		aux.add(Long.parseLong(tiemposSeparados[1]));
		return aux;
	}
	
	private double determinarDelay(long t0, long t1, long t2, long t3) {
		return ((double)(t1-t0+t3-t2));
	}
	
	private double determinarOffset(long t0, long t1, long t2, long t3) {
		//System.out.printf("%f\n", (double)(t1-t0+t2-t3)/2);
		return ((double)(t1-t0+t2-t3)/2);
	}
}
