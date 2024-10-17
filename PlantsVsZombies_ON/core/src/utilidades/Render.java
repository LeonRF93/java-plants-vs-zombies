package utilidades;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.utils.ScreenUtils;
import com.lionstavern.pvz.PvzPrincipal;

public class Render {
	
	// Constantes
	public static int ANCHO = 960;
	public static int ALTO = 540;
	public static int ANCHO_MONITOR;
	public static int ALTO_MONITOR;
	
	
	// Viewports
	public static int escalaX(int valor) {
        return (Render.ANCHO_MONITOR*valor)/Render.ANCHO;
    }
    public static int escalaY(int valor) {
        return (Render.ALTO_MONITOR*valor)/Render.ALTO;
    }

    public static float fEscalaX(float valor) {
        return (float)((Render.ANCHO_MONITOR*valor)/Render.ANCHO);
    }
    
    public static float fEscalaY(float valor) {
        return (float)((Render.ALTO_MONITOR*valor)/Render.ALTO);
    }
	
    
	// Otros
	public static SpriteBatch batch;

	
	public static void limpiarPantalla(float r,  float g, float b, float a) {
		ScreenUtils.clear(r, g, b, a);
	}
	
	public static float getDeltaTime() {
		return Gdx.graphics.getDeltaTime();
	}
}
