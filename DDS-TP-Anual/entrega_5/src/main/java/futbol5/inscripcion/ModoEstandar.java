package futbol5.inscripcion;

import futbol5.inscripcion.CriterioInscripcion;

public class ModoEstandar implements CriterioInscripcion {

	public String toString() {
		return "Estándar";
	}

	@Override
	public boolean dejaLugarAOtro() {
		return false;
	}
		
}