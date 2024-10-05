package zombies;

import com.badlogic.gdx.audio.Sound;

import utilidades.Render;

public class ZombieBasico extends Zombie {
	
	public ZombieBasico() {
		super("Zombie", 50, 100);
		
		super.setRecarga(super.RECARGA_PRECOZ);
		super.setImagen("img/zombies/zombie.png", 100, 100);
		super.setAnimacion("img/zombies/zombie_sprites.png", 7, 0.2f);
		super.disponibleAlInicio(); // lo pongo para q sea mas facil testear nomas
	}
	
}
