package futbol5.estadoPartido;

import futbol5.Partido;

public class PartidoCerrado extends EstadoPartido{

	public PartidoCerrado(Partido elPartido) {
		super(elPartido);
		// TODO Auto-generated constructor stub
	}


	public void generar()
	{
		partido.estado = new PartidoGenerado(partido);
	}
	

	public boolean validar()
	{
		return true;
	}

}
