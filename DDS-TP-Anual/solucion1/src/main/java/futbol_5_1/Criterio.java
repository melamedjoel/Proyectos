package futbol_5_1;

import java.util.ArrayList;

public class Criterio {
	private ArrayList<Integer> indices;
	private String texto;
	
	public ArrayList<Integer> getIndices() {
		return indices;
	}
	public void setIndices(ArrayList<Integer> indices) {
		this.indices = indices;
	}
	public String getTexto() {
		return texto;
	}
	public void setTexto(String texto) {
		this.texto = texto;
	}
	@Override
	public String toString() {
		return this.texto;
	}
	
}
