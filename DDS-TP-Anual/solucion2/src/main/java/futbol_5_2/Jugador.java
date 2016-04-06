package futbol_5_2;

import java.util.ArrayList;
import java.util.Date;

public class Jugador {
	private String _nombre;
	private ArrayList<Jugador> _amigos = new ArrayList<Jugador>();
	
	ArrayList<Infraccion> _infracciones = new ArrayList<Infraccion>();
	
	public String getNombre()
	{
		return _nombre;
	}
	
	public void setNombre(String unNombre)
	{
		_nombre = unNombre;
	}
	
	public static Jugador crear(String unNombre)
	{
		Jugador unJugador = new Jugador();
		unJugador.setNombre(unNombre);
		return unJugador;
	}
	
	public void agregarAmigo(Jugador amigo){
		_amigos.add(amigo);
	}
	
	public ArrayList<Jugador> getAmigos(){
		return _amigos;
	}
	
	public Boolean inscribirAPartido(Partido unPartido, Inscripcion unaInscripcion)
	{
		if (unPartido.llenoDeConfirmados())
			return false;

		return unaInscripcion.permitirInscripcion(unPartido);
	}
	
	public Boolean darseDeBaja(Partido unPartido, String motivo){
		if(unPartido.removerJugador(this)){
			Infraccion nuevaInfraccion = new Infraccion();
			nuevaInfraccion.setJugador(this);
			nuevaInfraccion.setFecha(new Date());
			nuevaInfraccion.setMotivo(motivo);
			_infracciones.add(nuevaInfraccion);
			return true;
		}
		return false;
	}
	
	
	public Boolean darseDeBaja(Partido unPartido, Inscripcion inscripcionReemplazante){
		if(unPartido.removerJugador(this)){
			inscripcionReemplazante.getJugador().inscribirAPartido(unPartido, inscripcionReemplazante);
			return true;
		}
		return false;
	}

	public void notificarInscripcionAmigos() {
		//crear mail por cada amigo y enviarselo
	}
}
