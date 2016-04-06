package futbol_5_1.ui;

import org.uqbar.arena.Application;
import org.uqbar.arena.windows.Window;

/**
 * Correr esta clase con el siguiente argument
 * 
 * -Djava.system.class.loader=com.uqbar.apo.APOClassLoader
 */
public class FutbolApplication extends Application {

	public static void main(String[] args) {
		new FutbolApplication().start();
	}

	@Override
	protected Window<?> createMainWindow() {
		return new MainWindow(this);
	}
	
}
