package utilidades;

public class ZombiAtlas {

	AnimacionAtlas animacion;
	
	public ZombiAtlas() {
		animacion = new AnimacionAtlas("img/zombies/zombie_basico_atlas/zombie_basico.pack");
		animacion.agregarAnimacion("zombie_caminando", 700/7, 100, 0.2f);
		animacion.agregarAnimacion("zombie_comiendo", 546/7, 102, 0.2f);
		animacion.setAnimacionActual("zombie_comiendo", 546/7, 102);
		
	}
	
	public void dibujar() {
		animacion.dibujar("zombie_caminando",100, 100);
	}
	
}
