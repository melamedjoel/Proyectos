package futbol_5_1;

import java.util.ArrayList;

import org.junit.Assert;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;

public class InscripcionEstandarTest {

	private Inscripcion unaInscripcion;
	private ArrayList<Inscripcion> listaInscripcionesPartido = new ArrayList<Inscripcion>();
	private	InscripcionCondicional unaInscripcionCondicionalDePrueba;
	private InscripcionSolidaria unaInscripcionSolidariaDePrueba;
	private	Jugador unJugador = new Jugador("Pepe");
	
	@Mock
	Partido unPartido;
	
	@Before
	public void setUp() throws Exception {
		MockitoAnnotations.initMocks(this);
		unaInscripcion = new InscripcionEstandar(unJugador);
		listaInscripcionesPartido.add(unaInscripcionSolidariaDePrueba);
		listaInscripcionesPartido.add(unaInscripcionCondicionalDePrueba);
	}

	@After
	public void tearDown() throws Exception {
	}

	@Test
	public void permitirInscripcionSiNoHayInscripciones() {
		Mockito.doReturn(0).when(unPartido).cantidadDeInscripciones();
		Assert.assertTrue(unaInscripcion.permitirInscripcion(unPartido));
	}
	
	@Test
	public void permitirInscripcionSiHay10InscripcionesYHayInscrAReemplazar() {
		Mockito.doReturn(10).when(unPartido).cantidadDeInscripciones();
		Mockito.doReturn(listaInscripcionesPartido).when(unPartido).obtenerInscripcionesAReemplazar(unaInscripcion);
		Assert.assertTrue(unaInscripcion.permitirInscripcion(unPartido));
	}
	
	@Test
	public void noPermitirInscripcionSiHay10InscripcionesYNoHayInscrAReemplazar() {
		Mockito.doReturn(10).when(unPartido).cantidadDeInscripciones();
		Mockito.doReturn(new ArrayList<Inscripcion>()).when(unPartido).obtenerInscripcionesAReemplazar(unaInscripcion);
		Assert.assertFalse(unaInscripcion.permitirInscripcion(unPartido));
	}
}
