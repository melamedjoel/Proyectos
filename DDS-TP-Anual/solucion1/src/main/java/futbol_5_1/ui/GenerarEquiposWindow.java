package futbol_5_1.ui;

import java.util.ArrayList;

import org.uqbar.arena.actions.MessageSend;
import org.uqbar.arena.bindings.NotNullObservable;
import org.uqbar.arena.bindings.Transformer;
import org.uqbar.arena.layout.ColumnLayout;
import org.uqbar.arena.layout.HorizontalLayout;
import org.uqbar.arena.widgets.Button;
import org.uqbar.arena.widgets.CheckBox;
import org.uqbar.arena.widgets.Label;
import org.uqbar.arena.widgets.Panel;
import org.uqbar.arena.widgets.RadioSelector;
import org.uqbar.arena.widgets.TextBox;
import org.uqbar.arena.widgets.tables.Column;
import org.uqbar.arena.widgets.tables.Table;
import org.uqbar.arena.windows.Dialog;
import org.uqbar.arena.windows.SimpleWindow;
import org.uqbar.arena.windows.WindowOwner;

import futbol_5_1.Jugador;


public class GenerarEquiposWindow extends SimpleWindow<GenerarEquipos> {

	public GenerarEquiposWindow(WindowOwner parent) {
		super(parent, new GenerarEquipos());
		//this.getModelObject().getAll();
	}

	@Override
	protected void createMainTemplate(Panel mainPanel) {
		this.setTitle("Generador de Equipos");
		this.setTaskDescription("Generador de Equipos");
		super.createMainTemplate(mainPanel);
		this.createGrid(mainPanel);
		
		this.createGridActions(mainPanel);
	}

	// *************************************************************************
	// * FORMULARIO DE GENERACION
	// *************************************************************************

	@Override
	protected void createFormPanel(Panel mainPanel) {
		Panel critFormPanel = new Panel(mainPanel);
		critFormPanel.setLayout(new ColumnLayout(2));

		new Label(critFormPanel).setText("Criterio de seleccion");
		new Label(critFormPanel).setText("");
		
		RadioSelector<ArrayList<Integer>> radioSelectorOrdenamiento = new RadioSelector<>(mainPanel);
	    radioSelectorOrdenamiento.bindValueToProperty("criterioSeleccionado");
	    radioSelectorOrdenamiento.bindItemsToProperty("criterios");

		Panel ordFormPanel = new Panel(mainPanel);
		ordFormPanel.setLayout(new ColumnLayout(2));
		
		new Label(ordFormPanel).setText("Criterio de ordenamiento");
		new Label(ordFormPanel).setText("");
		
		new Label(ordFormPanel).setText("Por hándicap");
		new CheckBox(ordFormPanel).bindValueToProperty("handicap");
		
		new Label(ordFormPanel).setText("Por promedio de notas del último partido");
		new CheckBox(ordFormPanel).bindValueToProperty("ultPartido");
		
		new Label(ordFormPanel).setText("Por promedio de notas de los últimos n partidos");
		new CheckBox(ordFormPanel).bindValueToProperty("numPartidos");
				
		new Label(ordFormPanel).setText("Ingrese el valor de los n partidos");
		TextBox numPartidos = new TextBox(ordFormPanel);
		numPartidos.setWidth(20);
		numPartidos.bindEnabledToProperty("numPartidos");
		numPartidos.bindValueToProperty("cantPartidosParaPromedio");
		
		this.getModelObject().clear();
	}

	@Override
	protected void addActions(Panel actionsPanel) {
		new Button(actionsPanel)
			.setCaption("Generar")
			.onClick(new MessageSend(this.getModelObject(), "generate"))
			.setAsDefault();
		
		new Button(actionsPanel) //
			.setCaption("Limpiar")
			.onClick(new MessageSend(this.getModelObject(), "clear"));
	
		new Button(actionsPanel) //
			.setCaption("Cerrar")
			.onClick(new MessageSend(this, "close"));
	}

	// *************************************************************************
	// ** RESULTADOS DE LA GENERACION
	// *************************************************************************

	protected void createGrid(Panel mainPanel) {
		
		Panel gridPanel = new Panel(mainPanel);
		gridPanel.setLayout(new ColumnLayout(2));
		
		new Label(gridPanel).setText("Equipo numero 1");
		new Label(gridPanel).setText("Equipo numero 2");
		
		Table<Jugador> table = new Table<Jugador>(gridPanel, Jugador.class);
		table.setHeigth(200);
		table.setWidth(300);
		table.bindItemsToProperty("equipo1.jugadores");

		Table<Jugador> table2 = new Table<Jugador>(gridPanel, Jugador.class);
		table2.setHeigth(200);
		table2.setWidth(300);
		table2.bindItemsToProperty("equipo2.jugadores");
		
		table.bindValueToProperty("jugadorSeleccionadoEquipo1");
		table2.bindValueToProperty("jugadorSeleccionadoEquipo2");
		
		this.describeResultsGrid(table);
		this.describeResultsGrid(table2);

	}

	protected void describeResultsGrid(Table<Jugador> table) {
		new Column<Jugador>(table)
			.setTitle("Nombre")
			.setFixedSize(150)
			.bindContentsToProperty("nombre");

		new Column<Jugador>(table)
			.setTitle("Apodo")
			.setFixedSize(100)
			.bindContentsToProperty("apodo");
	}

	protected void createGridActions(Panel mainPanel) {
		
		Panel actionsPanel = new Panel(mainPanel);
		actionsPanel.setLayout(new HorizontalLayout());
		
		Button editJugador1 = new Button(actionsPanel);
		editJugador1.setCaption("Visualizar datos del jugador del equipo 1");
		editJugador1.onClick(new MessageSend(this, "visualizarJugadorEquipo1"));
		
		Button editJugador2 = new Button(actionsPanel);
		editJugador2.setCaption("Visualizar datos del jugador del equipo 2");
		editJugador2.onClick(new MessageSend(this, "visualizarJugadorEquipo2"));
		
		// Deshabilitar los botones si no hay ningún elemento seleccionado en la grilla.
		NotNullObservable jugador1Seleccionado = new NotNullObservable("jugadorSeleccionadoEquipo1");
		editJugador1.bindEnabled(jugador1Seleccionado);
		
		NotNullObservable jugador2Seleccionado = new NotNullObservable("jugadorSeleccionadoEquipo2");
		editJugador2.bindEnabled(jugador2Seleccionado);
	}
	
	public void visualizarJugadorEquipo1() {
		this.openDialog(new VisualizarJugadorWindow(this, this.getModelObject().getJugadorSeleccionadoEquipo1()));
	}
	
	public void visualizarJugadorEquipo2() {
		this.openDialog(new VisualizarJugadorWindow(this, this.getModelObject().getJugadorSeleccionadoEquipo2()));
	}
	
	protected void openDialog(Dialog<?> dialog) {
		dialog.onAccept(new MessageSend(this.getModelObject(), "generate"));
		dialog.open();
	}
	
}
