package futbol_5_1.ui;


import java.io.Serializable;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.Date;
import java.util.List;

import org.uqbar.arena.widgets.CheckBox;
import org.uqbar.arena.widgets.Label;
import org.uqbar.commons.utils.Observable;

import scala.actors.threadpool.Arrays;
import futbol_5_1.repositorios.*;
import futbol_5_1.*;

@Observable
public class GenerarEquipos implements Serializable 
{
		private Equipo equipo1;
		private Equipo equipo2;
		private Criterio criterioSeleccionado; 
		private Boolean handicap;	
		private Boolean ultPartido;	
		private Boolean numPartidos;	
		private int cantPartidosParaPromedio;
		private Jugador jugadorSeleccionadoEquipo1;
		private Jugador jugadorSeleccionadoEquipo2;
		
		// ********************************************************
		// ** Acciones
		// ********************************************************
		
		public void generate() {
			ArrayList<Integer> indicesEquipo1 = this.criterioSeleccionado.getIndices();
			
			this.generarPorOrdenamientoDefinido(indicesEquipo1);
			
			this.equipo1 = RepositorioJugadores.getInstance().getPartido().getEquipo1();
			this.equipo2 = RepositorioJugadores.getInstance().getPartido().getEquipo2();

		}
		
		private void generarPorOrdenamientoDefinido(ArrayList<Integer> indicesEquipo1){
			ArrayList<Comparator<Jugador>> criterios = new ArrayList<Comparator<Jugador>>();
			if(this.handicap){
				criterios.add(Jugador.comparatorHandicap());
			}
			if(this.ultPartido){
				criterios.add(Jugador.comparatorAVGLastMatch());
			}
			if(this.numPartidos){
				criterios.add(Jugador.comparatorAVGLastNMatches(this.getCantPartidosParaPromedio()));
			}	
			
			RepositorioJugadores.getInstance().getPartido().generarEquiposTentativos(criterios, indicesEquipo1);
		}

		public void clear() {
			this.handicap = false;
			this.ultPartido = false;
			this.numPartidos = false;
			this.cantPartidosParaPromedio = 0;
			this.equipo1 = new Equipo("Equipo1", true);
			this.equipo2 = new Equipo("Equipo2", false);
			
			//this.getAll();
		}

		// ********************************************************
		// ** Accessors
		// ********************************************************
		
		

		public Equipo getEquipo1() {
			return this.equipo1;
		}

		public void setEquipo1(Equipo resultados) {
			this.equipo1 = resultados;
		}
		
		public Equipo getEquipo2() {
			return this.equipo2;
		}

		public void setEquipo2(Equipo resultados) {
			this.equipo2 = resultados;
		}
		
		public boolean getHandicap() {
			return this.handicap;
		}

		public void setHandicap(boolean theValue) {
			this.handicap = theValue;
		}
		
		public boolean getUltPartido() {
			return this.ultPartido;
		}

		public void setUltPartido(boolean theValue) {
			this.ultPartido = theValue;
		}
		
		public boolean getNumPartidos() {
			return this.numPartidos;
		}

		public void setNumPartidos(boolean theValue) {
			if (!theValue)
				this.setCantPartidosParaPromedio(0);
			
			this.numPartidos = theValue;
		}
		
		public Jugador getJugadorSeleccionadoEquipo1 () {
			return this.jugadorSeleccionadoEquipo1;
		}

		public void setJugadorSeleccionadoEquipo1 (Jugador jugadorSeleccionadoEquipo1) throws Exception {
			this.jugadorSeleccionadoEquipo1 = jugadorSeleccionadoEquipo1;
		}
		
		public Jugador getJugadorSeleccionadoEquipo2 () {
			return this.jugadorSeleccionadoEquipo2;
		}

		public void setJugadorSeleccionadoEquipo2 (Jugador jugadorSeleccionadoEquipo2) throws Exception {
			this.jugadorSeleccionadoEquipo2 = jugadorSeleccionadoEquipo2;
		}

		public int getCantPartidosParaPromedio() {
			
			if (getNumPartidos())
				return cantPartidosParaPromedio;
			
			return 0;
		}

		public void setCantPartidosParaPromedio(int cantPartidosParaPromedio) {
			this.cantPartidosParaPromedio = cantPartidosParaPromedio;
		}
		
		public Criterio getCriterioSeleccionado() {
			return criterioSeleccionado;
		}

		public void setCriterioSeleccionado(Criterio criterioSeleccionado) {
			this.criterioSeleccionado = criterioSeleccionado;
		}
		
		@SuppressWarnings("unchecked")
		public List<Criterio> getCriterios(){
			Criterio critPares = new Criterio();
			critPares.setTexto("Pares/Impares");
			critPares.setIndices(new ArrayList<Integer>(Arrays.asList(new Integer[]{1,3,5,7,9})));
			
			Criterio critRandom = new Criterio();
			critRandom.setTexto("Random");
			critRandom.setIndices(new ArrayList<Integer>(Arrays.asList(new Integer[]{1,4,5,8,9})));			
			
			ArrayList<Criterio> list = new ArrayList<Criterio>();
			list.add(critPares);
			list.add(critRandom);
			return list;
		}
		
		/*public void getAll() {
			this.setResultados(RepositorioJugadores.getInstance().getAll());
		}*/

}
