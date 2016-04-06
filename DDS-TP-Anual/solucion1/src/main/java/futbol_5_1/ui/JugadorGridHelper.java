package futbol_5_1.ui;

import org.uqbar.arena.widgets.tables.Column;
import org.uqbar.arena.widgets.tables.Table;

import futbol_5_1.Jugador;
import futbol_5_1.transformers.HandicapToColorTransformer;

public class JugadorGridHelper {
	
	private static HandicapToColorTransformer colorTransformer = new HandicapToColorTransformer();

	public static Table<Jugador> describeResultsGrid(Table<Jugador> table) {
		
		createColumn(table, "Nombre", "nombre", 150);
		createColumn(table, "Apodo", "apodo", 100);
		createColumn(table, "Handicap", "handicap", 100);
		createColumn(table, "Promedio", "promedioUltimoPartido", 116);
		
		return table;
	}
	
	private static void createColumn(Table<Jugador> table, String title, String propertyToBind, int size) {
		
		new Column<Jugador>(table)
				.setTitle(title)
				.bindContentsToProperty(propertyToBind)
				.setFixedSize(size)
				.bindBackground("handicap", colorTransformer);
	}
}
