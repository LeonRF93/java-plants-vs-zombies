package pantallas;

import com.badlogic.gdx.Screen;
import com.lionstavern.pvz.PvzPrincipal;

import utilidades.Globales;

public class ScreenManager {
	
    private PvzPrincipal principal;
	
	public ScreenManager(PvzPrincipal principal) {
		this.principal = principal;
	}
	
	
	public void setPantallaInicio() {
		principal.setScreen(new PantallaInicio(principal));
	}
	
	public void disposePantallaInicio(PantallaInicio pantallaInicio) {
		pantallaInicio.dispose();
	}
	
	public void setMenu() {
		principal.setScreen(new Menu(principal));
	}
	
	public void disposeMenu(Menu menu) {
		menu.dispose();
	}
	
	public void setPartida() {
		principal.setScreen(new Partida(principal));
		Globales.jardin.cantidadZombies = 0;
	}
	
	public void disposePartida(Partida partida) {
		partida.dispose();
	}

}
