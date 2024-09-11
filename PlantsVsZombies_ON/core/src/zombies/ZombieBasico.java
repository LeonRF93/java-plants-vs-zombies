package zombies;

public class ZombieBasico extends Zombie {

	public ZombieBasico() {
		super("Zombie", 50, 100, 0);
	
		super.setRecarga(RECARGA_RAPIDA);
		super.setImagen("img/zombies/zombie.png", 100, 100);
		super.setAnimacion("img/plants/sprites_nut.png", 5, 0.2f);
	}
	
}
