package plantas;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.TextureRegion;

import utilidades.Rutas;

public class Lanzaguisantes extends Planta {

	private Texture sprites = new Texture(Rutas.LANZAGUISANTES_SPRITES);
	private TextureRegion iddleRegion = new TextureRegion(sprites, 0, 0, 640, 80);

	public Lanzaguisantes() {
		super("Lanzaguisantes", 100, 100, 20);
	
		super.setRecarga(RECARGA_RAPIDA);
		super.setImagen(Rutas.LANZAGUISANTES_ICONO, 37, 37);

		super.agregarAnimacion(iddleRegion, 8, 0.2f);
	}
	
}
