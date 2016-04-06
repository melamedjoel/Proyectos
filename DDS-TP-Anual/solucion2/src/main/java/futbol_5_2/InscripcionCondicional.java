package futbol_5_2;

import java.util.function.Predicate;
import futbol_5_2.exceptions.CriterioNoDefinidoException;

public class InscripcionCondicional extends Inscripcion {
	private Predicate<Partido> _condicion;
	private Integer _prioridad = 3;
	
	public Predicate<Partido> getCondicion()
	{
		return _condicion;
	}
	
	public void setCondicion(Predicate<Partido> unaCondicion)
	{
		_condicion = unaCondicion;
	}
	
	
	public static InscripcionCondicional crear(Jugador unJugador)
	{
		InscripcionCondicional unaInscripcion = new InscripcionCondicional();
		unaInscripcion.setJugador(unJugador);
		return unaInscripcion;
	}

	@Override
	public Boolean permitirInscripcion(Partido unPartido)
	{
		//Si queda espacio en la coleccion lo agrego sin borrar nada
		if (unPartido.cantidadDeInscripciones() < 10)
		{
			if (this.getCondicion() == null)
				throw new CriterioNoDefinidoException("No se puede inscribir al jugador ya que no esta definida la condición");
				
			if (this.getCondicion().test(unPartido))
			{
				//Se cumplio la condicion entonces lo agrego a la lista
				unPartido.agregarInscripcion(this);
				return true;
			}

			//No se cumple la condicion, no lo puedo agregar
			return false;
		}

		//No queda mas lugar en la coleccion de inscripciones
		return false;
	}
}
