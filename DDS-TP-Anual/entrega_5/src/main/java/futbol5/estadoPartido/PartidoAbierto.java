package futbol5.estadoPartido;

import futbol5.Partido;

public class PartidoAbierto extends EstadoPartido{

	public PartidoAbierto(Partido elPartido) {
		super(elPartido);
		// TODO Auto-generated constructor stub
	}


	public void cerrar()
	{
		partido.estado = new PartidoCerrado(partido);
	}

}

