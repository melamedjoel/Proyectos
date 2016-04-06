package futbol_5_1;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.awt.Color;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.Date;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.persistence.Transient;

import futbol_5_1.exceptions.NoJugoSuficientesPartidosException;

@SuppressWarnings("serial")
@Entity
@Table(name = "tl_jugador")
public class Jugador extends Persona {
	
	@Column(name="ID_Jugador")
	private Long id;

	private String nombre;
	
	private String apodo;
	
	@Temporal(TemporalType.TIMESTAMP)
	private Date fechaNacimiento;
	
	private int handicap;
	
	@OneToMany(fetch = FetchType.LAZY, mappedBy = "jugador")
	private List<Infraccion> infracciones = new ArrayList<Infraccion>();
	
	@ManyToMany(fetch = FetchType.LAZY, cascade = CascadeType.PERSIST)
	@JoinTable(name = "tl_amigos_persona", joinColumns = @JoinColumn(name = "ID_Jugador", nullable = false, updatable = false), inverseJoinColumns = @JoinColumn(name = "ID_Persona", nullable = false, updatable = false))
	private List<Persona> amigos = new ArrayList<Persona>();
	
	@OneToMany(fetch = FetchType.LAZY, mappedBy = "jugador")
	private List<Calificacion> calificaciones = new ArrayList<Calificacion>();
	
	@Transient
	private Notificador notifier = new Notificador();
	
	public Jugador(){}
	
	public Jugador(String unNombre)
	{
		this.setNombre(unNombre);
	}
	
	public Jugador(String unNombre, String unApodo, Date unaFecha, int unHandicap)
	{
		this.setNombre(unNombre);
		this.setApodo(unApodo);
		this.setFechaNacimiento(unaFecha);
		this.setHandicap(unHandicap);
	}
	
	public Jugador(String unNombre, Notificador notifier)
	{
		this.setNombre(unNombre);
		this.setNotifier(notifier);
	}
	
	public String getNombre()
	{
		return nombre;
	}
	
	public void setNombre(String unNombre)
	{
		nombre = unNombre;
	}

	public void agregarAmigo(Persona amigo){
		amigos.add(amigo);
	}
	
	public void agregarCalificacion(Calificacion calif){
		calificaciones.add(calif);
	}
	
	public ArrayList<Persona> getAmigos(){
		return (ArrayList<Persona>) amigos;
	}
	
	public ArrayList<Calificacion> getCalificaciones(){
		return (ArrayList<Calificacion>) calificaciones;
	}
	
	public void calificar(Partido unPartido, Jugador unJugador, int puntaje, String critica) throws Exception{
		if(!unPartido.participoElJugador(unJugador) || !unPartido.participoElJugador(this))
			throw new Exception("Jugador no participo en el partido");
	
		Calificacion unaCalificacion = new Calificacion(puntaje, critica, unJugador, unPartido);
		unJugador.agregarCalificacion(unaCalificacion);
		
		//unPartido.agregarCalificacion(unaCalificacion);
	}
	
	public Boolean inscribirAPartido(Partido unPartido, Inscripcion unaInscripcion)
	{
		if (unPartido.llenoDeConfirmados())
			return false;

		return unaInscripcion.permitirInscripcion(unPartido);
	}
	
	public void proponerAmigoAPartido(Partido unPartido, Persona unAmigo) throws Exception
	{
		if(this.getAmigos().contains(unAmigo))
		{
			Mocion unaMocion = new Mocion(unAmigo, unPartido);
			unPartido.agregarMocion(unaMocion);
		}
		else
		{
			throw new Exception("No se puede proponer a alguien que no es amigo");
		}

	}
	
	public Boolean darseDeBaja(Partido unPartido, String motivo){
		if(unPartido.removerJugador(this)){
			getNotifier().generarInfraccion(this, motivo);
			return true;
		}

		return false;
	}
	
	
	public Boolean darseDeBaja(Partido unPartido, Jugador jugadorReemplazante){
		Inscripcion inscripcionDelJugador = unPartido.inscripcionDelJugador(this);
		if(inscripcionDelJugador!=null){
			inscripcionDelJugador.setJugador(jugadorReemplazante);
			return true;
		}
		return false;
	}

	public void notificarInscripcionAmigos() {
		getNotifier().notificarInscripcionAmigos(this.getAmigos());
	}

	Notificador getNotifier() {
		return notifier;
	}
	

	public void setNotifier(Notificador notifier) {
		this.notifier = notifier;
	}
	
	public void setHandicap(int unHandicap)
	{
		handicap = unHandicap;
	}
	
	//Le puse el nombre en castellano para usar este mismo metodo para generar la lista de jugadores
	public int obtenerHandicap()
	{
		return handicap;
	}
	//renombro con get para que bindee
	public double getHandicap(){
		return obtenerHandicap();
	}
	
	public double obtenerPromedioUltimoPartido()
	{
		try 
		{
			return obtenerPromedioSegunCantUltimosPartidos(1);
		}
		catch(Exception ex)
		{
			return 0;
		}
	}
	
	//renombro con get para que bindee
	public double getPromedioUltimoPartido() {
		return obtenerPromedioUltimoPartido();
	}
	
	
	public double obtenerPromedioUltimosNPartidos(int cantidadPartidos)
	{
		try 
		{
			return obtenerPromedioSegunCantUltimosPartidos(cantidadPartidos);
		}
		catch(Exception ex)
		{
			return 0;
		}
	}
	//hubo que redefinir estas dos funciones ya que obtenerPromedioSegunCantUltimosPartidos
	//devuelve una excepcion que desde la UI era imposible atrapar y devolver 0 para el caso
	//de que no haya jugado partidos, las grillas no se hacian y tiraba error la pantalla
	public double obtenerPromedioSegunCantUltimosPartidos(int cantidadPartidos)
	{
		ArrayList<Calificacion> calificaciones = this.getCalificaciones();
		
		if (calificaciones.size() < cantidadPartidos)
			throw new NoJugoSuficientesPartidosException("No se puede obtener el promedio ya que " + this.getNombre() + " no jugó " + cantidadPartidos + " partidos");
		
		List<Calificacion> listCalificaciones = calificaciones.subList(calificaciones.size() - cantidadPartidos, calificaciones.size());
		int sumaCalificaciones = listCalificaciones.stream().mapToInt(calificacion -> calificacion.getPuntaje()).sum();
		double promedio = sumaCalificaciones / cantidadPartidos;
		
		return promedio;
	}
	
	//renombro con get para que bindee
	public double getPromedioTodosLosPartidos(){
		try 
		{
			return obtenerPromedioTodosLosPartidos();
		}
		catch(Exception ex)
		{
			return 0;
		}
	}
	
	public double obtenerPromedioTodosLosPartidos(){
		return obtenerPromedioSegunCantUltimosPartidos(calificaciones.size()); 
	}
	
	public static Comparator<Jugador> comparatorHandicap()
	{ 
		return new Comparator<Jugador>() {
			public int compare(Jugador jugador1, Jugador jugador2) {
				if(jugador1.obtenerHandicap() > jugador2.obtenerHandicap())
					return -1;
				else
					return 1;
			}
		};
	}

	public static Comparator<Jugador> comparatorAVGLastMatch()
	{
		return new Comparator<Jugador>() {
			public int compare(Jugador jugador1, Jugador jugador2) {
				if(jugador1.obtenerPromedioUltimoPartido() > jugador2.obtenerPromedioUltimoPartido())
					return -1;
				else
					return 1;
			}
		};
	}
		
	public static Comparator<Jugador> comparatorAVGLastNMatches(int n)
	{
		return new Comparator<Jugador>() {
			public int compare(Jugador jugador1, Jugador jugador2) {
				if(jugador1.obtenerPromedioUltimosNPartidos(n) > jugador2.obtenerPromedioUltimosNPartidos(n))
					return -1;
				else
					return 1;
			}
		};
	}

	public String getApodo() {
		return apodo;
	}

	public void setApodo(String apodo) {
		this.apodo = apodo;
	}

	public Date getFechaNacimiento() {
		return fechaNacimiento;
	}

	public void setFechaNacimiento(Date fechaNacimiento) {
		this.fechaNacimiento = fechaNacimiento;
	}
	
	public ArrayList<Infraccion> getInfracciones(){
		return (ArrayList<Infraccion>) this.infracciones;
	}

	public void agregarInfraccion(Infraccion nuevaInfraccion) {
		this.infracciones.add(nuevaInfraccion);
	}

	public Boolean tieneInfracciones() {
		return !this.infracciones.isEmpty();
	}
	
	public int cantidadPartidosJugados() {
		return this.calificaciones.size();
	}
	
	public Color colorJugador() {
		return (this.getHandicap() >= 8) ? Color.BLUE : Color.BLACK;
	}
}
