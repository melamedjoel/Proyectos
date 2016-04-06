package futbol5;

import futbol5.excepciones.BusinessException;
import futbol5.ordenamiento.CriterioOrdenamiento;
import futbol5.ordenamiento.OrdenamientoPorHandicap;
import futbol5.utilitarios.Lists;
import futbol5.estadoPartido.*;

import java.util.ArrayList;
import java.util.List;

public class Partido {

	private List<Jugador> inscriptos;
	private Equipo equipo1;
	private Equipo equipo2;
	public EstadoPartido estado;
	private CriterioOrdenamiento criterioOrdenamiento;
	//private int distribucionEquipos; // 5 es par/impar, 16 = 1,4,5,8,9 vs. 2,3,6,7,10 -> se deja de usar por code smell arreglado

	public Partido() {
		inscriptos = new ArrayList<Jugador>();
		estado = new PartidoAbierto(this);
		//distribucionEquipos = 5; // par/impar -> se deja de usar por code smell arreglado
		criterioOrdenamiento = new OrdenamientoPorHandicap();
	}

	public void generarEquipos(ArrayList<Integer> indicesEquipo1, ArrayList<Integer> indicesEquipo2) {
		if (this.validarInscripcion() == false) {
			throw new BusinessException("Hubo un error");
		}
		this.distribuirEquipos(this.ordenarEquipos(), indicesEquipo1, indicesEquipo2);
		estado.generar();
	}

	private boolean validarInscripcion() {
		if (inscriptos.size() < 10) {
			return false;
		}
		return estado.validar();
	}

	private void distribuirEquipos(List<Jugador> jugadores, ArrayList<Integer> indicesEquipo1, ArrayList<Integer> indicesEquipo2) {
		equipo1 = new Equipo();
		equipo2 = new Equipo();
		/*if (distribucionEquipos == 5) {
			equipo1.setJugadores(Lists.newArrayList(jugadores.get(0),jugadores.get(2),jugadores.get(4),jugadores.get(6),jugadores.get(8)));
			
			equipo2.setJugadores(Lists.newArrayList(jugadores.get(1),jugadores.get(3),jugadores.get(5),jugadores.get(7),jugadores.get(9)));
		} else {
			// distribucionEquipos == 16 que ordena de esta manera
			equipo1.setJugadores(Lists.newArrayList(jugadores.get(0),jugadores.get(3),jugadores.get(4),jugadores.get(7),jugadores.get(8)));
			
			equipo2.setJugadores(Lists.newArrayList(jugadores.get(1),jugadores.get(2),jugadores.get(5),jugadores.get(6),jugadores.get(9)));
		}*/
		
		//CODE SMELL:
		//se observa un DUPLICATED CODE ya que en cada if realiza la misma accion cambiando los indices	
		//Tambien se ve un poquito de PRIMITIVE OBSESSION, ya que utiliza tipos de datos primitivos para separar 
		//los dos tipos distintos de indices.
		//Lo resolvemos de la siguiente manera: obtenemos los indices de los equipos por parametro y los seteamos
		
	
		equipo1.setJugadores(Lists.newArrayList(jugadores.get(indicesEquipo1.get(0)),jugadores.get(indicesEquipo1.get(1)),jugadores.get(indicesEquipo1.get(2)),jugadores.get(indicesEquipo1.get(3)), jugadores.get(indicesEquipo1.get(4))));
		equipo2.setJugadores(Lists.newArrayList(jugadores.get(indicesEquipo2.get(0)),jugadores.get(indicesEquipo2.get(1)),jugadores.get(indicesEquipo2.get(2)),jugadores.get(indicesEquipo2.get(3)), jugadores.get(indicesEquipo2.get(4))));
		
	}

	public List<Jugador> ordenarEquipos() {
		return criterioOrdenamiento.ordenar(this);
	}

	void inscribir(Jugador jugador) {
		if (inscriptos.size() < 10) {
			this.inscriptos.add(jugador);
		} else {
			if (this.hayAlgunJugadorQueCedaLugar()) {
				this.inscriptos.remove(this.jugadoresQueCedenLugar().get(0));
				this.inscriptos.add(jugador);
			} else {
				throw new BusinessException("No hay más lugar");
			}
		}
	}

	//CODE SMELL: Dupicated Code
	//En esta clase Partido, se podía observar como los métodos 'hayAlgunJugadorQueCedaLugar'
	//y 'jugadorQueCedeLugar' repetían la misma validación, en la cual, por cada jugador
	//inscripto del partido, le preguntaban si 'dejaLugarAOtro'. En lo único que diferían era
	//en que uno devolvía un boolean y el otro devolvía la lista de esos jugadores.
	//Por eso agregué el método 'jugadoresQueCedenLugar' que contiene la lógica con la validación
	//para que estos dos métodos la llamen.
	
	private List<Jugador> jugadoresQueCedenLugar() {
		List<Jugador> jugadores = new ArrayList<Jugador>();
		for (Jugador inscripto : inscriptos) {
			if (inscripto.dejaLugarAOtro()) {
				jugadores.add(inscripto);
			}
		}
		return jugadores;
	}
	
	private boolean hayAlgunJugadorQueCedaLugar() {
		//Comento este bloque de código porque se deja de utilizar gracias al code smell arreglado
		/*
		for (Jugador inscripto : inscriptos) {
			if(inscripto.dejaLugarAOtro()){
				return true;
			}
		}
		return false;
		*/
		if (jugadoresQueCedenLugar().size() > 0) {
			return true;
		}
		return false;
	}
	
	private Jugador jugadorQueCedeLugar() {
		if (!hayAlgunJugadorQueCedaLugar()) {
			return null;
		}
		
		//Comento este bloque de código porque se deja de utilizar gracias al code smell arreglado
		/*
		List<Jugador> jugadores=new ArrayList<Jugador>();
		for (Jugador inscripto : inscriptos) {
			if(inscripto.dejaLugarAOtro()){
				jugadores.add(inscripto);
			}
		}		
		
		return jugadores.get(0);
		*/
		return this.jugadoresQueCedenLugar().get(0);
	}

	public void cerrar() {
		estado.cerrar();
	}

	public List<Jugador> getInscriptos() {
		return inscriptos;
	}

	public void setCriterioOrdenamiento(CriterioOrdenamiento criterioOrdenamiento) {
		this.criterioOrdenamiento = criterioOrdenamiento;
	}

	//public void setDistribucionEquipos(int distribucionEquipos) {
		//this.distribucionEquipos = distribucionEquipos;
	//} -> se deja de usar por code smell arreglado

	public Equipo getEquipo1() {
		return equipo1;
	}

	public Equipo getEquipo2() {
		return equipo2;
	}
	
}
