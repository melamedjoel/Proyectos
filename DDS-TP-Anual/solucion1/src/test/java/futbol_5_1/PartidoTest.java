package futbol_5_1;

import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import java.time.LocalDate;
import java.time.Month;
import java.util.ArrayList;
import java.util.Comparator;
import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;

public class PartidoTest {

	private Partido unPartido;
	private Partido unPartidoAGenerar;
	private Notificador enviadorMails;
	
	private	InscripcionEstandar unaInscripcionEstandarDePrueba1;
	private	InscripcionEstandar unaInscripcionEstandarDePrueba2;
	private	InscripcionEstandar unaInscripcionEstandarDePrueba3;
	private	InscripcionEstandar unaInscripcionEstandarDePrueba4;
	private	InscripcionEstandar unaInscripcionEstandarDePrueba5;
	private	InscripcionEstandar unaInscripcionEstandarDePrueba6;
	private	InscripcionEstandar unaInscripcionEstandarDePrueba7;
	private	InscripcionEstandar unaInscripcionEstandarDePrueba8;
	private	InscripcionEstandar unaInscripcionEstandarDePrueba9;
	private	InscripcionEstandar unaInscripcionEstandarDePrueba10;

	//mockeo los jugadores por la dificultad de cargarle los datos para obtener los promedios de ultimos partidos
	@Mock
	Jugador unJugador1;
	@Mock
	Jugador unJugador2;
	@Mock
	Jugador unJugador3;
	@Mock
	Jugador unJugador4;
	@Mock
	Jugador unJugador5;
	@Mock
	Jugador unJugador6;
	@Mock
	Jugador unJugador7;
	@Mock
	Jugador unJugador8;
	@Mock
	Jugador unJugador9;
	@Mock
	Jugador unJugador10;
	
	@Before
	public void setUp() throws Exception {
		MockitoAnnotations.initMocks(this);
        
        enviadorMails = mock(Notificador.class);
        
        unPartido = new Partido("Paternal", LocalDate.of(2014, Month.JULY, 18), enviadorMails);
        unPartidoAGenerar = new Partido("Paternal", LocalDate.of(2014, Month.JULY, 18), enviadorMails);
        
        unaInscripcionEstandarDePrueba1 = new InscripcionEstandar(unJugador1);
        unaInscripcionEstandarDePrueba2 = new InscripcionEstandar(unJugador2);
        unaInscripcionEstandarDePrueba3 = new InscripcionEstandar(unJugador3);
        unaInscripcionEstandarDePrueba4 = new InscripcionEstandar(unJugador4);
        unaInscripcionEstandarDePrueba5 = new InscripcionEstandar(unJugador5);
        unaInscripcionEstandarDePrueba6 = new InscripcionEstandar(unJugador6);
        unaInscripcionEstandarDePrueba7 = new InscripcionEstandar(unJugador7);
        unaInscripcionEstandarDePrueba8 = new InscripcionEstandar(unJugador8);
        unaInscripcionEstandarDePrueba9 = new InscripcionEstandar(unJugador9);
        unaInscripcionEstandarDePrueba10 = new InscripcionEstandar(unJugador10);
	}

	@After
	public void tearDown() throws Exception {
	}

	@Test
	public void testCrearPartidoOk() {
		Assert.assertTrue(unPartido.getLugar() == "Paternal");
	}
	
	@Test
	public void testCrearPartidoFail() {
		Assert.assertFalse(unPartido.getLugar() == "Villa Crespo");
	}
	
	@Test
	public void testLlenoDeConfirmadosOk() {
		this.cargarInscripciones(unPartido);
		Assert.assertTrue(unPartido.llenoDeConfirmados());
		verify(enviadorMails).notificarPartidoLleno(unPartido); //esto verifica que haya llamado UNA vez al enviador de mails
	}
	
	@Test
	public void testLlenoDeConfirmadosFail() {
		unPartido.agregarInscripcion(unaInscripcionEstandarDePrueba1);
		Assert.assertFalse(unPartido.llenoDeConfirmados() == true);
	}
	
	
	@Test
	public void testAgregarInscripcionOk(){
		unPartido.agregarInscripcion(unaInscripcionEstandarDePrueba1);
		Assert.assertTrue(unPartido.cantidadDeInscripciones() == 1);
	}
	
	@Test
	public void testRemoverInscripcionYNotificarCupoLibreOk() {
		this.cargarInscripciones(unPartido);
		unPartido.removerInscripcion(unaInscripcionEstandarDePrueba1);
		Assert.assertTrue(unPartido.cantidadDeInscripciones() == 9);
		verify(enviadorMails).notificarCuposLibres(unPartido); //esto verifica que haya llamado UNA vez al enviador de mails
	}
	
	@Test
	public void testRemoverInscripcionOk() {
		unPartido.agregarInscripcion(unaInscripcionEstandarDePrueba1);
		unPartido.removerInscripcion(unaInscripcionEstandarDePrueba1);
		Assert.assertEquals(0, unPartido.cantidadDeInscripciones());
	}
	
	@Test
	public void testRemoverJugadorOk(){
		unPartido.agregarInscripcion(unaInscripcionEstandarDePrueba1);
		Assert.assertTrue(unPartido.removerJugador(unJugador1));
	}
	
	@Test
	public void testRemoverJugadorFail() throws Exception{
		unPartido.agregarInscripcion(unaInscripcionEstandarDePrueba1);
	}
	
	@Test
	public void testOrdenarPorHandicap(){
		Mockito.doReturn(2).when(unJugador1).obtenerHandicap();
		Mockito.doReturn(2).when(unJugador2).obtenerHandicap();
		Mockito.doReturn(2).when(unJugador3).obtenerHandicap();
		Mockito.doReturn(2).when(unJugador4).obtenerHandicap();
		Mockito.doReturn(2).when(unJugador5).obtenerHandicap();
		Mockito.doReturn(2).when(unJugador6).obtenerHandicap();
		Mockito.doReturn(3).when(unJugador7).obtenerHandicap();
		Mockito.doReturn(4).when(unJugador8).obtenerHandicap();
		Mockito.doReturn(1).when(unJugador9).obtenerHandicap();
		Mockito.doReturn(2).when(unJugador10).obtenerHandicap();
		
		this.cargarInscripciones(unPartidoAGenerar);
		ArrayList<Jugador> jugadores = unPartidoAGenerar.ordenarJugadoresPorCriterio(Jugador.comparatorHandicap());
		
		Assert.assertEquals(unJugador8, jugadores.get(0));
	}
	
	

	@Test
	public void testOrdenarPorPromedioUltimoPartido() throws Exception{
		Mockito.doReturn(6.0).when(unJugador1).obtenerPromedioUltimoPartido();
		Mockito.doReturn(6.0).when(unJugador2).obtenerPromedioUltimoPartido();
		Mockito.doReturn(6.0).when(unJugador3).obtenerPromedioUltimoPartido();
		Mockito.doReturn(6.0).when(unJugador4).obtenerPromedioUltimoPartido();
		Mockito.doReturn(3.0).when(unJugador5).obtenerPromedioUltimoPartido();
		Mockito.doReturn(6.0).when(unJugador6).obtenerPromedioUltimoPartido();
		Mockito.doReturn(5.0).when(unJugador7).obtenerPromedioUltimoPartido();
		Mockito.doReturn(8.5).when(unJugador8).obtenerPromedioUltimoPartido();
		Mockito.doReturn(5.0).when(unJugador9).obtenerPromedioUltimoPartido();
		Mockito.doReturn(3.0).when(unJugador10).obtenerPromedioUltimoPartido();
		this.cargarInscripciones(unPartidoAGenerar);
        ArrayList<Jugador> jugadores = unPartidoAGenerar.ordenarJugadoresPorCriterio(Jugador.comparatorAVGLastMatch());
		
		Assert.assertEquals(unJugador8, jugadores.get(0));
	}
	
	
	
	@Test
	public void testOrdenarPorPromedioUltimosNPartidos() throws Exception{
		//Haremos el test con los ultimos 3 partidos
		int nPartidos = 3; 
        Mockito.doReturn(6.0).when(unJugador1).obtenerPromedioUltimosNPartidos(nPartidos);
		Mockito.doReturn(6.0).when(unJugador2).obtenerPromedioUltimosNPartidos(nPartidos);
		Mockito.doReturn(6.0).when(unJugador3).obtenerPromedioUltimosNPartidos(nPartidos);
		Mockito.doReturn(6.0).when(unJugador4).obtenerPromedioUltimosNPartidos(nPartidos);
		Mockito.doReturn(3.0).when(unJugador5).obtenerPromedioUltimosNPartidos(nPartidos);
		Mockito.doReturn(6.0).when(unJugador6).obtenerPromedioUltimosNPartidos(nPartidos);
		Mockito.doReturn(5.0).when(unJugador7).obtenerPromedioUltimosNPartidos(nPartidos);
		Mockito.doReturn(8.5).when(unJugador8).obtenerPromedioUltimosNPartidos(nPartidos);
		Mockito.doReturn(5.0).when(unJugador9).obtenerPromedioUltimosNPartidos(nPartidos);
		Mockito.doReturn(3.0).when(unJugador10).obtenerPromedioUltimosNPartidos(nPartidos);
		this.cargarInscripciones(unPartidoAGenerar);
        ArrayList<Jugador> jugadores = unPartidoAGenerar.ordenarJugadoresPorCriterio(Jugador.comparatorAVGLastNMatches(3));
		
		Assert.assertEquals(unJugador8, jugadores.get(0));
	}
	
	@Test
	public void testOrdenarPorMixDeCriterios(){		
		Mockito.doReturn(1).when(unJugador1).obtenerHandicap();
		Mockito.doReturn(2).when(unJugador2).obtenerHandicap();
		Mockito.doReturn(3).when(unJugador3).obtenerHandicap();
		Mockito.doReturn(4).when(unJugador4).obtenerHandicap();
		Mockito.doReturn(5).when(unJugador5).obtenerHandicap();
		Mockito.doReturn(6).when(unJugador6).obtenerHandicap();
		Mockito.doReturn(7).when(unJugador7).obtenerHandicap();
		Mockito.doReturn(8).when(unJugador8).obtenerHandicap();
		Mockito.doReturn(9).when(unJugador9).obtenerHandicap();
		Mockito.doReturn(10).when(unJugador10).obtenerHandicap();
		
		Mockito.doReturn(10.0).when(unJugador1).obtenerPromedioUltimoPartido();
		Mockito.doReturn(9.0).when(unJugador2).obtenerPromedioUltimoPartido();
		Mockito.doReturn(4.0).when(unJugador3).obtenerPromedioUltimoPartido();
		Mockito.doReturn(7.0).when(unJugador4).obtenerPromedioUltimoPartido();
		Mockito.doReturn(6.0).when(unJugador5).obtenerPromedioUltimoPartido();
		Mockito.doReturn(5.0).when(unJugador6).obtenerPromedioUltimoPartido();
		Mockito.doReturn(8.0).when(unJugador7).obtenerPromedioUltimoPartido();
		Mockito.doReturn(3.0).when(unJugador8).obtenerPromedioUltimoPartido();
		Mockito.doReturn(2.0).when(unJugador9).obtenerPromedioUltimoPartido();
		Mockito.doReturn(1.0).when(unJugador10).obtenerPromedioUltimoPartido();
		
		this.cargarInscripciones(unPartidoAGenerar);
        
        ArrayList<Comparator<Jugador>> criterios = new ArrayList<Comparator<Jugador>>();
        criterios.add(Jugador.comparatorHandicap());
        criterios.add(Jugador.comparatorAVGLastMatch());
        
		ArrayList<Jugador> jugadores = unPartidoAGenerar.ordenarJugadoresPorCriterio(criterios);
		
		Assert.assertEquals(unJugador7, jugadores.get(0));
	}
	
	@Test
	public void testGenerarEquiposTentativosConAlgoritmoImparesEn1erEquipoOrdenadosPorHandicap() throws Exception{
		ArrayList<Integer> indicesEquipo1 = new ArrayList<Integer>();
		indicesEquipo1.add(1);
		indicesEquipo1.add(3);
		indicesEquipo1.add(5);
		indicesEquipo1.add(7);
		indicesEquipo1.add(9);
		Mockito.doReturn(1).when(unJugador1).obtenerHandicap();
		Mockito.doReturn(2).when(unJugador2).obtenerHandicap();
		Mockito.doReturn(3).when(unJugador3).obtenerHandicap();
		Mockito.doReturn(4).when(unJugador4).obtenerHandicap();
		Mockito.doReturn(5).when(unJugador5).obtenerHandicap();
		Mockito.doReturn(6).when(unJugador6).obtenerHandicap();
		Mockito.doReturn(7).when(unJugador7).obtenerHandicap();
		Mockito.doReturn(8).when(unJugador8).obtenerHandicap();
		Mockito.doReturn(9).when(unJugador9).obtenerHandicap();
		Mockito.doReturn(10).when(unJugador10).obtenerHandicap();
		
		this.cargarInscripciones(unPartidoAGenerar);
        
		//Al ordenar por Handicap, en las posiciones 1,3,5,7, y 9 me quedarian los jugadores 10,8,6,4,2
		//Si generamos los equipos y asertamos con esos jugadores, deberia correr bien el test
		
        unPartidoAGenerar.generarEquiposTentativos(Jugador.comparatorHandicap(), indicesEquipo1);
        
        Assert.assertEquals(unJugador10, unPartidoAGenerar.getEquipo1().obtenerJugadorPorIndice(0));
        Assert.assertEquals(unJugador8, unPartidoAGenerar.getEquipo1().obtenerJugadorPorIndice(1));
        Assert.assertEquals(unJugador6, unPartidoAGenerar.getEquipo1().obtenerJugadorPorIndice(2));
        Assert.assertEquals(unJugador4, unPartidoAGenerar.getEquipo1().obtenerJugadorPorIndice(3));
        Assert.assertEquals(unJugador2, unPartidoAGenerar.getEquipo1().obtenerJugadorPorIndice(4));
		
	}
	
	
	@Test
	public void testGenerarEquiposTentativosConAlgoritmo14589En1erEquipoOrdenadosPorHandicap() throws Exception{
		ArrayList<Integer> indicesEquipo1 = new ArrayList<Integer>();
		indicesEquipo1.add(1);
		indicesEquipo1.add(4);
		indicesEquipo1.add(5);
		indicesEquipo1.add(8);
		indicesEquipo1.add(9);

		Mockito.doReturn(1).when(unJugador1).obtenerHandicap();
		Mockito.doReturn(2).when(unJugador2).obtenerHandicap();
		Mockito.doReturn(3).when(unJugador3).obtenerHandicap();
		Mockito.doReturn(4).when(unJugador4).obtenerHandicap();
		Mockito.doReturn(5).when(unJugador5).obtenerHandicap();
		Mockito.doReturn(6).when(unJugador6).obtenerHandicap();
		Mockito.doReturn(7).when(unJugador7).obtenerHandicap();
		Mockito.doReturn(8).when(unJugador8).obtenerHandicap();
		Mockito.doReturn(9).when(unJugador9).obtenerHandicap();
		Mockito.doReturn(10).when(unJugador10).obtenerHandicap();

		this.cargarInscripciones(unPartidoAGenerar);
        
		//Al ordenar por Handicap, en las posiciones 1°, 4°, 5°, 8° y 9° me quedarian los jugadores 10,7,6,3,2
		//Si generamos los equipos y asertamos con esos jugadores, deberia correr bien el test
		
        unPartidoAGenerar.generarEquiposTentativos(Jugador.comparatorHandicap(), indicesEquipo1);
        
        Assert.assertEquals(unJugador10, unPartidoAGenerar.getEquipo1().obtenerJugadorPorIndice(0));
        Assert.assertEquals(unJugador7, unPartidoAGenerar.getEquipo1().obtenerJugadorPorIndice(1));
        Assert.assertEquals(unJugador6, unPartidoAGenerar.getEquipo1().obtenerJugadorPorIndice(2));
        Assert.assertEquals(unJugador3, unPartidoAGenerar.getEquipo1().obtenerJugadorPorIndice(3));
        Assert.assertEquals(unJugador2, unPartidoAGenerar.getEquipo1().obtenerJugadorPorIndice(4));
	}
	
	private void cargarInscripciones(Partido partido){
		partido.agregarInscripcion(unaInscripcionEstandarDePrueba1);
		partido.agregarInscripcion(unaInscripcionEstandarDePrueba2);
		partido.agregarInscripcion(unaInscripcionEstandarDePrueba3);
		partido.agregarInscripcion(unaInscripcionEstandarDePrueba4);
		partido.agregarInscripcion(unaInscripcionEstandarDePrueba5);
		partido.agregarInscripcion(unaInscripcionEstandarDePrueba6);
		partido.agregarInscripcion(unaInscripcionEstandarDePrueba7);
		partido.agregarInscripcion(unaInscripcionEstandarDePrueba8);
		partido.agregarInscripcion(unaInscripcionEstandarDePrueba9);
		partido.agregarInscripcion(unaInscripcionEstandarDePrueba10);
	}
	
}
