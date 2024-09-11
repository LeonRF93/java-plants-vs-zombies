package plantas;

public class Lanzaguisantes extends Planta {

	public Lanzaguisantes() {
		super("Lanzaguisantes", 100, 100, 20);
	
		super.setRecarga(RECARGA_RAPIDA);
		super.setImagen("img/plants/pea.png", 37, 37);
		super.setAnimacion("img/plants/sprites_pea.png", 8, 0.2f);
	}
	
}
