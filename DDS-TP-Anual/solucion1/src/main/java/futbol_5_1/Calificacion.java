package futbol_5_1;

import javax.persistence.*;

@Entity
@Table(name="tl_calificacion")
public class Calificacion{
		
	@Id  
	@GeneratedValue(strategy=GenerationType.AUTO)  
	@Column(name="ID_Calificacion")  
	private long id;
	
	private int puntaje;
	
	private String critica;
	
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "ID_Jugador", nullable = false)
	private Jugador jugador;
	
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "ID_Partido", nullable = false)
	private Partido partido;
	
	public Calificacion(){
		//Creo un constructor que no hace nada por restricción tecnológica de Hibernate,
		//ya que las clases deben tener un constructor público sin argumentos
	}
	
	public Calificacion(int puntaje, String critica, Jugador jugador, Partido partido) {
		this.setPuntaje(puntaje);
		this.setCritica(critica);
		this.setJugador(jugador);
		this.setPartido(partido);
	}	
	
	int getPuntaje() {
		return puntaje;
	}
	
	private void setPuntaje(int puntaje) {
		this.puntaje = puntaje;
	}
	
	@SuppressWarnings("unused")
	private String getCritica() {
		return critica;
	}
	private void setCritica(String critica) {
		this.critica = critica;
	}

	@SuppressWarnings("unused")
	private long getId() {
		return id;
	}

	@SuppressWarnings("unused")
	private void setId(long id) {
		this.id = id;
	}
	public Jugador getJugador() {
		return jugador;
	}
	public void setJugador(Jugador jugador) {
		this.jugador = jugador;
	}
	public Partido getPartido() {
		return partido;
	}
	public void setPartido(Partido partido) {
		this.partido = partido;
	}
	
	
	
	
}
