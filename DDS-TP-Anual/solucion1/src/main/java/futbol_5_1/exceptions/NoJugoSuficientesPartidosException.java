package futbol_5_1.exceptions;

public class NoJugoSuficientesPartidosException extends RuntimeException {
	
	private static final long serialVersionUID = 1L;

	public NoJugoSuficientesPartidosException(String message) 
	{
	    super(message);
	}
}
