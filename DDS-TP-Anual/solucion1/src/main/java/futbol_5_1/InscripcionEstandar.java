package futbol_5_1;

import javax.persistence.*;

@DiscriminatorValue("Estandar") 
public class InscripcionEstandar extends Inscripcion{
	
	public InscripcionEstandar(){}
	
	public InscripcionEstandar(Jugador unJugador)
	{
		this.setJugador(unJugador);
		this.setPrioridad(1);
	}
	
}
