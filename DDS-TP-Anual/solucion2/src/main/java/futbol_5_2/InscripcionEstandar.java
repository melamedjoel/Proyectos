package futbol_5_2;

public class InscripcionEstandar extends Inscripcion{
	private Integer _prioridad = 1;
	
	public static InscripcionEstandar crear(Jugador unJugador)
	{
		InscripcionEstandar unaInscripcion = new InscripcionEstandar();
		unaInscripcion.setJugador(unJugador);
		return unaInscripcion;
	}
}
