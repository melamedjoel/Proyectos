package futbol_5_1;

import java.time.LocalDate;
import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;

import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;

public class JugadorTest {

	private Jugador unJugador;
	private Notificador notifier;
	private Jugador unJugadorConNotifierReal;
	private Partido unPartidoReal;
	
	@Mock
	Jugador unJugador1;
	@Mock
	Jugador unJugador2;
	@Mock
	InscripcionEstandar unaInscripcion;
	@Mock
	Partido unPartido;
	@Mock
	Partido otroPartido;
	
	@Before
	public void setUp() throws Exception {
		MockitoAnnotations.initMocks(this);
		notifier = mock(Notificador.class);
		Notificador notifierReal = new Notificador();
		unPartidoReal = new Partido("Palermo", LocalDate.now());

		unJugador = new Jugador("Jugador1", notifier);
		unJugadorConNotifierReal = new Jugador("Jugador1", notifierReal);
		unJugadorConNotifierReal.agregarAmigo(unJugador1);
		unJugadorConNotifierReal.agregarAmigo(unJugador2);
	}

	@After
	public void tearDown() throws Exception {
	}

	@Test
	public void testCrearJugadorOk() {
		Assert.assertTrue((unJugador.getNombre() == "Jugador1"));
	}
	
	@Test
	public void testCrearJugadorFail() {
		Assert.assertFalse((unJugador.getNombre() == "Jugador2"));
	}
	
	@Test
	public void testInscribirAPartidoFail() {
		Mockito.doReturn(true).when(unPartido).llenoDeConfirmados();
		Assert.assertFalse((unJugador.inscribirAPartido(unPartido, unaInscripcion)));
	}
	
	@Test
	public void testInscribirAPartidoOk() {
		Mockito.doReturn(false).when(unPartido).llenoDeConfirmados();
		Mockito.doReturn(true).when(unaInscripcion).permitirInscripcion(unPartido);
		Assert.assertTrue((unJugador.inscribirAPartido(unPartido, unaInscripcion)));
	}
	
	@Test
	public void testDarseDeBajaConInfraccionOk(){
		Mockito.doReturn(true).when(unPartido).removerJugador(unJugador);
		Assert.assertTrue(unJugador.darseDeBaja(unPartido, "motivo"));
		verify(notifier).generarInfraccion(unJugador, "motivo"); //esto verifica que haya llamado UNA vez al sistema de infracciones
	}
	
	@Test 
	public void testDarseDeBajaConReemplazanteOk(){
		Mockito.doReturn(unaInscripcion).when(unPartido).inscripcionDelJugador(unJugador);
		Assert.assertTrue(unJugador.darseDeBaja(unPartido, unJugador2));
	}
	
	@Test
	public void testDarseDeBajaConReemplazanteFail() throws Exception{
		//unJugador no pertenece a unPartido
		unJugador.darseDeBaja(unPartido, unJugador2);
	}
	
	@Test
	public void testNotificarAmigosOk(){
		unJugador.notificarInscripcionAmigos();
		verify(notifier).notificarInscripcionAmigos(unJugador.getAmigos()); //esto verifica que haya llamado UNA vez al enviador de mails
	}
	
	@Test
	public void testCalificadoNoParticipoDePartido() throws Exception {
		Mockito.doThrow(new RuntimeException()).when(unJugador1).calificar(unPartido, unJugador2, 9, "Es muy bueno!");
	}
	
	@Test
	public void testCalificarJugadorOk() throws Exception {
		Mockito.doReturn(true).when(unPartido).participoElJugador(unJugador);
		Mockito.doReturn(true).when(unPartido).participoElJugador(unJugadorConNotifierReal); //necesito un jugador real para que maneje su colección de calificaciones
		unJugador.calificar(unPartido, unJugadorConNotifierReal, 3, "Es un perro");
		Assert.assertEquals(1, unJugadorConNotifierReal.getCalificaciones().size());
	}

	@Test
	public void testRecomendadoNoEsAmigo() throws Exception {
		Jugador jugadorNuevo = new Jugador("JugadorNoAmigo");
		Mockito.doThrow(new RuntimeException()).when(unJugador1).proponerAmigoAPartido(unPartido, jugadorNuevo);
	}
	
	@Test
	public void testRecomendarJugadorOk() throws Exception {
		unJugadorConNotifierReal.proponerAmigoAPartido(unPartidoReal, unJugador2);
		Assert.assertEquals(1, unPartidoReal.cantidadMociones());
	}
	
	@Test
	public void testSetYGetHandicapOk(){
		unJugador.setHandicap(8);
		Assert.assertEquals(8, unJugador.obtenerHandicap());
	}
	
	@Test
	public void testPromedioUltimoPartidoOk() throws Exception {
		Mockito.doReturn(true).when(unPartido).participoElJugador(unJugador);
		Mockito.doReturn(true).when(unPartido).participoElJugador(unJugadorConNotifierReal); 
		unJugador.calificar(unPartido, unJugadorConNotifierReal, 3, "Es un perro");
		Assert.assertEquals(3, Math.round(unJugadorConNotifierReal.obtenerPromedioUltimoPartido()));
	}
	
	@Test
	public void testPromedioUltimosNPartidosOk() throws Exception {
		Mockito.doReturn(true).when(unPartido).participoElJugador(unJugador);
		Mockito.doReturn(true).when(unPartido).participoElJugador(unJugadorConNotifierReal); 
		Mockito.doReturn(true).when(otroPartido).participoElJugador(unJugador);
		Mockito.doReturn(true).when(otroPartido).participoElJugador(unJugadorConNotifierReal); 
		unJugador.calificar(unPartido, unJugadorConNotifierReal, 3, "Es un perro");
		unJugador.calificar(otroPartido, unJugadorConNotifierReal, 5, "Mas o menos");
		Assert.assertEquals(4, Math.round(unJugadorConNotifierReal.obtenerPromedioUltimosNPartidos(2)));
	}

}
