package utilidades;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.utils.ScreenUtils;

import jardines.Jardin;

public class Globales {

	// Pausa
	public static boolean pausaActiva;
	
	public static float volumenSfx = 1.0f;
	public static boolean musicaOn = true;
	public static boolean sfxOn = true;
	
	// Otros
	public static String plantaReiniciarCooldown = "";
	
	//Jardin
	public static Jardin jardin;
	
	// Constantes
	public static int ANCHO = 960;
	public static int ALTO = 540;
	
	// Batch
	public static SpriteBatch batch;
	
	public static void limpiarPantalla(float r,  float g, float b, float a) {
		ScreenUtils.clear(r, g, b, a);
	}
	
	// Otros
	public static float getDeltaTime() {
		return Gdx.graphics.getDeltaTime();
	}
	
}
