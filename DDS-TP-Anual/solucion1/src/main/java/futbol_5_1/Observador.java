package futbol_5_1;

import java.util.ArrayList;

public interface Observador {
	
	public void notificarInscripcionAmigos(ArrayList<Persona> amigos);
	
	public void notificarPartidoLleno(Partido unPartido);
	
	public void notificarCuposLibres(Partido unPartido);
	
	public void generarInfraccion(Jugador unJugador, String motivo);
	
	public void mocionRechazada (Mocion unaMocion, String motivo);
}
