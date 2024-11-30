package jardines;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.audio.Music;
import com.badlogic.gdx.math.Vector2;

import utilidades.Imagen;
import utilidades.Render;
import utilidades.Rutas;

public class Noche extends Jardin {

	// La imagen del fondo toma el ANCHO y el ALTO del Render.
		// Le sumamos 300 al ANCHO para ajustar la imagen y que no se vea estirada
		Imagen imgDia = new Imagen(Rutas.ESCENARIO_NOCHE, Render.ANCHO + 300, Render.ALTO);

		private Music mscDia = Gdx.audio.newMusic(Gdx.files.internal(Rutas.MUSICA_NOCHE));
		private Music mscDiaHorda = Gdx.audio.newMusic(Gdx.files.internal(Rutas.MUSICA_NOCHE_HORDA));
		private Vector2 alineacionGrilla = new Vector2(230, 380);
		
		public Noche() {
			super("Noche",5,9, 210, Render.ALTO);
			super.inicializarObjetos(imgDia, mscDia, mscDiaHorda, alineacionGrilla);
		}
	
}
