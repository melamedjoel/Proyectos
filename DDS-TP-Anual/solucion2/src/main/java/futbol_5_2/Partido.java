package futbol_5_2;

import java.util.ArrayList;
import java.util.Date;
import java.util.stream.Collectors;

public class Partido 
{
	private ArrayList<Inscripcion> _inscripciones = new ArrayList<Inscripcion>();
	
	private Date _fechayhora;
	
	private String _lugar;
	

	public ArrayList<Inscripcion> getInscripciones() 
	{
		return _inscripciones;
	}
	
	public void agregarInscripcion(Inscripcion unaInscripcion){
		_inscripciones.add(unaInscripcion);
		unaInscripcion.getJugador().notificarInscripcionAmigos();
		if(this.llenoDeConfirmados()){
			//enviar mail al administrador avisando que se llenó el partido
		}
	}
	
	public Date getFechaYHora()
	{
		return _fechayhora;
	}

	public void setFechaYHora(Date unaFechaYHora)
	{
		_fechayhora = unaFechaYHora;
	}
	
	public String getLugar()
	{
		return _lugar;
	}
	
	public void setLugar(String unLugar)
	{
		_lugar = unLugar;
	}
	
	public Boolean llenoDeConfirmados()
	{
		ArrayList<Inscripcion> confirmados = (ArrayList<Inscripcion>) this.getInscripciones().stream()
											.filter(unaInscripcion -> unaInscripcion.getPrioridad() == 1)
											.collect(Collectors.toList());
		
		return confirmados.size()==10;		
	}
	
	public static Partido crear(String unLugar, Date unaFechaYHora)
	{
		Partido unPartido = new Partido();
		unPartido.setFechaYHora(unaFechaYHora);
		unPartido.setLugar(unLugar);
		return unPartido;
	}
	
	public int cantidadDeInscripciones(){
		return this.getInscripciones().size();
	}
	
	public void removerInscripcion(Inscripcion unaInscripcion){
		Boolean estabaLleno = this.llenoDeConfirmados();
		this.getInscripciones().remove(unaInscripcion);
		Boolean estaLleno = this.llenoDeConfirmados();
		if(estabaLleno && !estaLleno){
			//enviar mail al administrador avisando que se liberó un cupo
		}
	}
	
	public Boolean removerJugador(Jugador unJugador){		
		ArrayList<Inscripcion> listaComprobacionDeInscripcionDelJugador = (ArrayList<Inscripcion>)this.getInscripciones().stream()
				  .filter(inscripcion -> unJugador == inscripcion.getJugador())
				  .collect(Collectors.toList());
		
		if(listaComprobacionDeInscripcionDelJugador.size() == 0)
			return false;
		
		Inscripcion inscripcionARemover = listaComprobacionDeInscripcionDelJugador.get(0);
		
		this.removerInscripcion(inscripcionARemover);
		return true;
	}
	
	
	public ArrayList<Inscripcion> obtenerInscripcionesAReemplazar(Inscripcion unaInscripcion)
	{
		//No queda espacio en la coleccion, tengo que reemplazar una inscripcion condicional/solidaria
		ArrayList<Inscripcion> inscripcionesAReemplazar = (ArrayList<Inscripcion>) this.getInscripciones().stream()
														  .filter(inscripcion -> inscripcion.getPrioridad() > unaInscripcion.getPrioridad())
														  .collect(Collectors.toList());
		
		return inscripcionesAReemplazar;
	}
}
