package plantas;

public class Nuez extends Planta {

	public Nuez() {
		super("Nuez", 50, 500, 0);
	
		super.setRecarga(RECARGA_LENTA);
		super.setImagen("img/plants/nut.png", 100, 100);
		super.setAnimacionIddle("img/plants/sprites_nut.png", 5, 0.2f);
	}
	
}
