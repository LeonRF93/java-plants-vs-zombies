package zombies;

import recursos.Render;

public class ZombieBasico extends Zombie implements Caminable {
	
	public float tiempoCaminar = 0f;
	
	public ZombieBasico() {
		super("Zombie", 50, 100, 0);
	
		super.setRecarga(RECARGA_RAPIDA);
		super.setImagen("img/zombies/zombie.png", 100, 100);
		super.setAnimacion("img/zombies/zombie_sprites.png", 7, 0.2f);
		super.disponibleAlInicio(); // lo pongo para testear nomas
	}

	@Override
	public void caminar() {
		
		tiempoCaminar += Render.getDeltaTime();
		if(tiempoCaminar>0.1f) {
			tiempoCaminar = 0f;
			super.animationX-=1f;
		}
		
	}
	
}
