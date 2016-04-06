package db;

import static db.EntityManagerHelper.*;
import static org.junit.Assert.*;

import java.time.LocalDate;
import java.time.Month;
import java.util.Date;
import java.util.List;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import futbol_5_1.Jugador;

public class PersistenceTest {

   @Before
   public void before() {
      beginTransaction();
   }

   @After
   public void after() {
      rollback();
   }

   @Test
   public void testCrearJugador() {
      Jugador jugador = new Jugador("Pepe", "Pepito", new Date(), 10);
      
      entityManager().persist(jugador);
   }
   
   @Test
	public List<Jugador> todos() {
		return entityManager().createQuery("from tl_jugador").getResultList();
	}
   
}