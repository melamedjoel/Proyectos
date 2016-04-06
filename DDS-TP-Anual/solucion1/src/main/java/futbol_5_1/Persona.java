package futbol_5_1;

import javax.persistence.*;

import org.uqbar.commons.model.Entity;


@SuppressWarnings("serial")
@Table(name = "tl_persona")
@Inheritance(strategy=InheritanceType.JOINED)
@javax.persistence.Entity
public class Persona extends Entity {
	
	@Id
	@GeneratedValue(strategy=GenerationType.AUTO)  
	@Column(name="ID_Persona")
	private Long id;
	
	private String nombre;

	public String getNombre() {
		return nombre;
	}

	public void setNombre(String nombre) {
		this.nombre = nombre;
	}
}
