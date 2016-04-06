package futbol5.ordenamiento;

import java.util.ArrayList;
import java.util.List;
import futbol5.Jugador;

public class OrdenamientoMix extends CriterioOrdenamiento {
	
	List<CriterioOrdenamiento> criterios;
	
	public OrdenamientoMix() {
		criterios = new ArrayList<CriterioOrdenamiento>();
	}
	
	/*
		Codesmell detectado: Duplicated Code.
		Clases afectadas: OrdenamientoCalificacionUltimos2Partidos, OrdenamientoMix, OrdenamientoPorHandicap
		Explicación: Como se ve en el método "ordenar" de las tres clases afectadas, este método presenta exactamente la misma responsabilidad. 
		Considerando que la terna de clases heredan de una misma clase (CriterioOrdenamiento), se decide definir dicho método
		en esta superclase. El principal inconveniente presentado, es que al ser una interfaz, no permite especificar responsabilidades (solo la 
		firma de los métodos y no el cuerpo de estos). Para poder solucionar esto, se decide que la interfaz CriterioOrdenamiento, ahora pase a 
		ser una clase abstracta para así poder definirle comportamiento y eliminar el método "ordenar" de las tres sublcases ya que 
		son innecesarios.
	*/
	
	public void addCriterio(CriterioOrdenamiento criterio) {
		criterios.add(criterio);
	}
	
	public Double calcularValor(Jugador jugador) {
		return criterios.stream().mapToDouble(criterio -> criterio.calcularValor(jugador)).sum();
	}
	
}
