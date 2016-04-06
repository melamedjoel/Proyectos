package futbol_5_1.ui;

import org.uqbar.arena.actions.MessageSend;
import org.uqbar.arena.layout.ColumnLayout;
import org.uqbar.arena.widgets.Button;
import org.uqbar.arena.widgets.Panel;
import org.uqbar.arena.windows.SimpleWindow;
import org.uqbar.arena.windows.WindowOwner;

public class MainWindow extends SimpleWindow<ViewModelMain> {
	
	public MainWindow(WindowOwner parent) {
		super(parent, new ViewModelMain());
	}

	@Override
	protected void createFormPanel(Panel mainPanel) {
		this.setTitle("Home - Organizador futbol 5");
		
		Panel form = new Panel(mainPanel);
		form.setLayout(new ColumnLayout(2));
		
		Button buscadorJugadores = new Button(form);
		buscadorJugadores.setCaption("Buscador de jugadores");
		buscadorJugadores.onClick(() -> this.abrirBuscador());
		
		Button generadorEquipos = new Button(form);
		generadorEquipos.setCaption("Generador de equipos");
		generadorEquipos.onClick(() -> this.abrirGenerador());
	}

	@Override
	protected void addActions(Panel actionsPanel) {
		// TODO Auto-generated method stub
		
	}
	
	public void abrirBuscador() {
		BuscadorJugadoresWindow window = new BuscadorJugadoresWindow(this);
		window.open();
	}
	
	public void abrirGenerador() {
		GenerarEquiposWindow window = new GenerarEquiposWindow(this);
		window.open();
	}
}
