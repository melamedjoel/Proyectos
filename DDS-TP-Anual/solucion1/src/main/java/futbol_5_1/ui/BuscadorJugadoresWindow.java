package futbol_5_1.ui;

import org.uqbar.arena.actions.MessageSend;
import org.uqbar.arena.bindings.NotNullObservable;
import org.uqbar.arena.layout.ColumnLayout;
import org.uqbar.arena.layout.HorizontalLayout;
import org.uqbar.arena.widgets.Button;
import org.uqbar.arena.widgets.CheckBox;
import org.uqbar.arena.widgets.Label;
import org.uqbar.arena.widgets.Panel;
import org.uqbar.arena.widgets.TextBox;
import org.uqbar.arena.widgets.tables.Column;
import org.uqbar.arena.widgets.tables.Table;
import org.uqbar.arena.windows.Dialog;
import org.uqbar.arena.windows.SimpleWindow;
import org.uqbar.arena.windows.WindowOwner;

import futbol_5_1.*;

public class BuscadorJugadoresWindow extends SimpleWindow<BuscadorJugadores> {

	public BuscadorJugadoresWindow(WindowOwner parent) {
		super(parent, new BuscadorJugadores());
		this.getModelObject().getAll();
	}

	@Override
	protected void createMainTemplate(Panel mainPanel) {
		this.setTitle("Buscador de Jugadores");
		this.setTaskDescription("Ingrese los parámetros de búsqueda");

		super.createMainTemplate(mainPanel);

		this.createResultsGrid(mainPanel);
		this.createGridActions(mainPanel);
	}

	// *************************************************************************
	// * FORMULARIO DE BUSQUEDA
	// *************************************************************************

	@Override
	protected void createFormPanel(Panel mainPanel) {
		Panel searchFormPanel = new Panel(mainPanel);
		searchFormPanel.setLayout(new ColumnLayout(2));

		new Label(searchFormPanel).setText("Nombre del Jugador");
		new TextBox(searchFormPanel).bindValueToProperty("nombre");
		
		new Label(searchFormPanel).setText("Apodo del Jugador");
		new TextBox(searchFormPanel).bindValueToProperty("apodo");
		
		new Label(searchFormPanel).setText("Fecha de nacimiento");
		new TextBox(searchFormPanel).bindValueToProperty("fechaNacimientoFormatted");
		
		new Label(searchFormPanel).setText("Handicap del Jugador desde:");
		new TextBox(searchFormPanel).setWidth(12).bindValueToProperty("handicapDesde");
		
		new Label(searchFormPanel).setText("Handicap del Jugador hasta:");
		new TextBox(searchFormPanel).setWidth(12).bindValueToProperty("handicapHasta");
		
		new Label(searchFormPanel).setText("Promedio último partido desde:");
		new TextBox(searchFormPanel).setWidth(12).bindValueToProperty("promedioDesde");
		
		new Label(searchFormPanel).setText("Promedio último partido hasta:");
		new TextBox(searchFormPanel).setWidth(12).bindValueToProperty("promedioHasta");
		
		new Label(searchFormPanel).setText("Tiene infracciones:");
		new CheckBox(searchFormPanel).bindValueToProperty("tieneInfracciones");
		
		this.getModelObject().clear();
	}

	@Override
	protected void addActions(Panel actionsPanel) {
		new Button(actionsPanel)
			.setCaption("Buscar")
			.onClick(new MessageSend(this.getModelObject(), "search"))
			.setAsDefault();

		new Button(actionsPanel) //
			.setCaption("Limpiar")
			.onClick(new MessageSend(this.getModelObject(), "clear"));
		
		new Button(actionsPanel) //
			.setCaption("Cerrar")
			.onClick(new MessageSend(this, "close"));

	}

	// *************************************************************************
	// ** RESULTADOS DE LA BUSQUEDA
	// *************************************************************************

	protected void createResultsGrid(Panel mainPanel) {
		Table<Jugador> table = new Table<Jugador>(mainPanel, Jugador.class);
		table.setHeigth(200);
		table.setWidth(450);

		table.bindItemsToProperty("resultados");
		table.bindValueToProperty("jugadorSeleccionado");

		this.describeResultsGrid(table);
	}

	protected void describeResultsGrid(Table<Jugador> table) {		
		table = JugadorGridHelper.describeResultsGrid(table);
	}

	protected void createGridActions(Panel mainPanel) {
		Panel actionsPanel = new Panel(mainPanel);
		actionsPanel.setLayout(new HorizontalLayout());
		
		Button edit = new Button(actionsPanel);
		edit.setCaption("Visualizar datos");
		edit.onClick(new MessageSend(this, "visualizarJugador"));
		
		NotNullObservable jugadorSeleccionado = new NotNullObservable("jugadorSeleccionado");
		edit.bindEnabled(jugadorSeleccionado);
	}
	
	public void visualizarJugador() {
		this.openDialog(new VisualizarJugadorWindow(this, this.getModelObject().getJugadorSeleccionado()));
	}
	
	protected void openDialog(Dialog<?> dialog) {
		dialog.onAccept(new MessageSend(this.getModelObject(), "search"));
		dialog.open();
	}
}
