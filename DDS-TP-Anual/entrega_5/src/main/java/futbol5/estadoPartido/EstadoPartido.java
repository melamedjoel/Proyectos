package futbol5.estadoPartido;

import futbol5.Partido;

public abstract class EstadoPartido {
	// Ésta clase abstracta es la padre de PartidoAbierto, PartidoCerrado y PartidoGenerado.
	// Está creada para cosificar los estados, ya que cada uno tenía un comportamiento particular
	// y valía la pena cosificarlos. 
	// El Code Smell que habia era Primitive Obsession, ya que para diferenciar los estados se utilizaba
	// un string. Y cada vez que se tenia que hacer algo segun el comportamiento, se preguntaba por ese String.
	// Algo que no es muy práctico y eficiente. Ahora estan mejor divididas las responsabilidades.
	public Partido partido;
	
	public EstadoPartido (Partido elPartido)
	{
		partido = elPartido;
	}
	
	
	public void cerrar()
	{
		
	}
	
	public void generar()
	{
		
	}
	
	public boolean validar()
	{
		return false;
	}
}
