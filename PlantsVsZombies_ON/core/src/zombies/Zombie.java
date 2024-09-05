package zombies;

import recursos.Imagen;

public class Zombie {

	private int coste;
	private int vida;
	private int recarga;
	private int velocidad = 10;
	private Imagen imagen;
	
	public Zombie(int coste, int vida, int recarga, Imagen imagen) {
		this.coste = coste;
		this.vida = vida;
		this.recarga = recarga;
	}
	
}
