package zombies;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.TextureRegion;

import utilidades.Animacion;

public class ZombieBasico extends Zombie {
	
	private Texture sprites = new Texture("img/zombies/zombie-basico/zombie_basico.png");
	private TextureRegion caminarRegion = new TextureRegion(sprites, 0, 0, 700, 100);
	private TextureRegion comiendoRegion = new TextureRegion(sprites, 0, 100, 546, 102);
	
	public ZombieBasico() {
		super("Zombie", 50, 100);
		
		super.setRecarga(super.RECARGA_PRECOZ);
		super.setImagen("img/zombies/zombie.png", 100, 100);
		super.animacionCaminar = new Animacion(caminarRegion, 7, 0.2f);
		super.setAnimacionIddle("img/zombies/zombie-basico/zombie_basico.png", 7, 0.2f);
		super.animacionComer = new Animacion(comiendoRegion, 7, 0.15f);
		super.disponibleAlInicio(); // lo pongo para q sea mas facil testear nomas

	}
	
}
