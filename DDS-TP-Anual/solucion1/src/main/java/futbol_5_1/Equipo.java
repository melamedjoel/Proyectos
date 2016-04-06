package futbol_5_1;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;

@Entity
@Table(name="tl_equipo")
public class Equipo {
	
	@Id  
	@GeneratedValue(strategy=GenerationType.AUTO)  
	@Column(name="ID_Equipo")  
	private long id;  
	
	private String nombre;
	
	private boolean local;
	
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "ID_Partido", nullable = false)
	private Partido partido;
	
	@OneToMany(fetch = FetchType.LAZY)
	@JoinColumn(name = "ID_Equipo")
	private List<Jugador> jugadores = new ArrayList<Jugador>();

	public Long getId() {
		return id;
	}
	
	//Constructor para Hibernate
	public Equipo () {
		
	}
	
	public Equipo (String nombre, boolean local) {
		setNombre(nombre);
		setLocal(local);
	}

	public String getNombre() {
		return nombre;
	}

	public void setNombre(String nombre) {
		this.nombre = nombre;
	}

	public boolean getLocal() {
		return local;
	}

	public void setLocal(boolean local) {
		this.local = local;
	}

	public ArrayList<Jugador> getJugadores() {
		return (ArrayList<Jugador>) jugadores;
	}

	public void setJugadores(ArrayList<Jugador> jugadores) {
		this.jugadores = jugadores;
	}
	
	public void agregarJugador(Jugador unJugador) {
		this.jugadores.add(unJugador);
	}

	public Jugador obtenerJugadorPorIndice(int indice) {
		return jugadores.get(indice);
	}

	public Partido getPartido() {
		return partido;
	}

	public void setPartido(Partido partido) {
		this.partido = partido;
	}
}
