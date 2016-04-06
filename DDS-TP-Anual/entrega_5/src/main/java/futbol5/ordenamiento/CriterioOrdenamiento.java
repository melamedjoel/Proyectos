package futbol5.ordenamiento;

import futbol5.Jugador;
import futbol5.Partido;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public abstract class CriterioOrdenamiento {
	
	public List<Jugador> ordenar(Partido partido) 
	{
		Collections.sort(partido.getInscriptos(), (jugador1, jugador2) -> calcularValor(jugador1).compareTo(calcularValor(jugador2)));

		Collections.reverse(partido.getInscriptos());
		
		List<Jugador> jugadores = new ArrayList<Jugador>();
		jugadores.addAll(partido.getInscriptos());

		return jugadores;
	}

	public Double calcularValor(Jugador jugador) 
	{
		return 0.0;
	}
}
