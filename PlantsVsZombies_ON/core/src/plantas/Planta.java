package plantas;

import plantaYzombie.PlantaZombie;
import utilidades.Globales;

public abstract class Planta extends PlantaZombie {

	public Planta(String nombre, int coste, int vida, int damage) {
		super(nombre, coste, vida, damage);
		super.ANCHO_HITBOX = 25;
		super.ALTO_HITBOX = 65;
	}
	
	@Override 
	public void logica() {
		
	}
	
	@Override
	public void dibujar() {
		
		super.dibujarAnimacion();
		
		if(!Globales.pausaActiva) {
			super.reanudarAnimacion();
		} else {
			super.pausarAnimacion();
		}
		
	}

}
