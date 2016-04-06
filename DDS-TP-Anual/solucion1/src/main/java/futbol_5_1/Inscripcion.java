package futbol_5_1;

import java.util.ArrayList;

import javax.persistence.*;

@Entity
@Table(name = "tl_inscripcion")
@Inheritance(strategy=InheritanceType.SINGLE_TABLE) //utilizamos single table ya que creemos que no es necesario
//hacer una tabla por cada una de las clases que heredan de inscripcion, dado que la mayoria repiten los atributos
//salvo la inscripcionCondicional, por lo que nuestro peor escenario podría ser 1 valor en null, lo cual no
//nos perjudica
@DiscriminatorColumn(  
  name="TipoInscripcion",   
  discriminatorType=DiscriminatorType.STRING  
)  
public abstract class Inscripcion {
	
	 @Id  
	 @GeneratedValue(strategy=GenerationType.AUTO)  
	 @Column(name="ID_Inscripcion")  
	 private long id;  
	   
	
	@OneToOne
	@JoinColumn(name = "ID_Jugador")
	private Jugador jugador;
	
	private Integer prioridad;
	
	public Jugador getJugador()
	{
		return jugador;
	}
	
	public void setJugador(Jugador unJugador)
	{
		jugador = unJugador;
	}
	
	public Integer getPrioridad()
	{
		return this.prioridad;
	}
	
	public void setPrioridad(int prioridad)
	{
		this.prioridad = prioridad;
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
	
	public Inscripcion(){
		//Creo un constructor que no hace nada por restricción tecnológica de Hibernate,
		//ya que las clases deben tener un constructor público sin argumentos
	}

	@SuppressWarnings("unused")
	private long getId() {
		return id;
	}

	@SuppressWarnings("unused")
	private void setId(long id) {
		this.id = id;
	}	
}
