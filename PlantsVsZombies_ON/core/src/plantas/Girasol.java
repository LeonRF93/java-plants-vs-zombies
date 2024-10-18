package plantas;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import utilidades.Rutas;

public class Girasol extends Planta {
	
	 private Texture sprites = new Texture(Rutas.GIRASOL_SPRITES);
	 private TextureRegion iddleRegion = new TextureRegion(sprites, 0, 0, 800, 80);

	public Girasol() {
		super("Girasol", 50, 100, 0);
		
		super.setRecarga(RECARGA_RAPIDA);
		super.setImagen(Rutas.GIRASOL_ICONO, 37, 37);
		
		super.agregarAnimacion(iddleRegion, 10, 0.2f);
		super.disponibleAlInicio();
	}
	
}
