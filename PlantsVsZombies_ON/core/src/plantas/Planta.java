package plantas;

import plantaYzombie.PlantaZombie;
import utilidades.Globales;

public abstract class Planta extends PlantaZombie {

	public Planta(String nombre, int coste, int vida, int damage) {
		super(nombre, coste, vida, damage);
	}
	
	@Override
	public void ejecutar() {
		
		super.dibujarAnimaciones(super.ANIM_IDDLE);
		
		if(Globales.pausaActiva) {
			super.animaciones.get(ANIM_IDDLE).pausarAnimacionEnFrame(1);
		}
	}

}
