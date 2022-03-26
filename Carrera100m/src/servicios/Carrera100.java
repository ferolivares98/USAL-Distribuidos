package servicios;

import java.util.Map;
import java.util.concurrent.BrokenBarrierException;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.CyclicBarrier;
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
	
	/*Semaphore semPreparado;
	Semaphore semListo;*/
	Semaphore semMain;
	
	//CyclicBarrier (FORO)
	private CyclicBarrier bl;
	private CyclicBarrier bp;
	
	public Carrera100() {
		this.nAtletasInscritosPreparado = 0;
		this.nAtletasInscritosListo = 0;
		this.tiempoInicioCarrera = 0;
		this.tiemposDeLlegada = new ConcurrentHashMap<Integer, Long>();
		/*this.semPreparado = new Semaphore(0);
		this.semListo = new Semaphore(0);*/
		this.semMain = new Semaphore(0);
		bp = new CyclicBarrier(MAXATLETAS);
		bl = new CyclicBarrier(MAXATLETAS);
	}
	
	@POST
	@Path("reinicio")
	public void reinicio() {
		this.nAtletasInscritosPreparado = 0;
		this.nAtletasInscritosListo = 0;
		this.tiempoInicioCarrera = 0;
		this.tiemposDeLlegada.clear();
		/*this.semPreparado = new Semaphore(0);
		this.semListo = new Semaphore(0);*/
		this.semMain = new Semaphore(0);
		bp = new CyclicBarrier(MAXATLETAS);
		bl = new CyclicBarrier(MAXATLETAS);
	}
	
	@POST
	@Path("preparado")
	public void preparado() {
		nAtletasInscritosPreparado++;
		try {
			this.bp.await();
		} catch (InterruptedException e) {
			e.printStackTrace();
		} catch (BrokenBarrierException e) {
			e.printStackTrace();
		}
	}
	
	@POST
	@Path("listo")
	public void listo() {
		nAtletasInscritosListo++;
		try {
			this.bl.await();
		} catch (InterruptedException e) {
			e.printStackTrace();
		} catch (BrokenBarrierException e) {
			e.printStackTrace();
		}
		tiempoInicioCarrera = System.currentTimeMillis();
	}
	
	/*//Prueba de REST
	@GET
	@Produces(MediaType.TEXT_PLAIN)
	@Path("hola")
	public String hola() {
		return "hola " + nAtletasInscritosPreparado;
	}*/
	
	@GET
	@Produces(MediaType.TEXT_PLAIN)
	@Path("llegada")
	public String llegada(@DefaultValue("0") @QueryParam(value="dorsal") int dorsal) {
		Long llegada = System.currentTimeMillis();
		tiemposDeLlegada.put((Integer)dorsal, llegada);
		semMain.release(1);
		return llegada.toString();
	}
	
	@GET
	@Produces(MediaType.TEXT_PLAIN)
	@Path("resultados")
	public String resultados() {
		try {
			semMain.acquire(MAXATLETAS);
			semMain.release(MAXATLETAS); //Bloqueo del main esperando atletas.
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
		StringBuilder sb = new StringBuilder();
		sb.append("   Dorsal   |   Tiempo   \n");
		for(int d : tiemposDeLlegada.keySet()) {
			float tiempoTotal = ((float)(tiemposDeLlegada.get(d).longValue() - tiempoInicioCarrera)/1000); //Este tiempo igual para todos.
			sb.append(String.format("   %d        |   %f\n", d, tiempoTotal));
		}
		return sb.toString();
	}
}
