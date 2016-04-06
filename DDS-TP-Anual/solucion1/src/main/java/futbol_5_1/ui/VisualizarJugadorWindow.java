package futbol_5_1.ui;

import org.uqbar.arena.actions.MessageSend;
import org.uqbar.arena.aop.windows.TransactionalDialog;
import org.uqbar.arena.bindings.NotNullObservable;
import org.uqbar.arena.bindings.Transformer;
import org.uqbar.arena.layout.ColumnLayout;
import org.uqbar.arena.layout.HorizontalLayout;
import org.uqbar.arena.widgets.Button;
import org.uqbar.arena.widgets.Label;
import org.uqbar.arena.widgets.Panel;
import org.uqbar.arena.widgets.TextBox;
import org.uqbar.arena.widgets.tables.Column;
import org.uqbar.arena.widgets.tables.Table;
import org.uqbar.arena.windows.Dialog;
import org.uqbar.arena.windows.SimpleWindow;
import org.uqbar.arena.windows.WindowOwner;

import futbol_5_1.*;

public class VisualizarJugadorWindow extends TransactionalDialog<Jugador> {

	public VisualizarJugadorWindow(WindowOwner owner, Jugador model) {
		super(owner, model);
	}
	
	@Override
	protected void createFormPanel(Panel mainPanel) {
		Panel form = new Panel(mainPanel);
		form.setLayout(new ColumnLayout(2));
		
		this.setTitle("Visualización de datos: " + this.getModelObject().getNombre());
		
		new Label(form).setText("Nombre").setForeground(this.getModelObject().colorJugador());
		new Label(form).setForeground(this.getModelObject().colorJugador()).bindValueToProperty("nombre");

		new Label(form).setText("Apodo").setForeground(this.getModelObject().colorJugador());
		new Label(form).setForeground(this.getModelObject().colorJugador()).bindValueToProperty("apodo");
		
		new Label(form).setText("Handicap").setForeground(this.getModelObject().colorJugador());
		new Label(form).setForeground(this.getModelObject().colorJugador()).bindValueToProperty("handicap");
		
		new Label(form).setText("Cantidad partidos jugados").setForeground(this.getModelObject().colorJugador());
		new Label(form).setForeground(this.getModelObject().colorJugador()).bindValueToProperty("cantidadPartidosJugados");
		
		new Label(form).setText("Promedio ultimo partido").setForeground(this.getModelObject().colorJugador());
		new Label(form).setForeground(this.getModelObject().colorJugador()).bindValueToProperty("promedioUltimoPartido");
		
		new Label(form).setText("Promedio de todos los partidos jugados").setForeground(this.getModelObject().colorJugador());
		new Label(form).setForeground(this.getModelObject().colorJugador()).bindValueToProperty("promedioTodosLosPartidos");
		
		new Label(form).setText("Fecha de nacimiento").setForeground(this.getModelObject().colorJugador());
		new Label(form).setForeground(this.getModelObject().colorJugador()).bindValueToProperty("fechaNacimientoFormatted");
		
		createResultsGrid(mainPanel);
		createPenaltiesGrid(mainPanel);
	}

	@Override
	protected void addActions(Panel actions) {
		new Button(actions) //
			.setCaption("Volver")
			.onClick(new MessageSend(this, "cancel"));
	}
	
	protected void createResultsGrid(Panel mainPanel) {
		Table<Jugador> table = new Table<Jugador>(mainPanel, Jugador.class);
		table.setHeigth(200);
		table.setWidth(450);

		table.bindItemsToProperty("amigos");

		this.describeResultsGrid(table);
	}
	
	private void createPenaltiesGrid(Panel mainPanel) {
		Table<Infraccion> table = new Table<Infraccion>(mainPanel, Infraccion.class);
		table.setHeigth(200);
		table.setWidth(450);

		table.bindItemsToProperty("infracciones");

		this.describePenaltiesGrid(table);
	}

	protected void describeResultsGrid(Table<Jugador> table) {
		table = JugadorGridHelper.describeResultsGrid(table);
	}
	
	private void describePenaltiesGrid(Table<Infraccion> table) {
		new Column<Infraccion>(table)
			.setTitle("Fecha y hora")
			.setFixedSize(115)
			.bindContentsToProperty("fechaFormatted");

		new Column<Infraccion>(table)
			.setTitle("Motivo")
			.setFixedSize(350)
			.bindContentsToProperty("motivo");
	}
}

