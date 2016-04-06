package futbol5.inscripcion;

import futbol5.inscripcion.CriterioInscripcion;

public class ModoSolidario implements CriterioInscripcion {

	public String toString() {
		return "Solidario";
	}

	@Override
	public boolean dejaLugarAOtro() {
		return true;
	}
	
}
