package plantas;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.TextureRegion;

import utilidades.Rutas;

public class Nuez extends Planta {

	private Texture sprites = new Texture(Rutas.NUEZ_SPRITES);
	private TextureRegion iddleRegion = new TextureRegion(sprites, 0, 0, 450, 90);

	public Nuez() {
		super("Nuez", 50, 500, 0);
	
		super.setRecarga(super.RECARGA_LENTA);
		super.setImagen(Rutas.NUEZ_ICONO, 100, 100);
		
		super.agregarAnimacion(iddleRegion, 5, 0.2f);
	}
	
}
