package futbol_5_2;

public class InscripcionSolidaria extends Inscripcion {
	private Integer _prioridad = 2;
	
	public static InscripcionSolidaria crear(Jugador unJugador)
	{
		InscripcionSolidaria unaInscripcion = new InscripcionSolidaria();
		unaInscripcion.setJugador(unJugador);
		return unaInscripcion;
	}

}
