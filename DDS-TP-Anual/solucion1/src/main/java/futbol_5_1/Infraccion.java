package futbol_5_1;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

@Entity
@Table(name = "tl_infraccion")
public class Infraccion {
	
	@Id  
	@GeneratedValue  
	@Column(name="ID_Infraccion")  
	private long id;
	
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "ID_Jugador", nullable = false)
	private Jugador jugador;
	
	private String motivo;
	
	private LocalDate fecha;
	
	//Constructor para Hibernate
	public Infraccion () {
		
	}
	
	public Long getId() {
		return id;
	}
	
	public Jugador getJugador() {
		return jugador;
	}
	public void setJugador(Jugador jugador) {
		this.jugador = jugador;
	}
	public String getMotivo() {
		return motivo;
	}
	public void setMotivo(String motivo) {
		this.motivo = motivo;
	}
	public LocalDate getFecha() {
		return fecha;
	}
	public void setFecha(LocalDate fecha) {
		this.fecha = fecha;
	}
	
	public String getFechaFormatted() {
		return this.getFecha().format(DateTimeFormatter.ofPattern("dd/MM/uuuu"));
	}
}
