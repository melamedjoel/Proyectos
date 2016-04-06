package futbol_5_1;

import java.util.function.Predicate;

import org.junit.Assert;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;

public class InscripcionCondicionalTest {

	private InscripcionCondicional unaInscripcion;
	private	Jugador unJugador = new Jugador("Pepe");
	
	@Mock
	Partido unPartido; //mockeo por la dificultad de inicializarlo	
	
	@Mock
	Predicate<Partido> condicion; //mockeo por la dificultad de inicializarlo	
	
	@Before
	public void setUp() throws Exception {
		MockitoAnnotations.initMocks(this);
		unaInscripcion = new InscripcionCondicional(unJugador);
	}

	@After
	public void tearDown() throws Exception {
	}

	@Test
	public void permitirInscripcionSiNoHayInscripcionesYCumpleLaCondicion() {
		Mockito.doReturn(true).when(condicion).test(unPartido);
		unaInscripcion.setCondicion(condicion);
		Assert.assertTrue(unaInscripcion.permitirInscripcion(unPartido));
	}

	@Test
	public void noPermitirInscripcionSiNoHayInscripcionesYNoCumpleLaCondicion() {
		Mockito.doReturn(false).when(condicion).test(unPartido);
		unaInscripcion.setCondicion(condicion);
		Assert.assertFalse(unaInscripcion.permitirInscripcion(unPartido));
	}
	
	@Test
	public void noPermitirInscripcionSiHay10Inscripciones() {
		Mockito.doReturn(10).when(unPartido).cantidadDeInscripciones();
		Assert.assertFalse(unaInscripcion.permitirInscripcion(unPartido));
	}

}
