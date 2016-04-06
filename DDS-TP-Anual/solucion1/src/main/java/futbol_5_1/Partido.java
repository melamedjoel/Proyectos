package futbol_5_1;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
import javax.persistence.Table;
import javax.persistence.Transient;

@Entity
@Table(name = "tl_partido")
public class Partido{
	
	@Id
	@GeneratedValue(strategy=GenerationType.AUTO)
	@Column(name="ID_Partido")  
	private long id;
	
	private LocalDate fechayhora;
	
	private String lugar;
	
	@Transient
	private Notificador notifier = new Notificador();
	
	private boolean confirmado = false;
	
	@OneToMany(fetch = FetchType.LAZY)
	@JoinColumn(name = "ID_Partido")
	private List<Inscripcion> inscripciones = new ArrayList<Inscripcion>();
	
	@OneToMany(fetch = FetchType.LAZY)
	@JoinColumn(name = "ID_Partido")
	private List<Mocion> mociones = new ArrayList<Mocion>();
	
	@OneToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "ID_Partido")
	private Equipo equipo1 = new Equipo("Equipo1", true);
	
	@OneToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "ID_Partido")
	private Equipo equipo2 = new Equipo("Equipo2", false);
	
	/*
	@OneToMany(fetch = FetchType.LAZY, mappedBy = "equipo")
	private ArrayList<Calificacion> calificaciones = new ArrayList<Calificacion>();
	*/
	
	//Constructor para Hibernate
	public Partido () {
		
	}
	
	public Partido(String unLugar, LocalDate unaFechaYHora)
	{
		this.setFechaYHora(unaFechaYHora);
		this.setLugar(unLugar);

	}
	
	public Partido(String unLugar, LocalDate unaFechaYHora, Notificador notifierEmail){
		this.setFechaYHora(unaFechaYHora);
		this.setLugar(unLugar);
		this.setNotifier(notifierEmail);
	}
	
	public Long getId() {
		return id;
	}
	
	public ArrayList<Inscripcion> getInscripciones() 
	{
		return (ArrayList<Inscripcion>) inscripciones;
	}
	
	public void agregarInscripcion(Inscripcion unaInscripcion)
	{
		if (!confirmado)
		{
			inscripciones.add(unaInscripcion);
			unaInscripcion.getJugador().notificarInscripcionAmigos();
			if(this.llenoDeConfirmados())
			{
				getNotifier().notificarPartidoLleno(this);
			}
		}
	}
	
	public LocalDate getFechaYHora()
	{
		return fechayhora;
	}

	public void setFechaYHora(LocalDate unaFechaYHora)
	{
		fechayhora = unaFechaYHora;
	}
	
	public String getLugar()
	{
		return lugar;
	}
	
	public void setLugar(String unLugar)
	{
		lugar = unLugar;
	}
	
	public Boolean llenoDeConfirmados()
	{
		if(this.getInscripciones().size()>0)
		{
			ArrayList<Inscripcion> confirmados = (ArrayList<Inscripcion>) this.getInscripciones().stream()
												.filter(unaInscripcion -> unaInscripcion.getPrioridad() == 1)
												.collect(Collectors.toList());
			
			return confirmados.size()==10;
		}
		else
		{
			return false;
		}
	}
	
	public int cantidadDeInscripciones(){
		return this.getInscripciones().size();
	}
	
	public void removerInscripcion(Inscripcion unaInscripcion)
	{
		if (!confirmado)
		{
			Boolean estabaLleno = this.llenoDeConfirmados();
			this.getInscripciones().remove(unaInscripcion);
			Boolean estaLleno = this.llenoDeConfirmados();
			if(estabaLleno && !estaLleno)
			{
				getNotifier().notificarCuposLibres(this);
			}
		}
	}
	
	public Boolean removerJugador(Jugador unJugador)
	{	
		if (!confirmado)
		{
			Inscripcion inscripcionARemover = this.inscripcionDelJugador(unJugador);
			
			this.removerInscripcion(inscripcionARemover);
			return true;
		}
		
		return false;
	}
	
	public Inscripcion inscripcionDelJugador(Jugador unJugador){
		try{
			Inscripcion inscripcionDelJugador = this.getInscripciones().stream()
					  .filter(inscripcion -> unJugador == inscripcion.getJugador())
					  .collect(Collectors.toList()).get(0);
			
			return inscripcionDelJugador;
		}catch(Exception e){
			return null;
		}
	}
	
	
	public ArrayList<Inscripcion> obtenerInscripcionesAReemplazar(Inscripcion unaInscripcion)
	{
		//No queda espacio en la coleccion, tengo que reemplazar una inscripcion condicional/solidaria
		ArrayList<Inscripcion> inscripcionesAReemplazar = (ArrayList<Inscripcion>) this.getInscripciones().stream()
														  .filter(inscripcion -> inscripcion.getPrioridad() > unaInscripcion.getPrioridad())
														  .collect(Collectors.toList());
		
		return inscripcionesAReemplazar;
	}

	private Notificador getNotifier() {
		return notifier;
	}

	private void setNotifier(Notificador notifier) {
		this.notifier = notifier;
	}

	public boolean participoElJugador(Jugador unJugador) {
		Inscripcion inscripcionDelJugador = inscripcionDelJugador(unJugador);
		if(inscripcionDelJugador == null)
			return false;
		
		return true;
	}
	
	public void agregarMocion (Mocion unaMocion)
	{
		mociones.add(unaMocion);
	}
	
	public int cantidadMociones()
	{
		return mociones.size();
	}
	
	//Asumimos que el administrador carga los datos de Jugador y de Inscripcion y llama al metodo con los objetos armados 
	// y así evitamos un long parameter list
	public void aceptarMocion(Mocion unaMocion, Jugador unJugador, Inscripcion unaInscripcion)
	{
		unaMocion.aceptar();
		unaInscripcion.setJugador(unJugador);
		this.agregarInscripcion(unaInscripcion);
	}
	
	public void rechazarMocion(Mocion unaMocion, String motivo)
	{
		unaMocion.rechazar();
		this.notifier.mocionRechazada(unaMocion, motivo);
	}
	
	public ArrayList<Jugador> obtenerJugadores()
	{
		ArrayList<Jugador> jugadores = new ArrayList<Jugador>();
		for(Inscripcion inscripcion:getInscripciones())
			jugadores.add(inscripcion.getJugador());
		
		return jugadores;
	}
	
	
	public void generarEquiposTentativos(Comparator<? super Jugador> criterio, ArrayList<Integer> indicesEquipo1)
	{
		this.equipo1 = new Equipo("Equipo1", true);
		this.equipo2 = new Equipo("Equipo2", false);
		
		ArrayList<Jugador> jugadoresSegunCriterio = this.ordenarJugadoresPorCriterio(criterio);
		
		generarListasDeEquipos(jugadoresSegunCriterio, indicesEquipo1);
	}
	
	public void generarEquiposTentativos(ArrayList<Comparator<Jugador>> criterios, ArrayList<Integer> indicesEquipo1)
	{
		this.equipo1 = new Equipo("Equipo1", true);
		this.equipo2 = new Equipo("Equipo2", false);
		
		ArrayList<Jugador> jugadoresSegunCriterio = this.ordenarJugadoresPorCriterio(criterios);
		
		generarListasDeEquipos(jugadoresSegunCriterio, indicesEquipo1);
	}
	
	public ArrayList<Jugador> ordenarJugadoresPorCriterio(Comparator<? super Jugador> criterio) 
	{
		ArrayList<Jugador> jugadoresSegunCriterio = (ArrayList<Jugador>) this.obtenerJugadores().stream()
																		.sorted(criterio)
																		.collect(Collectors.toList());
		return jugadoresSegunCriterio;
	}
	
	public ArrayList<Jugador> ordenarJugadoresPorCriterio(ArrayList<Comparator<Jugador>> criterios)
	{		
		Map<Jugador, Integer> dic = new HashMap<Jugador, Integer>();
		for(Jugador jugador : this.obtenerJugadores())
		{
			dic.put(jugador, 0);
		}
		for (Comparator<? super Jugador> criterio : criterios)
		{
				ArrayList<Jugador> jugadoresSegunCriterio = (ArrayList<Jugador>) this.obtenerJugadores().stream()
																					.sorted(criterio)
																					.collect(Collectors.toList());
				int index = 0;
				for(Jugador jugador : jugadoresSegunCriterio)
				{
					dic.put(jugador, dic.get(jugador)+index);
					index++;
				}
		}
		
		ArrayList<Jugador> jugadoresSegunCriterioDef = this.ordenarJugadoresPorCriterio(new Comparator<Jugador>() {
			public int compare(Jugador jugador1, Jugador jugador2) {
				if(dic.get(jugador1) > dic.get(jugador2)) return 1;
				else return -1;
			}
		});
		return jugadoresSegunCriterioDef;
	}

	public void generarListasDeEquipos(ArrayList<Jugador> listaJugadores, ArrayList<Integer> indicesEquipo1)
	{
		for (int i=1; i<listaJugadores.size()+1; i++)
		{
			int posFinalAObtener = i-1;
            if(indicesEquipo1.contains(i))
            	getEquipo1().agregarJugador(listaJugadores.get(posFinalAObtener));
            else
            	getEquipo2().agregarJugador(listaJugadores.get(posFinalAObtener));
            
		}
	}

	public void confirmarEquipos()
	{
		confirmado = true;
	}

	public Equipo getEquipo1() {
		return equipo1;
	}

	public Equipo getEquipo2() {
		return equipo2;
	}

	/*
	public void agregarCalificacion(Calificacion calif){
		calificaciones.add(calif);
	}
	*/
}
