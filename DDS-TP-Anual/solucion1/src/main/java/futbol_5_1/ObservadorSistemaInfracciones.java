package futbol_5_1;

import java.time.LocalDate;
import java.util.ArrayList;

public class ObservadorSistemaInfracciones implements Observador {
	ArrayList<Infraccion> infracciones = new ArrayList<Infraccion>();
	
	public void notificarInscripcionAmigos(ArrayList<Persona> amigos){}
	
	public void notificarPartidoLleno(Partido unPartido){}
	
	public void notificarCuposLibres(Partido unPartido){}
	
	public void generarInfraccion(Jugador unJugador, String motivo){
		Infraccion nuevaInfraccion = new Infraccion();
		nuevaInfraccion.setJugador(unJugador);
		nuevaInfraccion.setFecha(LocalDate.now());
		nuevaInfraccion.setMotivo(motivo);
		infracciones.add(nuevaInfraccion);
		unJugador.agregarInfraccion(nuevaInfraccion);
	}
	
	public void mocionRechazada (Mocion unaMocion, String motivo){}
}