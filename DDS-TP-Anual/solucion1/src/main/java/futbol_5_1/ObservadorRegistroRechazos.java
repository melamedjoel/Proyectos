package futbol_5_1;

import java.util.ArrayList;
import java.util.Date;

public class ObservadorRegistroRechazos {
	ArrayList<Rechazo> rechazos = new ArrayList<Rechazo>();
	
	public void notificarInscripcionAmigos(ArrayList<Jugador> amigos){}
	
	public void notificarPartidoLleno(Partido unPartido){}
	
	public void notificarCuposLibres(Partido unPartido){}
	
	public void generarInfraccion(Jugador unJugador, String motivo){
	}
	
	public void mocionRechazada (Mocion unaMocion, String motivo){
		Rechazo nuevoRechazo = new Rechazo();
		nuevoRechazo.setMocion(unaMocion);
		nuevoRechazo.setFecha(new Date());
		nuevoRechazo.setMotivo(motivo);
		rechazos.add(nuevoRechazo);
	}
}
