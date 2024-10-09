package plantas;

public class Girasol extends Planta {

	public Girasol() {
		super("Girasol", 50, 100, 0);
		
		super.setRecarga(RECARGA_RAPIDA);
		super.setImagen("img/plants/sunflower.png", 37, 37);
		super.setAnimacionIddle("img/plants/sprites_sunflower.png", 10, 0.2f);
		super.disponibleAlInicio();
	}
	
}
