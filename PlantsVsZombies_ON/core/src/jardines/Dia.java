package jardines;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.audio.Music;
import com.badlogic.gdx.math.Vector2;

import utilidades.Imagen;
import utilidades.Render;
import utilidades.Rutas;

public class Dia extends Jardin {

	// La imagen del fondo toma el ANCHO y el ALTO del Render.
	// Le sumamos 300 al ANCHO para ajustar la imagen y que no se vea estirada
	Imagen imgDia = new Imagen(Rutas.ESCENARIO_DIA, Render.ANCHO + 300, Render.ALTO);

	private Music mscDia = Gdx.audio.newMusic(Gdx.files.internal(Rutas.MUSICA_DIA));
	private Music mscDiaHorda = Gdx.audio.newMusic(Gdx.files.internal(Rutas.MUSICA_DIA_HORDA));
	private Vector2 alineacionGrilla = new Vector2(230, 380);
	
	public Dia() {
		super("Dia",5,9, 220, Render.ALTO);
		super.inicializarObjetos(imgDia, mscDia, mscDiaHorda, alineacionGrilla);
	}

}
