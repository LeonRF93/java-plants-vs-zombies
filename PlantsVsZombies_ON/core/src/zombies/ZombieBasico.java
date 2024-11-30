	package zombies;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.TextureRegion;

import utilidades.Animacion;
import utilidades.AnimacionAtlas;
import utilidades.Rutas;

public class ZombieBasico extends Zombie {

	private Texture sprites = new Texture(Rutas.ZOMBIE_BASICO_SPRITES);
	private TextureRegion caminarRegion = new TextureRegion(sprites, 0, 0, 700, 100);
	private TextureRegion comerRegion = new TextureRegion(sprites, 0, 100, 546, 102);
	private TextureRegion caminarUnBrazoRegion = new TextureRegion(sprites, 0, 200, 700, 100);
	private TextureRegion comerUnBrazoRegion = new TextureRegion(sprites, 0, 302, 546, 102);
	
	public ZombieBasico() {
		super("Zombie", 50, 100);
		
		super.setImagen(Rutas.ZOMBIE_BASICO_ICONO, 100, 100);
		
		super.setRecarga(super.RECARGA_PRECOZ);
		super.disponibleAlInicio(); // lo pongo para q sea mas facil testear nomas

		super.agregarAnimacion(caminarRegion, 7, 0.2f);
		super.agregarAnimacion(caminarRegion, 7, 0.2f);
		super.agregarAnimacion(comerRegion, 7, 0.15f);
		super.agregarAnimacion(caminarUnBrazoRegion, 7, 0.2f);
		super.agregarAnimacion(comerUnBrazoRegion, 7, 0.15f);



	}
	
}
