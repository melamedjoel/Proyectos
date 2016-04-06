package futbol_5_2;

import java.util.ArrayList;

public abstract class Inscripcion {
	private Jugador _jugador;
	
	private Integer _prioridad;
	
	public Jugador getJugador()
	{
		return _jugador;
	}
	
	public void setJugador(Jugador unJugador)
	{
		_jugador = unJugador;
	}
	
	public Integer getPrioridad()
	{
		return _prioridad;
	}

	public Boolean permitirInscripcion(Partido unPartido)
	{
		//Si queda espacio en la coleccion lo agrego sin borrar nada
		if (unPartido.cantidadDeInscripciones() < 10)
		{
			unPartido.agregarInscripcion(this);
			return true;
		}

		//No queda espacio en la coleccion, tengo que reemplazar una inscripcion solidaria/condicional
		ArrayList<Inscripcion> inscripcionesReemplazar = unPartido.obtenerInscripcionesAReemplazar(this);

		if (!inscripcionesReemplazar.isEmpty()) 
		{
			Inscripcion inscripcionReemplazar = inscripcionesReemplazar.get(0);
			unPartido.removerInscripcion(inscripcionReemplazar);
			unPartido.agregarInscripcion(this);
			return true;
		}

		//Por las dudas que la lista este vacia, nunca deberia pasar
		return false;
	}
	
}
