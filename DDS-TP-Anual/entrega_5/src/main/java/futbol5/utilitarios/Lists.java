package futbol5.utilitarios;

import java.util.ArrayList;

public class Lists {
	/** *************************************************************************
	 * CLASE AUXILIAR NO REFACTOREAR!!!!
	 ****************************************************************************/
	public static <E> ArrayList<E> newArrayList(E... objects){
		ArrayList<E> list=new ArrayList<E>();
		for (E e : objects) {
			list.add(e);
		}
		return list;
	}
	

}
