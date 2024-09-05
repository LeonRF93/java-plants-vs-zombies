package recursos;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.utils.ScreenUtils;
import com.lionstavern.pvz.PvzPrincipal;

public class Render {
	
	// Constantes
	public static int ANCHO = 960;
	public static int ALTO = 540;
	
	// Otros
	public static SpriteBatch batch;

	
	public static void limpiarPantalla(float r,  float g, float b, float a) {
		ScreenUtils.clear(r, g, b, a);
	}
	
	public static float getDeltaTime() {
		return Gdx.graphics.getDeltaTime();
	}
}
