package futbol_5_1;

import java.util.*;

public class Notificador {
	
	private ArrayList<Observador> observadores = new ArrayList<Observador>();
	
	public void registrarObservador(Observador observador){
		observadores.add(observador);
	}
	
	public ArrayList<Observador> getObservadores(){
		return observadores;
	}
	
	public void notificarPartidoLleno(Partido unPartido){
		for(Observador observador : observadores){
			observador.notificarPartidoLleno(unPartido);
		}
	}
	
	public void notificarCuposLibres(Partido unPartido){
		for(Observador observador : observadores){
			observador.notificarCuposLibres(unPartido);
		}
	}
	
	public void notificarInscripcionAmigos(ArrayList<Persona> amigos){
		for(Observador observador : observadores){
			observador.notificarInscripcionAmigos(amigos);
		}
	}
	
	public void generarInfraccion(Jugador jugador, String motivo) {
		for(Observador observador : observadores){
			observador.generarInfraccion(jugador, motivo);
		}
	}
	
	public void mocionRechazada(Mocion unaMocion, String motivo) {
		for(Observador observador : observadores){
			observador.mocionRechazada(unaMocion, motivo);
		}
	}
	
}
