package futbol_5_1;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToOne;
import javax.persistence.Table;

public class Rechazo {
	 
	private long id;
	
	private Mocion mocion;
	
	private String motivo;
	
	private Date fecha;
	
	//Constructor para Hibernate
	public Rechazo () {
		
	}
	
	public Long getId() {
		return id;
	}
	
	public String getMotivo() {
		return motivo;
	}
	public void setMotivo(String motivo) {
		this.motivo = motivo;
	}
	public Date getFecha() {
		return fecha;
	}
	public void setFecha(Date fecha) {
		this.fecha = fecha;
	}
	public Mocion getMocion() {
		return mocion;
	}
	public void setMocion(Mocion mocion) {
		this.mocion = mocion;
	}
}
