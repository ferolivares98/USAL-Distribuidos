package servicios;

import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.Semaphore;

import javax.inject.Singleton;
import javax.ws.rs.DefaultValue;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.MediaType;

@Singleton
@Path("carrera100")
public class Carrera100 {

	final static int MAXATLETAS = 4;
	int nAtletasInscritosPreparado;
	int nAtletasInscritosListo;
	long tiempoInicioCarrera;
	private Map<Integer, Long> tiemposDeLlegada;
	
	Semaphore semPreparado;
	Semaphore semListo;
	
	public Carrera100() {
		this.nAtletasInscritosPreparado = 0;
		this.nAtletasInscritosListo = 0;
		this.tiempoInicioCarrera = 0;
		this.tiemposDeLlegada = new HashMap<Integer, Long>();
		this.semPreparado = new Semaphore(0);
		this.semListo = new Semaphore(0);
	}
	
	@POST
	@Path("reinicio")
	public void reinicio() {
		this.nAtletasInscritosPreparado = 0;
		this.nAtletasInscritosListo = 0;
		this.tiempoInicioCarrera = 0;
		this.tiemposDeLlegada.clear();
		this.semPreparado = new Semaphore(0);
		this.semListo = new Semaphore(0);
	}
	
	@POST
	@Path("preparado")
	public void preparado() {
		nAtletasInscritosPreparado++;
		if (nAtletasInscritosPreparado == MAXATLETAS) {
			semPreparado.release(MAXATLETAS);
		} else {
			try {
				semPreparado.acquire();
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		}
	}
	
	@POST
	@Path("listo")
	public void listo() {
		nAtletasInscritosListo++;
		if (nAtletasInscritosListo == MAXATLETAS) {
			semListo.release(MAXATLETAS);
		} else {
			try {
				semListo.acquire();
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		}
	}
	
	@GET
	@Produces(MediaType.TEXT_PLAIN)
	@Path("hola")
	public String llegada() {
		return "hola\n";
	}
	
	@GET
	@Produces(MediaType.TEXT_PLAIN)
	@Path("llegada")
	public String llegada(@DefaultValue("0") @QueryParam(value="dorsal") int dorsal) {
		Long llegada = System.currentTimeMillis();
		tiemposDeLlegada.put(dorsal, llegada);
		return llegada.toString();
	}
	
	@GET
	@Produces(MediaType.TEXT_PLAIN)
	@Path("resultados")
	public String resultados() {
		StringBuilder sb = new StringBuilder();
		sb.append("   Dorsal   |   Tiempo   \n");
		for(int d : tiemposDeLlegada.keySet()) {
			long tiempoTotal = tiemposDeLlegada.get(d).longValue() - tiempoInicioCarrera; //Este tiempo igual para todos.
			sb.append(String.format("   %d   |   %lf", d, tiempoTotal));
		}
		return sb.toString();
	}
}
