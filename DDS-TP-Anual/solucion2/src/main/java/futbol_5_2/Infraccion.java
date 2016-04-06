package futbol_5_2;

import java.util.Date;

public class Infraccion {
	private Jugador _jugador;
	private String _motivo;
	private Date _fecha;
	
	public Jugador getJugador() {
		return _jugador;
	}
	public void setJugador(Jugador jugador) {
		this._jugador = jugador;
	}
	public String getMotivo() {
		return _motivo;
	}
	public void setMotivo(String motivo) {
		this._motivo = motivo;
	}
	public Date getFecha() {
		return _fecha;
	}
	public void setFecha(Date fecha) {
		this._fecha = fecha;
	}
	
}
