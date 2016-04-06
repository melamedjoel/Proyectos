package futbol_5_1.repositorios;

import java.io.Serializable;
import java.time.LocalDate;
import java.time.Month;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.uqbar.commons.utils.Observable;

import futbol_5_1.*;

/**
 * 
 * @author npasserini
 */
@SuppressWarnings("serial")
@Observable
public class RepositorioJugadores implements Serializable {
	private static RepositorioJugadores instance;
	private List<Jugador> data = new ArrayList<Jugador>();
	private Partido partidoDevoto = new Partido("Devoto", LocalDate.of(2014, Month.FEBRUARY, 20));

	public static synchronized RepositorioJugadores getInstance() {
		if (instance == null) {
			instance = new RepositorioJugadores();
		}
		return instance;
	}

	@SuppressWarnings("deprecation")
	private RepositorioJugadores() {
		Jugador jose = new Jugador("Jose", "Pepe", new Date(), 8);
		Jugador ignacio = new Jugador("Ignacio", "Nacho", new Date(), 9);
		Jugador ricardo = new Jugador("Ricardo", "Ricky", new Date(), 6);
		Jugador francisco =new Jugador("Francisco", "Pancho", new Date(), 4) ;
		Jugador pedro = new Jugador("Pedro", "Chiqui", new Date(), 6);
		Jugador nicolas = new Jugador("Nicolas", "Nico", new Date(), 8);
		Jugador julian = new Jugador("Julian", "Chavo", new Date(), 10);
		Jugador eric = new Jugador("Eric", "Nerdi", new Date(), 1);
		Jugador axel =new Jugador("Axel", "Suva", new Date(), 5);
		Jugador joel =new Jugador("Joel", "Mela", new Date(), 3);
		
		jose.agregarAmigo(ignacio);
		
		Calificacion calificacion = new Calificacion(9, "Es un crack!", jose, partidoDevoto);
		jose.agregarCalificacion(calificacion);
		
		Infraccion infraccion = new Infraccion();
		infraccion.setFecha(LocalDate.of(1990, Month.FEBRUARY, 20));
		infraccion.setMotivo("El usuario canceló su participación y no informó reemplazo");
		jose.agregarInfraccion(infraccion);
		
        InscripcionEstandar inscripcionJose = new InscripcionEstandar(jose);
        InscripcionEstandar inscripcionIgnacio = new InscripcionEstandar(ignacio);
        InscripcionEstandar inscripcionRicardo= new InscripcionEstandar(ricardo);
        InscripcionEstandar inscripcionFrancisco= new InscripcionEstandar(francisco);
        InscripcionEstandar inscripcionPedro= new InscripcionEstandar(pedro);
        InscripcionEstandar inscripcionNicolas= new InscripcionEstandar(nicolas);
        InscripcionEstandar inscripcionJulian= new InscripcionEstandar(julian);
        InscripcionEstandar inscripcionEric= new InscripcionEstandar(eric);
        InscripcionEstandar inscripcionAxel= new InscripcionEstandar(axel);
        InscripcionEstandar inscripcionJoel= new InscripcionEstandar(joel);
        
        jose.inscribirAPartido(partidoDevoto, inscripcionJose);
        ignacio.inscribirAPartido(partidoDevoto, inscripcionIgnacio);
        ricardo.inscribirAPartido(partidoDevoto, inscripcionRicardo);
        francisco.inscribirAPartido(partidoDevoto, inscripcionFrancisco);
        pedro.inscribirAPartido(partidoDevoto, inscripcionPedro);
        nicolas.inscribirAPartido(partidoDevoto, inscripcionNicolas);
        julian.inscribirAPartido(partidoDevoto, inscripcionJulian);
        eric.inscribirAPartido(partidoDevoto, inscripcionEric);
        axel.inscribirAPartido(partidoDevoto, inscripcionAxel);
        joel.inscribirAPartido(partidoDevoto, inscripcionJoel);

		this.create(jose);
		this.create(ignacio);
		this.create(ricardo);
		this.create(francisco);
		this.create(pedro);
		this.create(nicolas);
		this.create(julian);
		this.create(eric);
		this.create(axel);
		this.create(joel);
	}

	// ********************************************************
	// ** Altas y bajas
	// ********************************************************

	public void create(Jugador jugador) {
//		jugador.validar();

		jugador.setId(this.data.size() + 1);
		this.data.add(jugador);
	}

	public void delete(Jugador jugador) {
		this.data.remove(jugador);
	}


	// ********************************************************
	// ** Búsquedas
	// ********************************************************

	public List<Jugador> search(String nombre, String apodo, Date fechaNacimiento, int handicapDesde, int handicapHasta, double promedioDesde, double promedioHasta, Boolean tieneInfracciones) {
		List<Jugador> resultados = new ArrayList<Jugador>();
		
		for (Jugador jugador : this.data) {
			if (matchStartWith(nombre, jugador.getNombre()) 
					&& matchContains(apodo, jugador.getApodo())
					&& matchMenor(fechaNacimiento, jugador.getFechaNacimiento())
					&& matchBetween(jugador.obtenerHandicap(), handicapDesde, handicapHasta)
					&& matchBetween(jugador.getPromedioUltimoPartido(), promedioDesde, promedioHasta)
					&& matchBoolean(tieneInfracciones, jugador.tieneInfracciones())
				) {
				resultados.add(jugador);
			}
		}

		return resultados;
	}

	public List<Jugador> jugadores() {
		List<Jugador> resultados = new ArrayList<Jugador>();
		for (Jugador jugador : this.data) {
				resultados.add(jugador);
			
		}
		return resultados;
	}
	
	private boolean matchBetween(double realValue, double valorDesde, double valorHasta) {
		return (realValue >= valorDesde && realValue <= valorHasta);
	}

	private boolean matchBetween(int realValue, int valorDesde, int valorHasta) {
		return (realValue >= valorDesde && realValue <= valorHasta);
	}

	private boolean matchBoolean(Boolean expectedValue,	Boolean realValue) {
		return (expectedValue == null 
				|| expectedValue == realValue);
	}

	private boolean matchMenor(Date fechaHasta, Date fechaNacimiento2) {
		return (fechaHasta == null
			|| fechaHasta.compareTo(fechaNacimiento2) >= 0);
	}

	protected boolean matchStartWith(Object expectedValue, Object realValue) {
		return expectedValue == null
			|| realValue.toString().toLowerCase().startsWith(expectedValue.toString().toLowerCase());
	}
	
	protected boolean matchContains(Object expectedValue, Object realValue) {
		return expectedValue == null
			|| realValue.toString().toLowerCase().contains(expectedValue.toString().toLowerCase());
	}
	
	public Jugador searchById(int id) {
		for (Jugador jugador : this.data) {
			if (jugador.getId().equals(id)) {
				return jugador;
			}
		}
		return null;
	}

	public List<Jugador> getAll() {
		return this.data;
	}
	
	public Partido getPartido(){
		return this.partidoDevoto;
	}
	

}
