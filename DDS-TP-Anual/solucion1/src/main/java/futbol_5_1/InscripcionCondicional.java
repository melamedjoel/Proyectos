package futbol_5_1;

import java.util.function.Predicate;

import futbol_5_1.exceptions.CriterioNoDefinidoException;

import javax.persistence.*;

@DiscriminatorValue("Condicional") 
public class InscripcionCondicional extends Inscripcion {
	@Transient //le pongo transient porque al ser una expresion lambda, no se puede persistir
	private Predicate<Partido> condicion;
	
	public InscripcionCondicional(){}
	
	public InscripcionCondicional(Jugador unJugador)
	{
		this.setJugador(unJugador);
		this.setPrioridad(3);
	}
	
	public Predicate<Partido> getCondicion()
	{
		return condicion;
	}
	
	public void setCondicion(Predicate<Partido> unaCondicion)
	{
		condicion = unaCondicion;
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
