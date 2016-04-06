package futbol5;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.IntStream;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import futbol5.excepciones.BusinessException;
import futbol5.ordenamiento.OrdenamientoCalificacionUltimos2Partidos;
import futbol5.ordenamiento.OrdenamientoMix;
import futbol5.ordenamiento.OrdenamientoPorHandicap;
import futbol5.utilitarios.Lists;
public class TestGenerarEquipo {

	private Partido partidoPocosJugadores;
	private Partido partidoOk;
	private Partido partido1;
	private Jugador sytek;
	private Jugador chicho;
	private Jugador pato;
	private Jugador lechu;
	private Jugador rodri;
	private Jugador mike;
	private Jugador dodi;
	private Jugador roly;
	private Jugador eric;
	private Jugador leo;
	private Jugador ferme;

	@Before
	public void init() {
		partidoPocosJugadores = new Partido();
		
		for (int i = 0; i < 6; i++) {
			inscribir(partidoPocosJugadores, new Jugador());
		}
		
		partidoOk = new Partido();
		partido1 = new Partido();
		sytek = new Jugador("sytek", 3d,Lists.newArrayList(5d,8d) );
		chicho = new Jugador("chicho", 5d, Lists.newArrayList(6d, 8d, 6d));
		pato = new Jugador("pato", 8d, Lists.newArrayList(9d, 8d));
		lechu = new Jugador("lechu", 6d, Lists.newArrayList(7d, 9d));
		rodri = new Jugador("rodri", 4d, Lists.newArrayList(5d, 8d));
		mike = new Jugador("mike", 1d, Lists.newArrayList(4d, 10d, 6d, 8d));
		dodi = new Jugador("dodi", 7d, Lists.newArrayList(6d, 7d));
		roly = new Jugador("roly", 9d, Lists.newArrayList(6d, 6d, 9d));
		eric = new Jugador("eric", 6d, Lists.newArrayList(9d, 4d, 3d, 10d));
		eric.modoSolidario();
		leo = new Jugador("leo", 2d, Lists.newArrayList(6d, 6d, 6d));
		leo.modoSolidario();
		ferme = new Jugador("ferme", 10d, Lists.newArrayList(9d, 10d, 7d));
		ferme.modoSolidario();
		inscribir(partidoOk, sytek);
		inscribir(partidoOk, chicho);
		inscribir(partidoOk, pato);
		inscribir(partidoOk, lechu);
		inscribir(partidoOk, rodri);
		inscribir(partidoOk, mike);
		inscribir(partidoOk, dodi);
		inscribir(partidoOk, eric);
		inscribir(partidoOk, leo);
		inscribir(partidoOk, ferme);
		inscribir(partido1, sytek);
		inscribir(partido1, chicho);
		inscribir(partido1, pato);
		inscribir(partido1, lechu);
		inscribir(partido1, rodri);
		inscribir(partido1, mike);
		inscribir(partido1, dodi);
		inscribir(partido1, roly);
		inscribir(partido1, eric);
		inscribir(partido1, leo);
		inscribir(partido1, ferme);
	}

	@Test(expected=BusinessException.class)
	public void pocosInscriptosNoGeneranEquipos() {
		ArrayList<Integer> indicesEquipo1 = new ArrayList<Integer>();
		ArrayList<Integer> indicesEquipo2 = new ArrayList<Integer>();
		//par impar
		indicesEquipo1.add(0);
		indicesEquipo1.add(2);
		indicesEquipo1.add(4);
		indicesEquipo1.add(6);
		indicesEquipo1.add(8);
		indicesEquipo2.add(1);
		indicesEquipo2.add(3);
		indicesEquipo2.add(5);
		indicesEquipo2.add(7);
		indicesEquipo2.add(9);
		partidoPocosJugadores.generarEquipos(indicesEquipo1, indicesEquipo2);
	}

	@Test(expected=BusinessException.class)
	public void partidoSinIniciarNoPuedeGenerarEquipos() {
		for (int i = 0; i < 3; i++) {
			inscribir(partidoPocosJugadores, new Jugador());
		}
		ArrayList<Integer> indicesEquipo1 = new ArrayList<Integer>();
		ArrayList<Integer> indicesEquipo2 = new ArrayList<Integer>();
		//par impar
		indicesEquipo1.add(0);
		indicesEquipo1.add(2);
		indicesEquipo1.add(4);
		indicesEquipo1.add(6);
		indicesEquipo1.add(8);
		indicesEquipo2.add(1);
		indicesEquipo2.add(3);
		indicesEquipo2.add(5);
		indicesEquipo2.add(7);
		indicesEquipo2.add(9);
		partidoPocosJugadores.generarEquipos(indicesEquipo1, indicesEquipo2);
	}

	@Test
	public void inscripcionJugadorNuevoDesplazaAPrimerSolidario() {
		inscribir(partido1, roly);
		Assert.assertTrue(partido1.getInscriptos().contains(roly));
		Assert.assertFalse(partido1.getInscriptos().contains(eric));
		Assert.assertTrue(partido1.getInscriptos().contains(ferme));
	}

	@Test
	public void generarEquiposPorHandicap() {
		System.out.println("******************************************");
		System.out.println("ordenamiento por handicap");
		List<Jugador> jugadores=partido1.ordenarEquipos();
		for (Jugador jugador : jugadores) {
			System.out.println("Jugador: " + jugador + " - calificacion: " + jugador.getCalificacion());
		}	
		Assert.assertEquals(Lists.newArrayList(ferme, roly, pato, dodi, lechu, chicho, rodri, sytek, leo, mike),
				jugadores);
	}

	@Test
	public void generarEquiposPorCalificacionUltimos2Partidos() {
		partido1.setCriterioOrdenamiento(new OrdenamientoCalificacionUltimos2Partidos());
		System.out.println("******************************************");
		System.out.println("ordenamiento por ultimas 2 calificaciones");
		List<Jugador> jugadores=partido1.ordenarEquipos();
		for (Jugador jugador : jugadores) {
			//Tomando los 2 últimos puntajes
			List<Double> puntajes=jugador.getPuntajes();
			List<Double> misPuntajes=new ArrayList<Double>(); 
			if(!puntajes.isEmpty()){
				misPuntajes.add(jugador.getPuntajes().get(puntajes.size()-1));
			}
			if(puntajes.size()>1){
				misPuntajes.add(jugador.getPuntajes().get(puntajes.size()-2));
			}
			
			//Cálculo de promedio
			Double promedio=0d;
			for (Double puntaje : misPuntajes) {
				promedio+=puntaje;
			}
			promedio/=misPuntajes.size();
			
			System.out.println("Jugador: " + jugador + " puntajes: " + jugador.getPuntajes() + " ult.puntajes: " + misPuntajes +" promedio: " + promedio);
		}
		Assert.assertEquals(Lists.newArrayList(ferme, pato, lechu, roly, mike, chicho, dodi, rodri, sytek, leo),
				jugadores);
	}

	@Test
	public void generarEquiposPorMixDeCriterios() {
		OrdenamientoMix ordenamientoMix = new OrdenamientoMix();
		ordenamientoMix.addCriterio(new OrdenamientoCalificacionUltimos2Partidos());
		ordenamientoMix.addCriterio(new OrdenamientoPorHandicap());
		partido1.setCriterioOrdenamiento(ordenamientoMix);
		System.out.println("******************************************");
		System.out.println("ordenamiento por mix");
		List<Jugador> jugadores=partido1.ordenarEquipos();
		System.out.println(jugadores);
		Assert.assertEquals(Lists.newArrayList(ferme, roly, pato, lechu, dodi, chicho, rodri, sytek, leo, mike),
				jugadores);
	}

	@Test
	public void distribuirEquiposParEImpar() {
		partido1.cerrar();
		ArrayList<Integer> indicesEquipo1 = new ArrayList<Integer>();
		ArrayList<Integer> indicesEquipo2 = new ArrayList<Integer>();
		//par impar
		indicesEquipo1.add(0);
		indicesEquipo1.add(2);
		indicesEquipo1.add(4);
		indicesEquipo1.add(6);
		indicesEquipo1.add(8);
		indicesEquipo2.add(1);
		indicesEquipo2.add(3);
		indicesEquipo2.add(5);
		indicesEquipo2.add(7);
		indicesEquipo2.add(9);
		partido1.generarEquipos(indicesEquipo1, indicesEquipo2);
		Assert.assertEquals(Lists.newArrayList(ferme, pato, lechu, rodri, leo), partido1.getEquipo1().getJugadores());
		Assert.assertEquals(Lists.newArrayList(roly, dodi, chicho, sytek, mike), partido1.getEquipo2().getJugadores());
	}
	@Test
	public void distribuirEquipos14589() {
		//partido1.setDistribucionEquipos(16); // ordenamiento --> se comenta por code smell arreglado
		partido1.cerrar();
		ArrayList<Integer> indicesEquipo1 = new ArrayList<Integer>();
		ArrayList<Integer> indicesEquipo2 = new ArrayList<Integer>();
		indicesEquipo1.add(0);
		indicesEquipo1.add(3);
		indicesEquipo1.add(4);
		indicesEquipo1.add(7);
		indicesEquipo1.add(8);
		indicesEquipo2.add(1);
		indicesEquipo2.add(2);
		indicesEquipo2.add(5);
		indicesEquipo2.add(6);
		indicesEquipo2.add(9);
		partido1.generarEquipos(indicesEquipo1, indicesEquipo2);
		Assert.assertEquals(Lists.newArrayList(ferme, dodi, lechu, sytek, leo), partido1.getEquipo1().getJugadores());
		Assert.assertEquals(Lists.newArrayList(roly, pato, chicho, rodri, mike), partido1.getEquipo2().getJugadores());
	}
	
	@Test(expected=BusinessException.class)
	public void generarEquiposCuandoSeCierra() {
		//partido1.setDistribucionEquipos(16); // ordenamiento --> se comenta porque no se usa mas por code smell arreglado
		partido1.cerrar();
		ArrayList<Integer> indicesEquipo1 = new ArrayList<Integer>();
		ArrayList<Integer> indicesEquipo2 = new ArrayList<Integer>();
		indicesEquipo1.add(0);
		indicesEquipo1.add(3);
		indicesEquipo1.add(4);
		indicesEquipo1.add(7);
		indicesEquipo1.add(8);
		indicesEquipo2.add(1);
		indicesEquipo2.add(2);
		indicesEquipo2.add(5);
		indicesEquipo2.add(6);
		indicesEquipo2.add(9);
		partido1.generarEquipos(indicesEquipo1, indicesEquipo2);
		partido1.generarEquipos(indicesEquipo1, indicesEquipo2);
	}

	/** *************************************************************************
	 * METODOS AUXILIARES DE LOS TESTS
	 ****************************************************************************/
	public void inscribir(Partido partido, Jugador jugador) {
		partido.inscribir(jugador);
	}

}
