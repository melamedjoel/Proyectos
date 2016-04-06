package futbol_5_1.ui;

import java.io.Serializable;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.Date;
import java.util.List;

import org.uqbar.commons.utils.Observable;

import futbol_5_1.repositorios.*;
import futbol_5_1.*;

@Observable
public class BuscadorJugadores implements Serializable{
	private String nombre;
	private String apodo;
	private Date fechaNacimiento;
	private String fechaNacimientoFormatted;
	private int handicapDesde = 0;
	private int handicapHasta = 10;
	private double promedioDesde = 0;
	private double promedioHasta = 10;
	private List<Jugador> resultados;
	private Boolean tieneInfracciones;	
	private Jugador jugadorSeleccionado;

	// ********************************************************
	// ** Acciones
	// ********************************************************
	
	public void search() {
		this.resultados = RepositorioJugadores.getInstance().search(this.nombre, this.apodo, this.fechaNacimiento, this.handicapDesde, this.handicapHasta, this.promedioDesde, this.promedioHasta, this.tieneInfracciones);
	}

	public void clear() {
		this.setNombre("");
		this.setApodo("");
		this.setFechaNacimiento(new Date());
		this.fechaNacimientoFormatted = LocalDate.now().format(DateTimeFormatter.ofPattern("dd/MM/uuuu"));
		this.setHandicapDesde(0);
		this.setHandicapHasta(10);
		this.setPromedioDesde((double) 0);
		this.setPromedioHasta((double) 10);
		
		this.getAll();
	}

	// ********************************************************
	// ** Accessors
	// ********************************************************
	
	public int getHandicapDesde() {
		return handicapDesde;
	}

	public void setHandicapDesde(int handicapDesde) {
		this.handicapDesde = handicapDesde;
	}

	public int getHandicapHasta() {
		return this.handicapHasta;
	}

	public void setHandicapHasta(int handicapHasta) {
		this.handicapHasta = handicapHasta;
	}

	public double getPromedioDesde() {
		return promedioDesde;
	}

	public void setPromedioDesde(double promedioDesde) {
		this.promedioDesde = promedioDesde;
	}

	public double getPromedioHasta() {
		return promedioHasta;
	}

	public void setPromedioHasta(double promedioHasta) {
		this.promedioHasta = promedioHasta;
	}

	public Boolean getTieneInfracciones() {
		return tieneInfracciones;
	}

	public void setTieneInfracciones(Boolean tieneInfracciones) {
		this.tieneInfracciones = tieneInfracciones;
	}

	public String getNombre() {
		return this.nombre;
	}

	public void setNombre(String nombre) {
		this.nombre = nombre;
	}

	public List<Jugador> getResultados() {
		return this.resultados;
	}

	public void setResultados(List<Jugador> resultados) {
		this.resultados = resultados;
	}

	public String getApodo() {
		return apodo;
	}

	public void setApodo(String apodo) {
		this.apodo = apodo;
	}

	public Date getFechaNacimiento() {
		return fechaNacimiento;
	}

	public void setFechaNacimiento(Date fechaNacimiento) {
		this.fechaNacimiento = fechaNacimiento;
	}	
	
	public Jugador getJugadorSeleccionado() {
		return this.jugadorSeleccionado;
	}

	public void setJugadorSeleccionado(Jugador jugadorSeleccionado) throws Exception {
		this.jugadorSeleccionado = jugadorSeleccionado;
	}
	
	public void getAll() {
		this.setResultados(RepositorioJugadores.getInstance().getAll());
	}
}
