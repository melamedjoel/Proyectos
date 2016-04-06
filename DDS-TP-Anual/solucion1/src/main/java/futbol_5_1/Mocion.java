package futbol_5_1;

import javax.persistence.*;

@Entity
@Table(name="tl_mocion")
public class Mocion{
	
	@Id  
	@GeneratedValue(strategy=GenerationType.AUTO)  
	@Column(name="ID_Mocion")  
	private long id;  

	public Long getId() {
		return id;
	}
	
	@ManyToOne
	@JoinColumn(name = "ID_Persona")
	private Persona persona;
	
	private Estado estado;
	
	@ManyToOne
	@JoinColumn(name = "ID_Partido")
	private Partido partido;
	
	public Mocion(){
		//Creo un constructor que no hace nada por restricción tecnológica de Hibernate,
		//ya que las clases deben tener un constructor público sin argumentos
	}
	public Mocion(Persona unaPersona, Partido unPartido)
	{
		this.setPersona(unaPersona);
		this.setPartido(unPartido);
		this.setEstado(Estado.PENDIENTE);
	}
	
	public Persona getPersona()
	{
		return persona;
	}
	
	public void setPersona(Persona unaPersona)
	{
		persona = unaPersona;
	}

	public void aceptar() {
		this.setEstado(Estado.ACEPTADA);
	}

	public void rechazar() {
		this.setEstado(Estado.RECHAZADA);
	}
	
	@Enumerated(EnumType.ORDINAL)
	public Estado getEstado() {
		return estado;
	}

	public void setEstado(Estado estado) {
		this.estado = estado;
	}
	
	public Partido getPartido() {
		return partido;
	}
	
	public void setPartido(Partido partido) {
		this.partido = partido;
	}
	
}
