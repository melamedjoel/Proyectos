package futbol_5_1;

import javax.persistence.*;

@DiscriminatorValue("Solidaria") 
public class InscripcionSolidaria extends Inscripcion {
	
	public InscripcionSolidaria(){}
	
	public InscripcionSolidaria(Jugador unJugador)
	{
		this.setJugador(unJugador);
		this.setPrioridad(2);
	}

}
