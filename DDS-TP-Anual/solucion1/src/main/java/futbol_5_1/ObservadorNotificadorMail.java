package futbol_5_1;
import java.util.ArrayList;

public class ObservadorNotificadorMail implements Observador {
	public void notificarInscripcionAmigos(ArrayList<Persona> amigos){
		//mandar mail a cada miembro de la lista
	}
	
	public void notificarPartidoLleno(Partido unPartido){
		//mandar mail a administrador
	}
	
	public void notificarCuposLibres(Partido unPartido){
		//mandar mail a administrador
	}
	
	public void generarInfraccion(Jugador unJugador, String motivo){}
	
	public void mocionRechazada (Mocion unaMocion, String motivo){}
}
