package jardines;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.audio.Music;
import com.badlogic.gdx.math.Vector2;

import utilidades.Imagen;
import utilidades.Render;

public class Dia extends Jardin {

	// La imagen del fondo toma el ANCHO y el ALTO del Render.
	// Le sumamos 300 al ANCHO para ajustar la imagen y que no se vea estirada
	Imagen imgDia = new Imagen("img/escenarios/dia.png", Render.ANCHO + 300, Render.ALTO);

	private Music mscDia = Gdx.audio.newMusic(Gdx.files.internal("audio/Day-Stage_.mp3"));
	private Vector2 alineacionGrilla = new Vector2(230, 380);
	
	public Dia() {
		super("Dia",5,9);
		super.inicializarObjetos(imgDia, mscDia, alineacionGrilla);
	}

}
